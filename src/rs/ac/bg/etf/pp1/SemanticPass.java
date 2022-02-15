package rs.ac.bg.etf.pp1;

import java.util.Stack;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.structure.HashTableDataStructure;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class SemanticPass extends VisitorAdaptor {

	Logger log = Logger.getLogger(getClass());
	boolean errorDetected = false;

	Obj currentMethod = null;
	SymbolDataStructure currentMethodLocals = null;
	boolean returnFound = false;

	Stack classNesting = new Stack();
	Stack classNestingSymbolData = new Stack();

	public SemanticPass() {
		Tab.insert(Obj.Type, "bool", new Struct(Struct.Bool));
		Tab.insert(Obj.Type, "void", new Struct(Struct.None));
	}

	private String structKindToString(int structKind) {
		switch (structKind) {
		case Struct.None:
			return "none";
		case Struct.Int:
			return "int";
		case Struct.Char:
			return "char";
		case Struct.Array:
			return "array";
		case Struct.Class:
			return "class";
		case Struct.Bool:
			return "bool";
		default:
			return "invalidKind";
		}
	}

	private Obj insertOrFail(int kind, String name, Struct type) {
		return insertOrFail(kind, name, type, null);
	}

	private Obj insertOrFail(int kind, String name, Struct type, SyntaxNode info) {
		if (Tab.currentScope.findSymbol(name) != null && Tab.currentScope.findSymbol(name) != Tab.noObj) {
			report_error("Vec je deklarisano " + name, info);
			return Tab.noObj;
		}

		Obj inserted = Tab.insert(kind, name, type);

		if (currentMethod != null) {
			currentMethodLocals.insertKey(inserted);
		} else if (!classNesting.empty()) {
			kind = Obj.Fld;
			SymbolDataStructure currentClassData = (SymbolDataStructure) classNestingSymbolData.peek();
			currentClassData.insertKey(inserted);
		}

		String tip = kind == Obj.Var ? "promenljiva"
				: kind == Obj.Con ? "konstanta"
						: kind == Obj.Meth ? "metoda"
								: kind == Obj.Type ? "klasni tip" : kind == Obj.Fld ? "polje" : "element";

		report_info("Deklarisan(a) " + tip + " " + name, info);
		return inserted;
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgNameString(), Tab.noType);
		Tab.openScope();
	};

	public void visit(Program program) {
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}

	public void visit(PrimitiveTypeInt primitiveTypeInt) {
		Obj primitiveTypeIntNode = Tab.find("int");
		primitiveTypeInt.struct = primitiveTypeIntNode.getType();
	}

	public void visit(PrimitiveTypeChar primitiveTypeChar) {
		Obj primitiveTypeCharNode = Tab.find("char");
		primitiveTypeChar.struct = primitiveTypeCharNode.getType();
	}

	public void visit(PrimitiveTypeBool primitiveTypeBool) {
		Obj primitiveTypeBoolNode = Tab.find("bool");
		primitiveTypeBool.struct = primitiveTypeBoolNode.getType();
	}

	public void visit(TypePrimitive typePrimitive) {
		typePrimitive.struct = typePrimitive.getPrimitiveType().struct;
	}

	public void visit(TypeNonPrimitive typeNonPrimitive) {
		Obj klasniTipNode = Tab.find(typeNonPrimitive.getClassName());
		if (Tab.noObj == klasniTipNode) {
			errorDetected = true;
			report_error("Ovaj klasni tip ne postoji u ovom opsegu", typeNonPrimitive);
			typeNonPrimitive.struct = new Struct(Struct.None);
		} else {
			typeNonPrimitive.struct = klasniTipNode.getType();
		}
	}

	public void visit(VarDecls varDecls) {
		Struct type = varDecls.getType().struct;

		VarList varList = varDecls.getVarList();

		while (varList instanceof MultiVar) {
			MultiVar multiVar = (MultiVar) varList;
			VarDecl varDecl = multiVar.getVarDecl(); // TODO do something with varDecl

			if (varDecl instanceof VarDeclNotArr) {
				VarDeclNotArr varDeclNotArr = (VarDeclNotArr) varDecl;
				insertOrFail(Obj.Var, varDeclNotArr.getVarName(), type, varDecls);
			} else if (varDecl instanceof VarDeclArr) {
				VarDeclArr varDeclArr = (VarDeclArr) varDecl;
				insertOrFail(Obj.Var, varDeclArr.getVarName(), Tab.insert(Obj.Type,
						structKindToString(type.getKind()) + "ArrType", new Struct(Struct.Array, type)).getType(),
						varDecls);
			} else {
				report_error("WTF, ovo nije ni Array ni NotArray", varDecl);
			}

			varList = multiVar.getVarList();
		}

		// Sad je sigurno SingleVar, definitivno poslednja u listi
		SingleVar singleVar = (SingleVar) varList;
		VarDecl varDecl = singleVar.getVarDecl();

		if (varDecl instanceof VarDeclNotArr) {
			VarDeclNotArr varDeclNotArr = (VarDeclNotArr) varDecl;
			insertOrFail(Obj.Var, varDeclNotArr.getVarName(), type, varDecls);
		} else if (varDecl instanceof VarDeclArr) {
			VarDeclArr varDeclArr = (VarDeclArr) varDecl;
			insertOrFail(Obj.Var, varDeclArr.getVarName(),
					Tab.insert(Obj.Type, structKindToString(type.getKind()) + "ArrType", new Struct(Struct.Array, type))
							.getType(),
					varDecls);
		} else {
			report_error("WTF, ovo nije ni Array ni NotArray", varDecl);
		}
	}

	private int getConstExprValue(ConstExpr constExpr) {
		if (constExpr instanceof ConstExprInt) {
			ConstExprInt constExprInt = (ConstExprInt) constExpr;
			return constExprInt.getN1().intValue();
		} else if (constExpr instanceof ConstExprChar) {
			ConstExprChar constExprChar = (ConstExprChar) constExpr;
			return Character.getNumericValue(constExprChar.getC1().charValue());
		} else { // ConstExprBool
			ConstExprBool constExprBool = (ConstExprBool) constExpr;
			BoolValue boolValue = constExprBool.getBoolValue();
			return boolValue instanceof BoolValueFalse ? 0 : 1;
		}
	}

	public void visit(ConstDecl constDecl) {
		// TODO check type compatibility?

		PrimitiveType primitiveType = constDecl.getPrimitiveType();

		ConstDeclArray constDeclArray = constDecl.getConstDeclArray();

		while (constDeclArray instanceof MultiConstDeclArray) {
			MultiConstDeclArray multiConstDeclArray = (MultiConstDeclArray) constDeclArray;

			ConstExpr constExpr = multiConstDeclArray.getConstExpr();

			Obj inserted = insertOrFail(Obj.Con, multiConstDeclArray.getConstIdent(), primitiveType.struct, constDecl);
			inserted.setAdr(getConstExprValue(constExpr));

			constDeclArray = multiConstDeclArray.getConstDeclArray();
		}

		// Sad je sigurno SingleConstDeclArray
		SingleConstDeclArray singleConstDeclArray = (SingleConstDeclArray) constDeclArray;

		ConstExpr constExpr = singleConstDeclArray.getConstExpr();

		Obj inserted = insertOrFail(Obj.Con, singleConstDeclArray.getConstIdent(), primitiveType.struct, constDecl);
		inserted.setAdr(getConstExprValue(constExpr));
	}

	public void visit(RecordIdent recordIdent) {
		recordIdent.obj = insertOrFail(Obj.Type, recordIdent.getRecordTypeName(), new Struct(Struct.Class), recordIdent);
		Tab.openScope();
		
		classNesting.push(recordIdent.obj.getType());
		classNestingSymbolData.push(new HashTableDataStructure());
	}

	public void visit(RecordDecl recordDecl) {
		Tab.chainLocalSymbols(recordDecl.getRecordIdent().obj);
		Tab.closeScope();
		
		Struct currentRecord = (Struct) classNesting.pop();
		currentRecord.setMembers((SymbolDataStructure) classNestingSymbolData.pop());
	}

	public void visit(ClassName className) {
		className.obj = insertOrFail(Obj.Type, className.getClassNameString(), new Struct(Struct.Class), className);
		Tab.openScope();

		classNesting.push(className.obj.getType());
		classNestingSymbolData.push(new HashTableDataStructure());
	}

	public void visit(ClassDecl classDecl) {
		Tab.chainLocalSymbols(classDecl.getClassName().obj);
		Tab.closeScope();

		Struct currentClass = (Struct) classNesting.pop();
		currentClass.setMembers((SymbolDataStructure) classNestingSymbolData.pop());
	}

	public void visit(TypeOrVoid typeOrVoid) {
		if (typeOrVoid instanceof VoidType) {
			Obj primitiveTypeVoidNode = Tab.find("void");
			typeOrVoid.struct = primitiveTypeVoidNode.getType();
		} else { // Not void
			NotVoidType notVoidType = (NotVoidType) typeOrVoid;
			typeOrVoid.struct = notVoidType.getType().struct;
		}
	}

	public void visit(VoidType voidType) {
		Obj primitiveTypeVoidNode = Tab.find("void");
		voidType.struct = primitiveTypeVoidNode.getType();
	}

	public void visit(NotVoidType notVoidType) {
		notVoidType.struct = notVoidType.getType().struct;
	}

	public void visit(MethodSignature methodSignature) {
		currentMethod = insertOrFail(Obj.Meth, methodSignature.getMethName(), methodSignature.getTypeOrVoid().struct,
				methodSignature);
		currentMethodLocals = new HashTableDataStructure();
		methodSignature.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + methodSignature.getMethName(), methodSignature);
	}

	public void visit(MethodDecl methodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.find("void").getType()) {
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName()
					+ " nema return iskaz!", null);
		}

		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		report_info("Gotova obrada funkcije " + methodDecl.getMethodSignature().getMethName(), methodDecl);
		currentMethod = null;
		currentMethodLocals = null;
	}

	public void visit(YesFormalParams yesFormalParams) {
		FormParsList formParsListIterator = yesFormalParams.getFormParsList();

		int numberOfFormalParams = 0;

		while (formParsListIterator instanceof MultiFormalParam) {
			MultiFormalParam multiFormalParam = (MultiFormalParam) formParsListIterator;

			Struct type = multiFormalParam.getType().struct;
			VarDecl varDecl = multiFormalParam.getVarDecl();

			if (varDecl instanceof VarDeclNotArr) {
				VarDeclNotArr varDeclNotArr = (VarDeclNotArr) varDecl;
				insertOrFail(Obj.Var, varDeclNotArr.getVarName(), type, yesFormalParams).setFpPos(numberOfFormalParams);
			} else if (varDecl instanceof VarDeclArr) {
				VarDeclArr varDeclArr = (VarDeclArr) varDecl;
				insertOrFail(Obj.Var, varDeclArr.getVarName(),
						Tab.insert(Obj.Type, structKindToString(type.getKind()) + "ArrType",
								new Struct(Struct.Array, type)).getType(),
						yesFormalParams).setFpPos(numberOfFormalParams);
			} else {
				report_error("WTF, ovo nije ni Array ni NotArray", varDecl);
			}

			++numberOfFormalParams;
			formParsListIterator = multiFormalParam.getFormParsList();
		}

		// Sigurno SingleFormalParam

		SingleFormalParam singleFormalParam = (SingleFormalParam) formParsListIterator;

		Struct type = singleFormalParam.getType().struct;
		VarDecl varDecl = singleFormalParam.getVarDecl();

		if (varDecl instanceof VarDeclNotArr) {
			VarDeclNotArr varDeclNotArr = (VarDeclNotArr) varDecl;
			insertOrFail(Obj.Var, varDeclNotArr.getVarName(), type, yesFormalParams).setFpPos(numberOfFormalParams);
		} else if (varDecl instanceof VarDeclArr) {
			VarDeclArr varDeclArr = (VarDeclArr) varDecl;
			insertOrFail(Obj.Var, varDeclArr.getVarName(),
					Tab.insert(Obj.Type, structKindToString(type.getKind()) + "ArrType", new Struct(Struct.Array, type))
							.getType(),
					yesFormalParams).setFpPos(numberOfFormalParams);
		} else {
			report_error("WTF, ovo nije ni Array ni NotArray", varDecl);
		}

		++numberOfFormalParams;
		currentMethod.setLevel(numberOfFormalParams);
	}

	/////////////////// STATEMENTS START ////////////////////////

	
	
	/////////////////// STATEMENTS END ////////////////////////

}

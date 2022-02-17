package rs.ac.bg.etf.pp1;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
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

	public int numberOfGlobalVars = 0;

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

		kind = classNesting.empty() ? kind : Obj.Fld;

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
		numberOfGlobalVars = Tab.currentScope.getnVars();
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
		recordIdent.obj = insertOrFail(Obj.Type, recordIdent.getRecordTypeName(), new Struct(Struct.Class),
				recordIdent);
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
		returnFound = false;
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

	public void visit(FactorNumberConst f) {
		f.obj = Tab.find("int");
	}

	public void visit(FactorCharConst f) {
		f.obj = Tab.find("char");
	}

	public void visit(FactorBoolConst f) {
		f.obj = Tab.find("bool");
	}

	public void visit(FactorExprInParens f) {
		f.obj = f.getExpr().obj;
	}

	private static int objectIdGen = 0;
	public void visit(FactorNew f) {
		if (f.getType().struct.getKind() != Struct.Class) {
			report_error("Posle 'new' mora da ide naziv klase", f);
		}

		f.obj = Tab.insert(Obj.Var, "NewObject"+objectIdGen++, f.getType().struct);
	}

	private static int arrayIdGen = 0;
	public void visit(FactorNewArray f) {
		if (f.getExpr().obj.getType().getKind() != Struct.Int) {
			report_error("Ne moze u uglastim zagradama nesto sto nije integer", f);
			return;
		}

		f.obj = Tab.insert(Obj.Var, "NewArray"+arrayIdGen++, new Struct(Struct.Array, f.getType().struct));
	}

	public void visit(FactorDesignator f) {
		if (f.getDesignator().obj == null) {
			report_error("Designator ne postoji", f);
			return;
		}

		f.obj = f.getDesignator().obj;
	}

	public void visit(FactorDesignatorCall f) {
		if (f.getDesignator() instanceof DesignatorIdent) {
			DesignatorIdent designatorIdent = (DesignatorIdent) f.getDesignator();
			if (Tab.find(designatorIdent.getDesignatorName()).getKind() != Obj.Meth) {
				report_error("Ovo nije metoda, ne moze se pozvati", f);
				return;
			}
		}
		Designator methodDesignator = f.getDesignator();
		f.obj = methodDesignator.obj;

		OptionalActPars actualPars = f.getOptionalActPars();
		Collection c = methodDesignator.obj.getLocalSymbols();
		Iterator i = c.iterator();

		try {
			// Count actual pars
			int numOfPars = 0;
			if (actualPars instanceof YesActPars) {
				ActPars ac = ((YesActPars) actualPars).getActPars();
				while (ac instanceof NonLastExprActPar) {
					if (((NonLastExprActPar) ac).getExpr().obj.getType() != ((Obj) i.next()).getType()) { //TODO check if obj.getType() works as intended
						if(((LastExprActPars) ac).getExpr().obj.getType().getKind() != Struct.Array)
							report_error("Ne poklapaju se svi tipovi", actualPars);
					}
					++numOfPars;
					ac = ((NonLastExprActPar) ac).getActPars();
				}
				if (((LastExprActPars) ac).getExpr().obj.getType() != ((Obj) i.next()).getType()) { //TODO check if obj.getType() works as intended
					if(((LastExprActPars) ac).getExpr().obj.getType().getKind() != Struct.Array)
						report_error("Ne poklapaju se svi tipovi", actualPars);
				}
				++numOfPars;
			}

			if (methodDesignator.obj.getLevel() != numOfPars) {
				report_error("Ne poklapa se broj formalnih parametara sa brojem stvarnih parametara", methodDesignator);
			}
		} catch (NoSuchElementException e) {
			report_error("Ne poklapa se broj formalnih parametara sa brojem stvarnih parametara", methodDesignator);
		}
	}

	public void visit(MultiFactorTerm multiFactorTerm) {
		Factor firstFactor = multiFactorTerm.getFactor();

		if (firstFactor.obj.getType() != Tab.find("int").getType()) {
			report_error("Samo tip int moze da ucestvuje u operaciji mnozenja/deljenja/ostatka pri deljenju",
					multiFactorTerm);
		}

		MultipleFactorList mpf = multiFactorTerm.getMultipleFactorList();
		do {
			Factor f;
			if (mpf instanceof NonLastFactor) {
				NonLastFactor nlf = (NonLastFactor) mpf;
				f = nlf.getFactor();
				mpf = nlf.getMultipleFactorList();
			} else {
				LastFactor lf = (LastFactor) mpf;
				f = lf.getFactor();
			}
			if (!firstFactor.obj.getType().compatibleWith(f.obj.getType())) {
				report_error("Tipovi nisu kompatibilni", multiFactorTerm);
			}
			if (f.obj.getType() != Tab.find("int").getType()) {
				report_error("Samo tip int moze da ucestvuje u operaciji mnozenja/deljenja/ostatka pri deljenju",
						multiFactorTerm);
			}
		} while (mpf instanceof NonLastFactor);

		multiFactorTerm.obj = Tab.find("int");
	}

	public void visit(SingleFactorTerm term) {
		term.obj = term.getFactor().obj;
	}

	public void visit(JustTermExpr expr) {
		expr.obj = expr.getTerm().obj;
	}

	public void visit(MinusJustTermExpr expr) {
		if (expr.getTerm().obj.getType() != Tab.find("int").getType()) {
			report_error("Moze se negirati samo integer", expr);
		}
		expr.obj = expr.getTerm().obj;
	}

	public void visit(NullExpr expr) {
		expr.obj = Tab.find("null");
	}

	public void visit(MultiAddOpExpr expr) {
		Term firstTerm = expr.getTerm();
		if (firstTerm.obj.getType() != Tab.find("int").getType()) {
			report_error("Mogu se dodavati i oduzimati samo integeri", expr);
		}

		AdditionTermList atl = expr.getAdditionTermList();
		do {
			Term t;
			if (atl instanceof NonLastTerm) {
				t = ((NonLastTerm) atl).getTerm();
				atl = ((NonLastTerm) atl).getAdditionTermList();
			} else {
				t = ((LastTerm) atl).getTerm();
			}
			if (t.obj.getType() != Tab.find("int").getType()) {
				report_error("Mogu se dodavati i oduzimati samo integeri", expr);
			}
		} while (atl instanceof NonLastTerm);

		expr.obj = Tab.find("int");
	}

	public void visit(MinusMultiAddOpExpr expr) {
		Term firstTerm = expr.getFirstTermNegative().getTerm();
		if (firstTerm.obj.getType() != Tab.find("int").getType()) {
			report_error("Mogu se dodavati i oduzimati samo integeri", expr);
		}

		AdditionTermList atl = expr.getAdditionTermList();
		do {
			Term t;
			if (atl instanceof NonLastTerm) {
				t = ((NonLastTerm) atl).getTerm();
				atl = ((NonLastTerm) atl).getAdditionTermList();
			} else {
				t = ((LastTerm) atl).getTerm();
			}
			if (t.obj.getType() != Tab.find("int").getType()) {
				report_error("Mogu se dodavati i oduzimati samo integeri", expr);
			}
		} while (atl instanceof NonLastTerm);

		expr.obj = Tab.find("int");
	}

	public void visit(DesignatorIdent designator) {
		Obj target = Tab.find(designator.getDesignatorName());
		if (target == Tab.noObj) {
			report_error("Nepostojeci designator", designator);
			return;
		}

		DesignationList designationList = designator.getDesignationList();

		while (!(designationList instanceof DesignationNone) && target != null) {
			if (target.getKind() != Obj.Var && target.getKind() != Obj.Fld) {
				report_error("Pristupanje nekom clanu klase/recorda/niza je moguce samo u slucaju varijable",
						designator);
			}
			if (designationList instanceof DesignationObjectAccess) {
				DesignationObjectAccess doa = (DesignationObjectAccess) designationList;
				Struct type = target.getType();
				if (type.getKind() != Struct.Class) {
					report_error("Samo se klasama/rekordima moze pristupati memberima", doa);
				}
				Obj tmpTar = type.getMembersTable().searchKey(doa.getMemberName()); // TODO Test if this properly propagates record membership
				target = new Obj(Obj.Fld, Integer.toString(target.getAdr()) , tmpTar.getType(), tmpTar.getAdr(), target.getLevel());
				
				doa.obj = tmpTar;
				
				designationList = doa.getDesignationList();
			} else if (designationList instanceof DesignationArrayAccess) {
				DesignationArrayAccess daa = (DesignationArrayAccess) designationList;
				Struct type = target.getType();
				if (type.getKind() != Struct.Array) {
					report_error("Samo se nizu moze pristupati elementima", daa);
				}
				if (daa.getExpr().obj.getType().getKind() != Struct.Int) {
					report_error("Samo integer moze biti indeks niza", daa);
				}
				target = new Obj(Obj.Elem, Integer.toString(target.getAdr()), target.getType().getElemType(), target.getAdr(), target.getLevel());
				// TODO check if this works. Looks like it works...
				designationList = daa.getDesignationList();
				
				daa.obj = target;
			}
		}
		designator.obj = target;
	}

	public void visit(DesignatorStatementAssign stmt) {
		Designator d = stmt.getDesignator();
		Expr e = stmt.getExpr();

		if (!e.obj.getType().assignableTo(d.obj.getType())) {
			report_error("Nisu kompatibilni tipovi za dodelu", stmt);
		}
	}

	public void visit(DesignatorStatementInc stmt) {
		Designator d = stmt.getDesignator();
		if (!(d.obj.getKind() == Obj.Var || d.obj.getKind() == Obj.Fld || d.obj.getKind() == Obj.Elem)
				&& d.obj.getType().getKind() != Struct.Int) {
			report_error("Ne moze se upotrebiti ovaj operator", stmt);
		}
	}

	public void visit(DesignatorStatementDec stmt) {
		Designator d = stmt.getDesignator();
		if (!(d.obj.getKind() == Obj.Var || d.obj.getKind() == Obj.Fld || d.obj.getKind() == Obj.Elem)
				&& d.obj.getType().getKind() != Struct.Int) {
			report_error("Ne moze se upotrebiti ovaj operator", stmt);
		}
	}

	public void visit(DesignatorStatementFuncCall stmt) {
		Designator methodDesignator = stmt.getDesignator();
		OptionalActPars actualPars = stmt.getOptionalActPars();

		if (methodDesignator.obj.getKind() != Obj.Meth) {
			report_error("Ne moze se pozvati nesto sto nije metoda", stmt);
			return;
		}

		Collection c = methodDesignator.obj.getLocalSymbols();
		Iterator i = c.iterator();

		try {
			// Count actual pars
			int numOfPars = 0;
			if (actualPars instanceof YesActPars) {
				ActPars ac = ((YesActPars) actualPars).getActPars();
				while (ac instanceof NonLastExprActPar) {
					if (((NonLastExprActPar) ac).getExpr().obj.getType() != ((Obj) i.next()).getType()) {
						if(((LastExprActPars) ac).getExpr().obj.getType().getKind() != Struct.Array)
							report_error("Ne poklapaju se svi tipovi", actualPars);
					}
					++numOfPars;
					ac = ((NonLastExprActPar) ac).getActPars();
				}
				if (((LastExprActPars) ac).getExpr().obj.getType() != ((Obj) i.next()).getType()) {
					if(((LastExprActPars) ac).getExpr().obj.getType().getKind() != Struct.Array)
						report_error("Ne poklapaju se svi tipovi", actualPars);
				}
				++numOfPars;
			}

			if (methodDesignator.obj.getLevel() != numOfPars) {
				report_error("Ne poklapa se broj formalnih parametara sa brojem stvarnih parametara", stmt);
			}
		} catch (NoSuchElementException e) {
			report_error("Ne poklapa se broj formalnih parametara sa brojem stvarnih parametara", stmt);
		}
	}

	public void visit(ReturnStmt stmt) {
		if (currentMethod == null) {
			report_error("Return se mora nalaziti unutar funkcije, a ne ovde", stmt);
		}

		if (!currentMethod.getType().compatibleWith(stmt.getExpr().obj.getType())) {
			report_error("Ne moze se taj tip vratiti", stmt);
		}

		returnFound = true;
	}

	public void visit(BreakStmt stmt) {
		boolean valid = false;
		for (SyntaxNode ancestor = stmt.getParent(); ancestor != null; ancestor = ancestor.getParent()) {
			if (ancestor instanceof DoWhileStmt) {
				valid = true;
				return;
			}
		}
		if (!valid) {
			report_error("Break moze da stoji samo unutar petlje, a ne ovde", stmt);
		}
	}

	public void visit(ContinueStmt stmt) {
		boolean valid = false;
		for (SyntaxNode ancestor = stmt.getParent(); ancestor != null; ancestor = ancestor.getParent()) {
			if (ancestor instanceof DoWhileStmt) {
				valid = true;
				return;
			}
		}
		if (!valid) {
			report_error("Continue moze da stoji samo unutar petlje, a ne ovde", stmt);
		}
	}

	public void visit(ReadStmt stmt) {
		int kind = stmt.getDesignator().obj.getKind();
		if (kind != Obj.Fld && kind != Obj.Var && kind != Obj.Elem) {
			report_error("Ovo se ne moze ucitati od korisnika", stmt);
		}
		if (stmt.getDesignator().obj.getType() != Tab.intType && stmt.getDesignator().obj.getType() != Tab.charType
				&& stmt.getDesignator().obj.getType() != Tab.find("bool").getType()) {
			report_error("Ovo se ne moze ucitati od korisnika", stmt);
		}
	}

	public void visit(PrintStmt stmt) {
		int kind = stmt.getExpr().obj.getType().getKind();
		if (kind != Struct.Int && kind != Struct.Char && kind != Struct.Bool) {
			report_error("Mogu se ispisivati samo primitivni tipovi", stmt);
		}
	}

	public void visit(PrintStmtWithWidth stmt) {
		int kind = stmt.getExpr().obj.getType().getKind();
		if (kind != Struct.Int && kind != Struct.Char && kind != Struct.Bool) {
			report_error("Mogu se ispisivati samo primitivni tipovi", stmt);
		}
	}

	/////////////////// STATEMENTS END ////////////////////////

	/////////////////// CONDITIONS BEGIN ////////////////////////

	public void visit(CondFactJustExpr condFact) {
		Expr expr = condFact.getExpr();
		if (expr.obj.getType().getKind() != Struct.Bool) {
			report_error("Uslov mora biti nesto sto je bool tipa", condFact);
		}
	}

	public void visit(CondFactRelOp condFact) {
		Expr e0 = condFact.getExpr();
		Expr e1 = condFact.getExpr1();

		if (e0.obj == null || e1.obj == null) {
			report_error("Ne moze se ispitati validnost na kompatibilnost u slucaju nepostojeceg designatora", e0);
			return;
		}

		if (!e0.obj.getType().compatibleWith(e1.obj.getType())) {
			report_error("Ne mogu se porediti tipovi koji nisu kompatibilni", condFact);
			return;
		}

		if (e0.obj.getType().getKind() == Struct.Array || e0.obj.getType().getKind() == Struct.Class) {
			RelOp operator = condFact.getRelOp();
			if (!(operator instanceof RelOpEq || operator instanceof RelOpNeq)) {
				report_error("Klase, recordi i nizovi se mogu porediti samo na jednakost", operator);
			}
		}
	}

	// TODO uraditi ostalo ako treba... Za sad se cini da ne treba

	/////////////////// CONDITIONS END ////////////////////////
}

package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}

	///////////////////////// NODE VISIT START //////////////////////

	public void visit(MethodSignature methodSignature) {
		if ("main".equalsIgnoreCase(methodSignature.getMethName())) {
			mainPc = Code.pc;
		}

		methodSignature.obj.setAdr(Code.pc);

		int numberOfLocals = 0;
		MethodDecl methodDecl = (MethodDecl) methodSignature.getParent();
		OptionalVarDeclsList ovdl = methodDecl.getOptionalVarDeclsList();
		if (ovdl instanceof YesVarDeclsList) {
			++numberOfLocals;
			VarDeclsList vdl = ((YesVarDeclsList) ovdl).getVarDeclsList();
			while (vdl instanceof MultiVarDecls) {
				++numberOfLocals;
				vdl = ((MultiVarDecls) vdl).getVarDeclsList();
			}
		}

		// Generate the entry
		Code.put(Code.enter);
		Code.put(methodSignature.obj.getLevel());
		Code.put(methodSignature.obj.getLevel() + numberOfLocals);
	}

	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(PrintStmt stmt) {

		stmt.getExpr().obj.getAdr();
		switch (stmt.getExpr().obj.getType().getKind()) {
		case Struct.Int:
			Code.loadConst(5);
			Code.put(Code.print);
			break;
		case Struct.Char:
			Code.loadConst(1);
			Code.put(Code.bprint);
			break;
		case Struct.Bool:
			byte value = Code.buf[--Code.pc]; // Eliminate the bool value and shoot chars
			String output = value != 15 ? "true" : "false";

			for (int i = 0; i < output.length(); ++i) {
				Code.loadConst((int) (output.charAt(i)));
				Code.loadConst(1);
				Code.put(Code.bprint);
			}

			break;
		}
	}

	public void visit(FactorNumberConst f) {
		Code.load(new Obj(Obj.Con, f.obj.getName(), f.obj.getType(), f.getN1().intValue(), f.obj.getLevel()));
	}

	public void visit(FactorCharConst f) {
		Code.load(new Obj(Obj.Con, f.obj.getName(), f.obj.getType(), (int) (f.getC1().charValue()), f.obj.getLevel()));
	}

	public void visit(FactorBoolConst f) {
		Code.load(new Obj(Obj.Con, f.obj.getName(), f.obj.getType(), f.getBoolValue() instanceof BoolValueTrue ? 1 : 0,
				f.obj.getLevel()));
	}
	
	public void visit(DesignatorStatementAssign stmt){
		Code.store(stmt.getDesignator().obj);
	}

	public void visit(DesignatorIdent designator) {
		if (designator.getDesignatorName().contentEquals("eol")) { // Const EOL
			Obj eolObj = Tab.find("eol");
			Code.load(new Obj(Obj.Con, eolObj.getName(), eolObj.getType(), 10, eolObj.getLevel()));
			return;
		}
		
		SyntaxNode parent = designator.getParent();
		if(FactorDesignator.class == parent.getClass()){ // Ako je sa leve strane izraza, baca se na expr stack
			Code.load(designator.obj);
		}
	}
	
	public void visit(MultiFactorTerm multiFactorTerm) {
		int operation = multiFactorTerm.getMulOp() instanceof MulOpTimes ? Code.mul: 
			multiFactorTerm.getMulOp() instanceof MulOpDiv ? Code.div : Code.rem;
		Code.put(operation);
	}
	
	public void visit(NonLastFactor nonLastFactor) {
		int operation = nonLastFactor.getMulOp() instanceof MulOpTimes ? Code.mul: 
			nonLastFactor.getMulOp() instanceof MulOpDiv ? Code.div : Code.rem;
		Code.put(operation);
	}
	
	public void visit(NonLastTerm nonLastTerm) {
		int operation = nonLastTerm.getAddOp() instanceof AddOpPlus ? Code.add : Code.sub ;
		Code.put(operation);
	}
	
	public void visit(MultiAddOpExpr multiAddOpExpr) {
		int operation = multiAddOpExpr.getAddOp() instanceof AddOpPlus ? Code.add : Code.sub ;
		Code.put(operation);
	}
	
	public void visit(MinusJustTermExpr minusJustTermExpr) {
		Code.put(Code.neg);
	}
	
	public void visit(MinusMultiAddOpExpr minusMultiAddOpExpr) {
		int operation = minusMultiAddOpExpr.getAddOp() instanceof AddOpPlus ? Code.add : Code.sub ;
		Code.put(operation);
	}
	
	public void visit(FirstTermNegative firstTermNegative) {
		Code.put(Code.neg);
	}
	
	public void visit(DesignatorStatementInc stmt) {
		Designator designator = stmt.getDesignator();
		Code.load(designator.obj);
		Code.load(new Obj(Obj.Con, "const1", new Struct(Struct.Int), 1, designator.obj.getLevel()));
		Code.put(Code.add);
		Code.store(designator.obj);
	}
	
	public void visit(DesignatorStatementDec stmt) {
		Designator designator = stmt.getDesignator();
		Code.load(designator.obj);
		Code.load(new Obj(Obj.Con, "const1", new Struct(Struct.Int), 1, designator.obj.getLevel()));
		Code.put(Code.sub);
		Code.store(designator.obj);
	}

}

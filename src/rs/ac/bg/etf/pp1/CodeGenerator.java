package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}
	
	///////////////////////// NODE VISIT START //////////////////////
	
	public void visit(MethodSignature methodSignature) {
		if("main".equalsIgnoreCase(methodSignature.getMethName())){
			mainPc = Code.pc;
		}
		
		methodSignature.obj.setAdr(Code.pc);
		
		int numberOfLocals = 0;
		MethodDecl methodDecl = (MethodDecl)methodSignature.getParent();
		OptionalVarDeclsList ovdl = methodDecl.getOptionalVarDeclsList();
		if(ovdl instanceof YesVarDeclsList) {
			++numberOfLocals;
			VarDeclsList vdl = ((YesVarDeclsList) ovdl).getVarDeclsList();
			while(vdl instanceof MultiVarDecls) {
				++numberOfLocals;
				vdl = ((MultiVarDecls) vdl).getVarDeclsList();
			}
		}
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(methodSignature.obj.getLevel());
		Code.put(methodSignature.obj.getLevel() + numberOfLocals);
	}
	
	public void visit(MethodDecl methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(PrintStmt stmt) {
		switch(stmt.getExpr().struct.getKind()) {
		case Struct.Int:
			Code.loadConst(5);
			Code.put(Code.print);
			break;
		case Struct.Char:
			Code.loadConst(1);
			Code.put(Code.bprint);
			break;
		case Struct.Bool:
			// TODO
			break;
		}
	}
	
	
}

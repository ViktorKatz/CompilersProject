package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
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

		// Generate the entry
		Code.put(Code.enter);
		Code.put(methodSignature.obj.getLevel());
		Code.put(methodSignature.obj.getLocalSymbols().size());
	}

	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(PrintStmt stmt) {
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
			String output = (value & 1) == 1 ? "true" : "false";
			for (int i = 0; i < output.length(); ++i) {
				Code.loadConst((int) (output.charAt(i)));
				Code.loadConst(1);
				Code.put(Code.bprint);
			}
			break;
		}
	}

	public void visit(PrintStmtWithWidth stmt) {
		int width = stmt.getN2().intValue();
		switch (stmt.getExpr().obj.getType().getKind()) {
		case Struct.Int:
			Code.loadConst(width);
			Code.put(Code.print);
			break;
		case Struct.Char:
			Code.loadConst(width);
			Code.put(Code.bprint);
			break;
		case Struct.Bool:
			byte value = Code.buf[--Code.pc]; // Eliminate the bool value and shoot chars
			String output = value != 15 ? "true" : "false";
			for (int i = 0; i < output.length(); ++i) {
				Code.loadConst((int) (output.charAt(i)));
				Code.loadConst(width);
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

	public void visit(DesignatorStatementAssign stmt) {
		Code.store(stmt.getDesignator().obj);
	}

	public void visit(DesignatorIdent designator) {
		if (designator.getDesignatorName().contentEquals("eol")) { // Const EOL
			Code.load(Tab.find("eol"));
			return;
		}

		/*
		 * DesignationList dl = designator.getDesignationList(); while(dl instanceof
		 * DesignationArrayAccess || dl instanceof DesignationObjectAccess) {
		 * DesignationArrayAccess daa; DesignationObjectAccess doa; if(dl instanceof
		 * DesignationArrayAccess) { daa = (DesignationArrayAccess) dl;
		 * daa.getDesignationArrayLoad();daa.get dl = daa.getDesignationList(); } else
		 * if(dl instanceof DesignationObjectAccess) { doa = (DesignationObjectAccess)
		 * dl;
		 * 
		 * dl = doa.getDesignationList(); } }
		 */

		SyntaxNode parent = designator.getParent();
		if (FactorDesignator.class == parent.getClass()) { // Ako je sa desne strane izraza, baca se na expr stack
			if (designator.obj.getKind() == Obj.Fld) {
				Obj o = designator.obj;
				String name = o.getName();
				try {
					int nameLoc = Integer.parseInt(name);
					Code.load(new Obj(Obj.Var, o.getName(), o.getType(), nameLoc, o.getLevel()));
				} catch (NumberFormatException e) {
					// Code.load(new Obj(Obj.Var, o.getName(), o.getType(), o.getAdr(),
					// o.getLevel()));
				}
			}

			Code.load(designator.obj);

		}
		if (DesignatorStatementAssign.class == parent.getClass()) { // Ako je sa leve strane izraza...
			if(designator.obj.getKind() == Obj.Fld)
				Code.load(new Obj(Obj.Var,
						designator.obj.getName(),
						designator.obj.getType(),
						Integer.parseInt(designator.obj.getName()),	// Kroz name se propagira originalna adresa maticnog objekta
						designator.obj.getLevel()));
			
			DesignationList dl = designator.getDesignationList();
			while (dl instanceof DesignationArrayAccess || dl instanceof DesignationObjectAccess) {
				DesignationArrayAccess daa;
				DesignationObjectAccess doa;
				if (dl instanceof DesignationArrayAccess) {
					daa = (DesignationArrayAccess) dl;
					// Code.load(daa.obj);
					dl = daa.getDesignationList();
				} else if (dl instanceof DesignationObjectAccess) {
					doa = (DesignationObjectAccess) dl;
					// Code.load(doa.obj);
					dl = doa.getDesignationList();
				}
			}
		}
	}

	public void visit(DesignationArrayAccess daa) {

	}

	public void visit(MultiFactorTerm multiFactorTerm) {
		int operation = multiFactorTerm.getMulOp() instanceof MulOpTimes ? Code.mul
				: multiFactorTerm.getMulOp() instanceof MulOpDiv ? Code.div : Code.rem;
		Code.put(operation);
	}

	public void visit(NonLastFactor nonLastFactor) {
		int operation = nonLastFactor.getMulOp() instanceof MulOpTimes ? Code.mul
				: nonLastFactor.getMulOp() instanceof MulOpDiv ? Code.div : Code.rem;
		Code.put(operation);
	}

	public void visit(NonLastTerm nonLastTerm) {
		int operation = nonLastTerm.getAddOp() instanceof AddOpPlus ? Code.add : Code.sub;
		Code.put(operation);
	}

	public void visit(MultiAddOpExpr multiAddOpExpr) {
		int operation = multiAddOpExpr.getAddOp() instanceof AddOpPlus ? Code.add : Code.sub;
		Code.put(operation);
	}

	public void visit(MinusJustTermExpr minusJustTermExpr) {
		Code.put(Code.neg);
	}

	public void visit(MinusMultiAddOpExpr minusMultiAddOpExpr) {
		int operation = minusMultiAddOpExpr.getAddOp() instanceof AddOpPlus ? Code.add : Code.sub;
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

	public void visit(ReadStmt stmt) {
		Designator designator = stmt.getDesignator();
		if (designator.obj.getType() == Tab.find("int").getType()) {
			Code.put(Code.read);
			Code.store(designator.obj);
		} else { // Char or bool
			Code.put(Code.bread);
			Code.store(designator.obj);
		}
	}

	public void visit(DesignatorStatementFuncCall stmt) {
		Obj methObj = stmt.getDesignator().obj;
		int offset = methObj.getAdr() - Code.pc;

		Code.put(Code.call);
		Code.put2(offset);
	}

	public void visit(FactorDesignatorCall funcCall) {
		if (((DesignatorIdent) funcCall.getDesignator()).getDesignatorName().equals("len")) {
			Code.put(Code.arraylength);
			return;
		}
		// TODO Like len(arr) implement chr(int) and ord(chr);

		Obj methObj = funcCall.getDesignator().obj;
		int offset = methObj.getAdr() - Code.pc;

		Code.put(Code.call);
		Code.put2(offset);
	}

	public void visit(FactorNewArray newArrayFact) {
		Code.put(Code.newarray);
		int isItWideWord = newArrayFact.getType().struct == Tab.find("char").getType() ? 0 : 1;
		Code.put(isItWideWord);
	}

	public void visit(FactorNew factorNew) {
		Code.put(Code.new_);
		Code.put2(factorNew.getType().struct.getNumberOfFields());
	}

	public void visit(DesignationArrayEntry dae) {
		SyntaxNode ancestor = dae.getParent();
		while (!(ancestor instanceof DesignatorIdent))
			ancestor = ancestor.getParent();

		Obj designatorObj = ((DesignatorIdent) ancestor).obj;
		Code.load(new Obj(Obj.Var, "ArrayDesignationAccess", designatorObj.getType(), designatorObj.getAdr(),
				designatorObj.getLevel()));
	}

}

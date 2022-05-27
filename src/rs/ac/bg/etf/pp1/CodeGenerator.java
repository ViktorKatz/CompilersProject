package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

import java.util.HashMap;
import java.util.HashSet;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}

	////////////////////// Conditions start/////////////////

	public HashMap<String, String> methodsWithVarArgs;

	// Ordered
	Class[] relOpClasses = { RelOpEq.class, RelOpNeq.class, RelOpLess.class, RelOpLeq.class, RelOpGre.class,
			RelOpGeq.class };

	HashMap myJumpAddr = new HashMap(128);
	HashMap endAddr = new HashMap(128); // Initial capacity

	private SyntaxNode getCondTreeParent(SyntaxNode child) {
		SyntaxNode parent = child.getParent();
		if (child instanceof LastCondFact || child instanceof NonLastCondFact) {
			while (!(parent instanceof LastCondTerm || parent instanceof NonLastCondTerm)) {
				parent = parent.getParent();
			}
			return parent;
		} else if (child instanceof LastCondTerm || child instanceof NonLastCondTerm) {
			while (!(parent instanceof Condition)) {
				parent = parent.getParent();
			}
			return parent;
		}
		throw new Error("Ovo pozivaj samo na CondFact i CondTerm");
	}

	public void visit(NonLastCondFact CF) {
		CondFact factor = CF.getCondFact();
		if (factor instanceof CondFactRelOp) {
			CondFactRelOp factorRelOp = (CondFactRelOp) factor;
			for (int op = 0; op < relOpClasses.length; ++op) {
				if (relOpClasses[op] == factorRelOp.getRelOp().getClass()) {
					myJumpAddr.put(CF, new Integer(Code.pc)); // Ovde je moj skokic
					Code.putFalseJump(op, Code.pc);
					break;
				}
			}
		} else {
			Code.load(new Obj(Obj.Con, "Poligraf", new Struct(Struct.Bool), 0, 100));
			myJumpAddr.put(CF, new Integer(Code.pc)); // Ovde je moj skokic
			Code.putFalseJump(Code.ne, Code.pc);
		}

		endAddr.put(CF, new Integer(Code.pc));
	}

	public void visit(LastCondFact CF) {
		CondFact factor = CF.getCondFact();
		if (factor instanceof CondFactRelOp) {
			CondFactRelOp factorRelOp = (CondFactRelOp) factor;
			for (int op = 0; op < relOpClasses.length; ++op) {
				if (relOpClasses[op] == factorRelOp.getRelOp().getClass()) {
					myJumpAddr.put(CF, new Integer(Code.pc)); // Ovde je moj skokic
					Code.putFalseJump(op, Code.pc);
					break;
				}
			}
		} else {
			Code.load(new Obj(Obj.Con, "Poligraf", new Struct(Struct.Bool), 0, 100));
			myJumpAddr.put(CF, new Integer(Code.pc)); // Ovde je moj skokic
			Code.putFalseJump(Code.ne, Code.pc);
		}

		endAddr.put(CF, new Integer(Code.pc));
	}

	public void visit(NonLastCondTerm CT) {
		myJumpAddr.put(CT, new Integer(Code.pc));
		Code.putJump(Code.pc);
		endAddr.put(CT, new Integer(Code.pc));
	}

	public void visit(LastCondTerm CT) {
		myJumpAddr.put(CT, new Integer(Code.pc));
		Code.putJump(Code.pc);
		endAddr.put(CT, new Integer(Code.pc));
	}

	public void visit(Condition CL) {
		myJumpAddr.put(CL, new Integer(Code.pc));
		Code.putJump(Code.pc);
		endAddr.put(CL, new Integer(Code.pc));
	}

	private class AddressFixer extends VisitorAdaptor {
		private int thenAddres;
		private int afterBodyAddress;

		public AddressFixer(int thenAddres, int afterBodyOrElseAddress) {
			this.thenAddres = thenAddres;
			this.afterBodyAddress = afterBodyOrElseAddress;
		}

		public void visit(NonLastCondFact CF) {
			ConditionList termActually = (ConditionList) getCondTreeParent(CF);
			substituteAddress(((Integer) myJumpAddr.get(CF)).intValue(),
					((Integer) endAddr.get(termActually)).intValue());
		}

		public void visit(LastCondFact CF) {
			ConditionList termActually = (ConditionList) getCondTreeParent(CF);
			substituteAddress(((Integer) myJumpAddr.get(CF)).intValue(),
					((Integer) endAddr.get(termActually)).intValue());
		}

		public void visit(NonLastCondTerm CT) {
			substituteAddress(((Integer) myJumpAddr.get(CT)).intValue(), thenAddres);
		}

		public void visit(LastCondTerm CT) {
			substituteAddress(((Integer) myJumpAddr.get(CT)).intValue(), thenAddres);
		}

		public void visit(Condition CL) {
			substituteAddress(((Integer) myJumpAddr.get(CL)).intValue(), afterBodyAddress);
		}

		private void substituteAddress(int addrToFix, int targetAddr) {
			int pcBackup = Code.pc;
			Code.pc = targetAddr;
			Code.fixup(addrToFix + 1);
			Code.pc = pcBackup;
		}
	}

	public void visit(IfStmt stmt) {
		Condition rootCondition = stmt.getCondition();
		int thenAddr = ((Integer) endAddr.get(rootCondition)).intValue();
		int afterBodyAddr = Code.pc;

		rootCondition.traverseBottomUp(new AddressFixer(thenAddr, afterBodyAddr));
	}

	public void visit(IfElseStmt stmt) {
		Condition rootCondition = stmt.getCondition();
		int thenAddr = ((Integer) endAddr.get(rootCondition)).intValue();
		int afterBodyAddr = Code.pc;

		int elseAddr = ((Integer) endAddr.get(stmt.getElseCheckpoint())).intValue();

		rootCondition.traverseBottomUp(new AddressFixer(thenAddr, elseAddr));

		// Jump out of the THEN branch, not to continue to ELSE
		int pcBackup = Code.pc;
		Code.pc = afterBodyAddr;
		Code.fixup(((Integer) myJumpAddr.get(stmt.getElseCheckpoint())).intValue() + 1);
		Code.pc = pcBackup;

	}

	public void visit(ElseCheckpoint checkpoint) {
		myJumpAddr.put(checkpoint, new Integer(Code.pc));
		Code.putJump(Code.pc); // Jump to after else
		endAddr.put(checkpoint, new Integer(Code.pc));
	}

	////////////////////////// DO WHILE START //////////////////////

	public void visit(DoWhileStmt stmt) {
		Condition rootCondition = stmt.getCondition();
		int thenAddr = ((Integer) endAddr.get(stmt.getDoCheckpoint())).intValue();
		int afterBodyAddr = Code.pc;

		rootCondition.traverseBottomUp(new AddressFixer(thenAddr, afterBodyAddr));

		StatementOrBlockOfStatements stmtBlock = stmt.getStatementOrBlockOfStatements();

		stmtBlock.traverseBottomUp(new BreakContinueFixer(thenAddr, afterBodyAddr));
	}

	public void visit(DoCheckpoint checkpoint) {
		endAddr.put(checkpoint, new Integer(Code.pc));
	}

	public void visit(BreakStmt stmt) {
		myJumpAddr.put(stmt, new Integer(Code.pc));
		Code.putJump(Code.pc); // Fake. Will be replaced by the end address of block.
	}

	public void visit(ContinueStmt stmt) {
		myJumpAddr.put(stmt, new Integer(Code.pc));
		Code.putJump(Code.pc); // Fake. Will be replaced by the beginning address of block.
	}

	private class BreakContinueFixer extends VisitorAdaptor {
		private int blockBegin;
		private int blockEnd;

		public BreakContinueFixer(int blockBegin, int blockEnd) {
			this.blockBegin = blockBegin;
			this.blockEnd = blockEnd;
		}

		public void visit(BreakStmt stmt) {
			if (myJumpAddr.containsKey(stmt)) {
				int statementAddress = ((Integer) myJumpAddr.remove(stmt)).intValue(); // Remove it so nesting is not
																						// interrupted
				substituteAddress(statementAddress, blockEnd);
			}
		}

		public void visit(ContinueStmt stmt) {
			if (myJumpAddr.containsKey(stmt)) {
				int statementAddress = ((Integer) myJumpAddr.remove(stmt)).intValue(); // Remove it so nesting is not
																						// interrupted
				substituteAddress(statementAddress, blockBegin);
			}
		}

		private void substituteAddress(int addrToFix, int targetAddr) {
			int pcBackup = Code.pc;
			Code.pc = targetAddr;
			Code.fixup(addrToFix + 1);
			Code.pc = pcBackup;
		}
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

			if (designator.obj.getKind() == Obj.Elem) {
				DesignationList dl = designator.getDesignationList();
				if (dl instanceof DesignationArrayAccess)
					dl = ((DesignationArrayAccess) dl).getDesignationList();
				if (dl instanceof DesignationObjectAccess)
					dl = ((DesignationObjectAccess) dl).getDesignationList();
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

			Code.load(designator.obj);

		}
		if (DesignatorStatementAssign.class == parent.getClass()) { // Ako je sa leve strane izraza...
			if (designator.obj.getKind() == Obj.Fld)
				Code.load(new Obj(Obj.Var, designator.obj.getName(), designator.obj.getType(),
						Integer.parseInt(designator.obj.getName()), // Kroz name se propagira originalna adresa maticnog
																	// objekta
						designator.obj.getLevel()));

			DesignationList dl = designator.getDesignationList();
			if (dl instanceof DesignationArrayAccess)
				dl = ((DesignationArrayAccess) dl).getDesignationList();
			if (dl instanceof DesignationObjectAccess)
				dl = ((DesignationObjectAccess) dl).getDesignationList();
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
		
		if (methodsWithVarArgs.containsKey(methObj.getName())) {			
			String varArgsType = methodsWithVarArgs.get(methObj.getName());
			Obj varArgsArray = varArgsType.contentEquals("char") ? charVarArgsArrayBuiltIn : intVarArgsArrayBuiltIn;

			int fixedArgs = methObj.getLevel() - 1;// -1 jer je poslednji zapravo varargs
			int realArgs = countRealArgs(stmt.getOptionalActPars());
			int numOfVarArgs = realArgs - fixedArgs;
			
			Code.loadConst(numOfVarArgs); // Max number of var args
			Code.put(Code.newarray);
			Code.put(varArgsType.contentEquals("char") ? 0 : 1);
			Code.store(varArgsArray);
			
			
			for(int i = numOfVarArgs - 1; i>=0; --i) {
				Code.load(varArgsArray);
				Code.put(Code.dup_x1);
				Code.put(Code.pop);
				
				Code.loadConst(i);
				Code.put(Code.dup_x1);
				Code.put(Code.pop);
				
				Code.put(varArgsType.contentEquals("char")? Code.bastore : Code.astore);
			}
			
			Code.load(varArgsArray);
		}
		
		int offset = methObj.getAdr() - Code.pc;

		Code.put(Code.call);
		Code.put2(offset);
	}

	public Obj intVarArgsArrayBuiltIn;
	public Obj charVarArgsArrayBuiltIn;

	public void visit(FactorDesignatorCall funcCall) {
		if (((DesignatorIdent) funcCall.getDesignator()).getDesignatorName().equals("len")) {
			Code.put(Code.arraylength);
			return;
		}
		if (((DesignatorIdent) funcCall.getDesignator()).getDesignatorName().equals("ord")) {
			// Vec je na steku lol
			return;
		}
		if (((DesignatorIdent) funcCall.getDesignator()).getDesignatorName().equals("chr")) {
			// Vec je na steku lol
			return;
		}

		Obj methObj = funcCall.getDesignator().obj;

		if (methodsWithVarArgs.containsKey(methObj.getName())) {			
			String varArgsType = methodsWithVarArgs.get(methObj.getName());
			Obj varArgsArray = varArgsType.contentEquals("char") ? charVarArgsArrayBuiltIn : intVarArgsArrayBuiltIn;

			int fixedArgs = methObj.getLevel() - 1;// -1 jer je poslednji zapravo varargs
			int realArgs = countRealArgs(funcCall.getOptionalActPars());
			int numOfVarArgs = realArgs - fixedArgs;
			
			Code.loadConst(numOfVarArgs); // Max number of var args
			Code.put(Code.newarray);
			Code.put(varArgsType.contentEquals("char") ? 0 : 1);
			Code.store(varArgsArray);
			
			
			for(int i = numOfVarArgs - 1; i >= 0; --i) {
				Code.load(varArgsArray);
				Code.put(Code.dup_x1);
				Code.put(Code.pop);
				
				Code.loadConst(i);
				Code.put(Code.dup_x1);
				Code.put(Code.pop);
				
				Code.put(varArgsType.contentEquals("char")? Code.bastore : Code.astore);
			}
			
			Code.load(varArgsArray);
		}

		int offset = methObj.getAdr() - Code.pc;

		Code.put(Code.call);
		Code.put2(offset);
	}

	private int countRealArgs(OptionalActPars oap) {
		if (oap instanceof NoActPars)
			return 0;
		ActPars actPars = ((YesActPars) oap).getActPars();
		int number = 1;
		while (actPars instanceof NonLastExprActPar) {
			number++;
			actPars = ((NonLastExprActPar) actPars).getActPars();
		}
		return number;
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
		SyntaxNode grandparent = dae.getParent().getParent();
		if (grandparent instanceof DesignationObjectAccess) {
			Obj parentObj = (((DesignationArrayAccess) dae.getParent())).obj;
			Code.load(new Obj(Obj.Var, parentObj.getName(), parentObj.getType(), Integer.parseInt(parentObj.getName()),
					parentObj.getLevel()));

			Code.load(parentObj);
			return; // If it is a part of an object, don't
		}

		SyntaxNode ancestor = dae.getParent();
		while (!(ancestor instanceof DesignatorIdent))
			ancestor = ancestor.getParent();

		Obj designatorObj = ((DesignatorIdent) ancestor).obj;
		Code.load(new Obj(Obj.Var, "ArrayDesignationAccess", designatorObj.getType(), designatorObj.getAdr(),
				designatorObj.getLevel()));
	}

}

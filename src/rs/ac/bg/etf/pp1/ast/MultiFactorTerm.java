// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:12:4


package rs.ac.bg.etf.pp1.ast;

public class MultiFactorTerm extends Term {

    private MultipleFactorList MultipleFactorList;
    private MulOp MulOp;
    private Factor Factor;

    public MultiFactorTerm (MultipleFactorList MultipleFactorList, MulOp MulOp, Factor Factor) {
        this.MultipleFactorList=MultipleFactorList;
        if(MultipleFactorList!=null) MultipleFactorList.setParent(this);
        this.MulOp=MulOp;
        if(MulOp!=null) MulOp.setParent(this);
        this.Factor=Factor;
        if(Factor!=null) Factor.setParent(this);
    }

    public MultipleFactorList getMultipleFactorList() {
        return MultipleFactorList;
    }

    public void setMultipleFactorList(MultipleFactorList MultipleFactorList) {
        this.MultipleFactorList=MultipleFactorList;
    }

    public MulOp getMulOp() {
        return MulOp;
    }

    public void setMulOp(MulOp MulOp) {
        this.MulOp=MulOp;
    }

    public Factor getFactor() {
        return Factor;
    }

    public void setFactor(Factor Factor) {
        this.Factor=Factor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleFactorList!=null) MultipleFactorList.accept(visitor);
        if(MulOp!=null) MulOp.accept(visitor);
        if(Factor!=null) Factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleFactorList!=null) MultipleFactorList.traverseTopDown(visitor);
        if(MulOp!=null) MulOp.traverseTopDown(visitor);
        if(Factor!=null) Factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleFactorList!=null) MultipleFactorList.traverseBottomUp(visitor);
        if(MulOp!=null) MulOp.traverseBottomUp(visitor);
        if(Factor!=null) Factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultiFactorTerm(\n");

        if(MultipleFactorList!=null)
            buffer.append(MultipleFactorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MulOp!=null)
            buffer.append(MulOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Factor!=null)
            buffer.append(Factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultiFactorTerm]");
        return buffer.toString();
    }
}

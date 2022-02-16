// generated with ast extension for cup
// version 0.8
// 16/1/2022 1:43:53


package rs.ac.bg.etf.pp1.ast;

public class MultiFactorTerm extends Term {

    private Factor Factor;
    private MultipleFactorList MultipleFactorList;

    public MultiFactorTerm (Factor Factor, MultipleFactorList MultipleFactorList) {
        this.Factor=Factor;
        if(Factor!=null) Factor.setParent(this);
        this.MultipleFactorList=MultipleFactorList;
        if(MultipleFactorList!=null) MultipleFactorList.setParent(this);
    }

    public Factor getFactor() {
        return Factor;
    }

    public void setFactor(Factor Factor) {
        this.Factor=Factor;
    }

    public MultipleFactorList getMultipleFactorList() {
        return MultipleFactorList;
    }

    public void setMultipleFactorList(MultipleFactorList MultipleFactorList) {
        this.MultipleFactorList=MultipleFactorList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Factor!=null) Factor.accept(visitor);
        if(MultipleFactorList!=null) MultipleFactorList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Factor!=null) Factor.traverseTopDown(visitor);
        if(MultipleFactorList!=null) MultipleFactorList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Factor!=null) Factor.traverseBottomUp(visitor);
        if(MultipleFactorList!=null) MultipleFactorList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultiFactorTerm(\n");

        if(Factor!=null)
            buffer.append(Factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultipleFactorList!=null)
            buffer.append(MultipleFactorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultiFactorTerm]");
        return buffer.toString();
    }
}

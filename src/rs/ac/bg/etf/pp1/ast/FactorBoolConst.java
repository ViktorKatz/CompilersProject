// generated with ast extension for cup
// version 0.8
// 16/1/2022 20:1:4


package rs.ac.bg.etf.pp1.ast;

public class FactorBoolConst extends Factor {

    private BoolValue BoolValue;

    public FactorBoolConst (BoolValue BoolValue) {
        this.BoolValue=BoolValue;
        if(BoolValue!=null) BoolValue.setParent(this);
    }

    public BoolValue getBoolValue() {
        return BoolValue;
    }

    public void setBoolValue(BoolValue BoolValue) {
        this.BoolValue=BoolValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(BoolValue!=null) BoolValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(BoolValue!=null) BoolValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(BoolValue!=null) BoolValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorBoolConst(\n");

        if(BoolValue!=null)
            buffer.append(BoolValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorBoolConst]");
        return buffer.toString();
    }
}

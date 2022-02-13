// generated with ast extension for cup
// version 0.8
// 13/1/2022 19:11:26


package rs.ac.bg.etf.pp1.ast;

public class ConstBoolDecl extends ConstDecl {

    private String I1;
    private BoolValue BoolValue;

    public ConstBoolDecl (String I1, BoolValue BoolValue) {
        this.I1=I1;
        this.BoolValue=BoolValue;
        if(BoolValue!=null) BoolValue.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
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
        buffer.append("ConstBoolDecl(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(BoolValue!=null)
            buffer.append(BoolValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstBoolDecl]");
        return buffer.toString();
    }
}

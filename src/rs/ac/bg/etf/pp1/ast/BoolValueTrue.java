// generated with ast extension for cup
// version 0.8
// 15/1/2022 3:19:3


package rs.ac.bg.etf.pp1.ast;

public class BoolValueTrue extends BoolValue {

    public BoolValueTrue () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BoolValueTrue(\n");

        buffer.append(tab);
        buffer.append(") [BoolValueTrue]");
        return buffer.toString();
    }
}

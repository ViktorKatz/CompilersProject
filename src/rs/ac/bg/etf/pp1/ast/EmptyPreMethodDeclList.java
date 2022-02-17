// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:12:4


package rs.ac.bg.etf.pp1.ast;

public class EmptyPreMethodDeclList extends PreMethodDeclList {

    public EmptyPreMethodDeclList () {
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
        buffer.append("EmptyPreMethodDeclList(\n");

        buffer.append(tab);
        buffer.append(") [EmptyPreMethodDeclList]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 13/1/2022 19:11:26


package rs.ac.bg.etf.pp1.ast;

public class NoMethodDecls extends MethodDeclList {

    public NoMethodDecls () {
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
        buffer.append("NoMethodDecls(\n");

        buffer.append(tab);
        buffer.append(") [NoMethodDecls]");
        return buffer.toString();
    }
}

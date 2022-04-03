// generated with ast extension for cup
// version 0.8
// 4/3/2022 1:14:14


package rs.ac.bg.etf.pp1.ast;

public class TypeNonPrimitive extends Type {

    private String className;

    public TypeNonPrimitive (String className) {
        this.className=className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className=className;
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
        buffer.append("TypeNonPrimitive(\n");

        buffer.append(" "+tab+className);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypeNonPrimitive]");
        return buffer.toString();
    }
}

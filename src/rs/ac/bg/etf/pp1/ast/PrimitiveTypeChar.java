// generated with ast extension for cup
// version 0.8
// 16/1/2022 4:41:1


package rs.ac.bg.etf.pp1.ast;

public class PrimitiveTypeChar extends PrimitiveType {

    public PrimitiveTypeChar () {
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
        buffer.append("PrimitiveTypeChar(\n");

        buffer.append(tab);
        buffer.append(") [PrimitiveTypeChar]");
        return buffer.toString();
    }
}

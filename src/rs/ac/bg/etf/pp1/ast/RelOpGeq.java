// generated with ast extension for cup
// version 0.8
// 16/1/2022 1:45:32


package rs.ac.bg.etf.pp1.ast;

public class RelOpGeq extends RelOp {

    public RelOpGeq () {
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
        buffer.append("RelOpGeq(\n");

        buffer.append(tab);
        buffer.append(") [RelOpGeq]");
        return buffer.toString();
    }
}

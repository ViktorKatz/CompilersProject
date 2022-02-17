// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:12:4


package rs.ac.bg.etf.pp1.ast;

public class ProgName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String progNameString;

    public ProgName (String progNameString) {
        this.progNameString=progNameString;
    }

    public String getProgNameString() {
        return progNameString;
    }

    public void setProgNameString(String progNameString) {
        this.progNameString=progNameString;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("ProgName(\n");

        buffer.append(" "+tab+progNameString);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgName]");
        return buffer.toString();
    }
}

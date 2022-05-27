// generated with ast extension for cup
// version 0.8
// 28/4/2022 1:45:11


package rs.ac.bg.etf.pp1.ast;

public class RecordIdent implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String recordTypeName;

    public RecordIdent (String recordTypeName) {
        this.recordTypeName=recordTypeName;
    }

    public String getRecordTypeName() {
        return recordTypeName;
    }

    public void setRecordTypeName(String recordTypeName) {
        this.recordTypeName=recordTypeName;
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
        buffer.append("RecordIdent(\n");

        buffer.append(" "+tab+recordTypeName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RecordIdent]");
        return buffer.toString();
    }
}

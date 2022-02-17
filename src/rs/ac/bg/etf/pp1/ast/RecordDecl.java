// generated with ast extension for cup
// version 0.8
// 17/1/2022 10:34:3


package rs.ac.bg.etf.pp1.ast;

public class RecordDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private RecordIdent RecordIdent;
    private VarDeclsList VarDeclsList;

    public RecordDecl (RecordIdent RecordIdent, VarDeclsList VarDeclsList) {
        this.RecordIdent=RecordIdent;
        if(RecordIdent!=null) RecordIdent.setParent(this);
        this.VarDeclsList=VarDeclsList;
        if(VarDeclsList!=null) VarDeclsList.setParent(this);
    }

    public RecordIdent getRecordIdent() {
        return RecordIdent;
    }

    public void setRecordIdent(RecordIdent RecordIdent) {
        this.RecordIdent=RecordIdent;
    }

    public VarDeclsList getVarDeclsList() {
        return VarDeclsList;
    }

    public void setVarDeclsList(VarDeclsList VarDeclsList) {
        this.VarDeclsList=VarDeclsList;
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
        if(RecordIdent!=null) RecordIdent.accept(visitor);
        if(VarDeclsList!=null) VarDeclsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RecordIdent!=null) RecordIdent.traverseTopDown(visitor);
        if(VarDeclsList!=null) VarDeclsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RecordIdent!=null) RecordIdent.traverseBottomUp(visitor);
        if(VarDeclsList!=null) VarDeclsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RecordDecl(\n");

        if(RecordIdent!=null)
            buffer.append(RecordIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclsList!=null)
            buffer.append(VarDeclsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RecordDecl]");
        return buffer.toString();
    }
}

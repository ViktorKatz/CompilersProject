// generated with ast extension for cup
// version 0.8
// 16/1/2022 20:1:3


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private PrimitiveType PrimitiveType;
    private ConstDeclArray ConstDeclArray;

    public ConstDecl (PrimitiveType PrimitiveType, ConstDeclArray ConstDeclArray) {
        this.PrimitiveType=PrimitiveType;
        if(PrimitiveType!=null) PrimitiveType.setParent(this);
        this.ConstDeclArray=ConstDeclArray;
        if(ConstDeclArray!=null) ConstDeclArray.setParent(this);
    }

    public PrimitiveType getPrimitiveType() {
        return PrimitiveType;
    }

    public void setPrimitiveType(PrimitiveType PrimitiveType) {
        this.PrimitiveType=PrimitiveType;
    }

    public ConstDeclArray getConstDeclArray() {
        return ConstDeclArray;
    }

    public void setConstDeclArray(ConstDeclArray ConstDeclArray) {
        this.ConstDeclArray=ConstDeclArray;
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
        if(PrimitiveType!=null) PrimitiveType.accept(visitor);
        if(ConstDeclArray!=null) ConstDeclArray.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(PrimitiveType!=null) PrimitiveType.traverseTopDown(visitor);
        if(ConstDeclArray!=null) ConstDeclArray.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(PrimitiveType!=null) PrimitiveType.traverseBottomUp(visitor);
        if(ConstDeclArray!=null) ConstDeclArray.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(PrimitiveType!=null)
            buffer.append(PrimitiveType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclArray!=null)
            buffer.append(ConstDeclArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}

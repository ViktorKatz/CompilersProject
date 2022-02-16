// generated with ast extension for cup
// version 0.8
// 16/1/2022 1:45:32


package rs.ac.bg.etf.pp1.ast;

public class ClassConstructor implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private OptionalVarDeclsList OptionalVarDeclsList;
    private BlockOfStatements BlockOfStatements;

    public ClassConstructor (String I1, OptionalVarDeclsList OptionalVarDeclsList, BlockOfStatements BlockOfStatements) {
        this.I1=I1;
        this.OptionalVarDeclsList=OptionalVarDeclsList;
        if(OptionalVarDeclsList!=null) OptionalVarDeclsList.setParent(this);
        this.BlockOfStatements=BlockOfStatements;
        if(BlockOfStatements!=null) BlockOfStatements.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public OptionalVarDeclsList getOptionalVarDeclsList() {
        return OptionalVarDeclsList;
    }

    public void setOptionalVarDeclsList(OptionalVarDeclsList OptionalVarDeclsList) {
        this.OptionalVarDeclsList=OptionalVarDeclsList;
    }

    public BlockOfStatements getBlockOfStatements() {
        return BlockOfStatements;
    }

    public void setBlockOfStatements(BlockOfStatements BlockOfStatements) {
        this.BlockOfStatements=BlockOfStatements;
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
        if(OptionalVarDeclsList!=null) OptionalVarDeclsList.accept(visitor);
        if(BlockOfStatements!=null) BlockOfStatements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionalVarDeclsList!=null) OptionalVarDeclsList.traverseTopDown(visitor);
        if(BlockOfStatements!=null) BlockOfStatements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionalVarDeclsList!=null) OptionalVarDeclsList.traverseBottomUp(visitor);
        if(BlockOfStatements!=null) BlockOfStatements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassConstructor(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(OptionalVarDeclsList!=null)
            buffer.append(OptionalVarDeclsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(BlockOfStatements!=null)
            buffer.append(BlockOfStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassConstructor]");
        return buffer.toString();
    }
}

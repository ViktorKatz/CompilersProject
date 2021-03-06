// generated with ast extension for cup
// version 0.8
// 28/4/2022 1:45:11


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodSignature MethodSignature;
    private FormPars FormPars;
    private OptionalVarDeclsList OptionalVarDeclsList;
    private BlockOfStatements BlockOfStatements;

    public MethodDecl (MethodSignature MethodSignature, FormPars FormPars, OptionalVarDeclsList OptionalVarDeclsList, BlockOfStatements BlockOfStatements) {
        this.MethodSignature=MethodSignature;
        if(MethodSignature!=null) MethodSignature.setParent(this);
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.OptionalVarDeclsList=OptionalVarDeclsList;
        if(OptionalVarDeclsList!=null) OptionalVarDeclsList.setParent(this);
        this.BlockOfStatements=BlockOfStatements;
        if(BlockOfStatements!=null) BlockOfStatements.setParent(this);
    }

    public MethodSignature getMethodSignature() {
        return MethodSignature;
    }

    public void setMethodSignature(MethodSignature MethodSignature) {
        this.MethodSignature=MethodSignature;
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
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
        if(MethodSignature!=null) MethodSignature.accept(visitor);
        if(FormPars!=null) FormPars.accept(visitor);
        if(OptionalVarDeclsList!=null) OptionalVarDeclsList.accept(visitor);
        if(BlockOfStatements!=null) BlockOfStatements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodSignature!=null) MethodSignature.traverseTopDown(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(OptionalVarDeclsList!=null) OptionalVarDeclsList.traverseTopDown(visitor);
        if(BlockOfStatements!=null) BlockOfStatements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodSignature!=null) MethodSignature.traverseBottomUp(visitor);
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(OptionalVarDeclsList!=null) OptionalVarDeclsList.traverseBottomUp(visitor);
        if(BlockOfStatements!=null) BlockOfStatements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodSignature!=null)
            buffer.append(MethodSignature.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
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
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}

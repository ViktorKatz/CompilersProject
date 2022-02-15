// generated with ast extension for cup
// version 0.8
// 15/1/2022 3:19:3


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private ClassName ClassName;
    private ClassExtension ClassExtension;
    private VarDeclsList VarDeclsList;
    private ClassBody ClassBody;

    public ClassDecl (ClassName ClassName, ClassExtension ClassExtension, VarDeclsList VarDeclsList, ClassBody ClassBody) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.ClassExtension=ClassExtension;
        if(ClassExtension!=null) ClassExtension.setParent(this);
        this.VarDeclsList=VarDeclsList;
        if(VarDeclsList!=null) VarDeclsList.setParent(this);
        this.ClassBody=ClassBody;
        if(ClassBody!=null) ClassBody.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
    }

    public ClassExtension getClassExtension() {
        return ClassExtension;
    }

    public void setClassExtension(ClassExtension ClassExtension) {
        this.ClassExtension=ClassExtension;
    }

    public VarDeclsList getVarDeclsList() {
        return VarDeclsList;
    }

    public void setVarDeclsList(VarDeclsList VarDeclsList) {
        this.VarDeclsList=VarDeclsList;
    }

    public ClassBody getClassBody() {
        return ClassBody;
    }

    public void setClassBody(ClassBody ClassBody) {
        this.ClassBody=ClassBody;
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
        if(ClassName!=null) ClassName.accept(visitor);
        if(ClassExtension!=null) ClassExtension.accept(visitor);
        if(VarDeclsList!=null) VarDeclsList.accept(visitor);
        if(ClassBody!=null) ClassBody.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(ClassExtension!=null) ClassExtension.traverseTopDown(visitor);
        if(VarDeclsList!=null) VarDeclsList.traverseTopDown(visitor);
        if(ClassBody!=null) ClassBody.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(ClassExtension!=null) ClassExtension.traverseBottomUp(visitor);
        if(VarDeclsList!=null) VarDeclsList.traverseBottomUp(visitor);
        if(ClassBody!=null) ClassBody.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassExtension!=null)
            buffer.append(ClassExtension.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclsList!=null)
            buffer.append(VarDeclsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassBody!=null)
            buffer.append(ClassBody.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}

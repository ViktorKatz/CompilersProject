// generated with ast extension for cup
// version 0.8
// 28/4/2022 1:45:11


package rs.ac.bg.etf.pp1.ast;

public class NonEmptyPreMethodDeclList extends PreMethodDeclList {

    private PreMethodDeclList PreMethodDeclList;
    private PreMethodDecl PreMethodDecl;

    public NonEmptyPreMethodDeclList (PreMethodDeclList PreMethodDeclList, PreMethodDecl PreMethodDecl) {
        this.PreMethodDeclList=PreMethodDeclList;
        if(PreMethodDeclList!=null) PreMethodDeclList.setParent(this);
        this.PreMethodDecl=PreMethodDecl;
        if(PreMethodDecl!=null) PreMethodDecl.setParent(this);
    }

    public PreMethodDeclList getPreMethodDeclList() {
        return PreMethodDeclList;
    }

    public void setPreMethodDeclList(PreMethodDeclList PreMethodDeclList) {
        this.PreMethodDeclList=PreMethodDeclList;
    }

    public PreMethodDecl getPreMethodDecl() {
        return PreMethodDecl;
    }

    public void setPreMethodDecl(PreMethodDecl PreMethodDecl) {
        this.PreMethodDecl=PreMethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(PreMethodDeclList!=null) PreMethodDeclList.accept(visitor);
        if(PreMethodDecl!=null) PreMethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(PreMethodDeclList!=null) PreMethodDeclList.traverseTopDown(visitor);
        if(PreMethodDecl!=null) PreMethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(PreMethodDeclList!=null) PreMethodDeclList.traverseBottomUp(visitor);
        if(PreMethodDecl!=null) PreMethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NonEmptyPreMethodDeclList(\n");

        if(PreMethodDeclList!=null)
            buffer.append(PreMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(PreMethodDecl!=null)
            buffer.append(PreMethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NonEmptyPreMethodDeclList]");
        return buffer.toString();
    }
}

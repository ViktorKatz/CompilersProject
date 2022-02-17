// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:12:4


package rs.ac.bg.etf.pp1.ast;

public class YesClassBodyYesConst extends ClassBody {

    private ClassConstructor ClassConstructor;
    private MethodDeclList MethodDeclList;

    public YesClassBodyYesConst (ClassConstructor ClassConstructor, MethodDeclList MethodDeclList) {
        this.ClassConstructor=ClassConstructor;
        if(ClassConstructor!=null) ClassConstructor.setParent(this);
        this.MethodDeclList=MethodDeclList;
        if(MethodDeclList!=null) MethodDeclList.setParent(this);
    }

    public ClassConstructor getClassConstructor() {
        return ClassConstructor;
    }

    public void setClassConstructor(ClassConstructor ClassConstructor) {
        this.ClassConstructor=ClassConstructor;
    }

    public MethodDeclList getMethodDeclList() {
        return MethodDeclList;
    }

    public void setMethodDeclList(MethodDeclList MethodDeclList) {
        this.MethodDeclList=MethodDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassConstructor!=null) ClassConstructor.accept(visitor);
        if(MethodDeclList!=null) MethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassConstructor!=null) ClassConstructor.traverseTopDown(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassConstructor!=null) ClassConstructor.traverseBottomUp(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("YesClassBodyYesConst(\n");

        if(ClassConstructor!=null)
            buffer.append(ClassConstructor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclList!=null)
            buffer.append(MethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [YesClassBodyYesConst]");
        return buffer.toString();
    }
}

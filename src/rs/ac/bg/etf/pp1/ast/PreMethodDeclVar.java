// generated with ast extension for cup
// version 0.8
// 16/1/2022 1:45:32


package rs.ac.bg.etf.pp1.ast;

public class PreMethodDeclVar extends PreMethodDecl {

    private VarDecls VarDecls;

    public PreMethodDeclVar (VarDecls VarDecls) {
        this.VarDecls=VarDecls;
        if(VarDecls!=null) VarDecls.setParent(this);
    }

    public VarDecls getVarDecls() {
        return VarDecls;
    }

    public void setVarDecls(VarDecls VarDecls) {
        this.VarDecls=VarDecls;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDecls!=null) VarDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDecls!=null) VarDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDecls!=null) VarDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PreMethodDeclVar(\n");

        if(VarDecls!=null)
            buffer.append(VarDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PreMethodDeclVar]");
        return buffer.toString();
    }
}

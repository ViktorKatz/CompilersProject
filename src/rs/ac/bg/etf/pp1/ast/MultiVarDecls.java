// generated with ast extension for cup
// version 0.8
// 14/1/2022 23:5:49


package rs.ac.bg.etf.pp1.ast;

public class MultiVarDecls extends VarDeclsList {

    private VarDeclsList VarDeclsList;
    private VarDecls VarDecls;

    public MultiVarDecls (VarDeclsList VarDeclsList, VarDecls VarDecls) {
        this.VarDeclsList=VarDeclsList;
        if(VarDeclsList!=null) VarDeclsList.setParent(this);
        this.VarDecls=VarDecls;
        if(VarDecls!=null) VarDecls.setParent(this);
    }

    public VarDeclsList getVarDeclsList() {
        return VarDeclsList;
    }

    public void setVarDeclsList(VarDeclsList VarDeclsList) {
        this.VarDeclsList=VarDeclsList;
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
        if(VarDeclsList!=null) VarDeclsList.accept(visitor);
        if(VarDecls!=null) VarDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclsList!=null) VarDeclsList.traverseTopDown(visitor);
        if(VarDecls!=null) VarDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclsList!=null) VarDeclsList.traverseBottomUp(visitor);
        if(VarDecls!=null) VarDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultiVarDecls(\n");

        if(VarDeclsList!=null)
            buffer.append(VarDeclsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecls!=null)
            buffer.append(VarDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultiVarDecls]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 16/1/2022 4:41:1


package rs.ac.bg.etf.pp1.ast;

public class DesignationArrayAccess extends DesignationList {

    private Expr Expr;
    private DesignationList DesignationList;

    public DesignationArrayAccess (Expr Expr, DesignationList DesignationList) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.DesignationList=DesignationList;
        if(DesignationList!=null) DesignationList.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public DesignationList getDesignationList() {
        return DesignationList;
    }

    public void setDesignationList(DesignationList DesignationList) {
        this.DesignationList=DesignationList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(DesignationList!=null) DesignationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(DesignationList!=null) DesignationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(DesignationList!=null) DesignationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignationArrayAccess(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignationList!=null)
            buffer.append(DesignationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignationArrayAccess]");
        return buffer.toString();
    }
}

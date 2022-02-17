// generated with ast extension for cup
// version 0.8
// 17/1/2022 10:34:3


package rs.ac.bg.etf.pp1.ast;

public class DesignationArrayAccess extends DesignationList {

    private DesignationArrayEntry DesignationArrayEntry;
    private Expr Expr;
    private DesignationArrayLoad DesignationArrayLoad;
    private DesignationList DesignationList;

    public DesignationArrayAccess (DesignationArrayEntry DesignationArrayEntry, Expr Expr, DesignationArrayLoad DesignationArrayLoad, DesignationList DesignationList) {
        this.DesignationArrayEntry=DesignationArrayEntry;
        if(DesignationArrayEntry!=null) DesignationArrayEntry.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.DesignationArrayLoad=DesignationArrayLoad;
        if(DesignationArrayLoad!=null) DesignationArrayLoad.setParent(this);
        this.DesignationList=DesignationList;
        if(DesignationList!=null) DesignationList.setParent(this);
    }

    public DesignationArrayEntry getDesignationArrayEntry() {
        return DesignationArrayEntry;
    }

    public void setDesignationArrayEntry(DesignationArrayEntry DesignationArrayEntry) {
        this.DesignationArrayEntry=DesignationArrayEntry;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public DesignationArrayLoad getDesignationArrayLoad() {
        return DesignationArrayLoad;
    }

    public void setDesignationArrayLoad(DesignationArrayLoad DesignationArrayLoad) {
        this.DesignationArrayLoad=DesignationArrayLoad;
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
        if(DesignationArrayEntry!=null) DesignationArrayEntry.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(DesignationArrayLoad!=null) DesignationArrayLoad.accept(visitor);
        if(DesignationList!=null) DesignationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignationArrayEntry!=null) DesignationArrayEntry.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(DesignationArrayLoad!=null) DesignationArrayLoad.traverseTopDown(visitor);
        if(DesignationList!=null) DesignationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignationArrayEntry!=null) DesignationArrayEntry.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(DesignationArrayLoad!=null) DesignationArrayLoad.traverseBottomUp(visitor);
        if(DesignationList!=null) DesignationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignationArrayAccess(\n");

        if(DesignationArrayEntry!=null)
            buffer.append(DesignationArrayEntry.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignationArrayLoad!=null)
            buffer.append(DesignationArrayLoad.toString("  "+tab));
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

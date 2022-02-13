// generated with ast extension for cup
// version 0.8
// 13/1/2022 19:11:26


package rs.ac.bg.etf.pp1.ast;

public class DesignatorThis extends Designator {

    private DesignationList DesignationList;

    public DesignatorThis (DesignationList DesignationList) {
        this.DesignationList=DesignationList;
        if(DesignationList!=null) DesignationList.setParent(this);
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
        if(DesignationList!=null) DesignationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignationList!=null) DesignationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignationList!=null) DesignationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorThis(\n");

        if(DesignationList!=null)
            buffer.append(DesignationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorThis]");
        return buffer.toString();
    }
}

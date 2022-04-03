// generated with ast extension for cup
// version 0.8
// 4/3/2022 1:14:14


package rs.ac.bg.etf.pp1.ast;

public class MinusMultiAddOpExpr extends Expr {

    private FirstTermNegative FirstTermNegative;
    private AddOp AddOp;
    private AdditionTermList AdditionTermList;

    public MinusMultiAddOpExpr (FirstTermNegative FirstTermNegative, AddOp AddOp, AdditionTermList AdditionTermList) {
        this.FirstTermNegative=FirstTermNegative;
        if(FirstTermNegative!=null) FirstTermNegative.setParent(this);
        this.AddOp=AddOp;
        if(AddOp!=null) AddOp.setParent(this);
        this.AdditionTermList=AdditionTermList;
        if(AdditionTermList!=null) AdditionTermList.setParent(this);
    }

    public FirstTermNegative getFirstTermNegative() {
        return FirstTermNegative;
    }

    public void setFirstTermNegative(FirstTermNegative FirstTermNegative) {
        this.FirstTermNegative=FirstTermNegative;
    }

    public AddOp getAddOp() {
        return AddOp;
    }

    public void setAddOp(AddOp AddOp) {
        this.AddOp=AddOp;
    }

    public AdditionTermList getAdditionTermList() {
        return AdditionTermList;
    }

    public void setAdditionTermList(AdditionTermList AdditionTermList) {
        this.AdditionTermList=AdditionTermList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FirstTermNegative!=null) FirstTermNegative.accept(visitor);
        if(AddOp!=null) AddOp.accept(visitor);
        if(AdditionTermList!=null) AdditionTermList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FirstTermNegative!=null) FirstTermNegative.traverseTopDown(visitor);
        if(AddOp!=null) AddOp.traverseTopDown(visitor);
        if(AdditionTermList!=null) AdditionTermList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FirstTermNegative!=null) FirstTermNegative.traverseBottomUp(visitor);
        if(AddOp!=null) AddOp.traverseBottomUp(visitor);
        if(AdditionTermList!=null) AdditionTermList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MinusMultiAddOpExpr(\n");

        if(FirstTermNegative!=null)
            buffer.append(FirstTermNegative.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddOp!=null)
            buffer.append(AddOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AdditionTermList!=null)
            buffer.append(AdditionTermList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MinusMultiAddOpExpr]");
        return buffer.toString();
    }
}

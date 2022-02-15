// generated with ast extension for cup
// version 0.8
// 15/1/2022 0:4:49


package rs.ac.bg.etf.pp1.ast;

public class LastFactor extends MultipleFactorList {

    private MulOp MulOp;
    private Factor Factor;

    public LastFactor (MulOp MulOp, Factor Factor) {
        this.MulOp=MulOp;
        if(MulOp!=null) MulOp.setParent(this);
        this.Factor=Factor;
        if(Factor!=null) Factor.setParent(this);
    }

    public MulOp getMulOp() {
        return MulOp;
    }

    public void setMulOp(MulOp MulOp) {
        this.MulOp=MulOp;
    }

    public Factor getFactor() {
        return Factor;
    }

    public void setFactor(Factor Factor) {
        this.Factor=Factor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MulOp!=null) MulOp.accept(visitor);
        if(Factor!=null) Factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MulOp!=null) MulOp.traverseTopDown(visitor);
        if(Factor!=null) Factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MulOp!=null) MulOp.traverseBottomUp(visitor);
        if(Factor!=null) Factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LastFactor(\n");

        if(MulOp!=null)
            buffer.append(MulOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Factor!=null)
            buffer.append(Factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LastFactor]");
        return buffer.toString();
    }
}

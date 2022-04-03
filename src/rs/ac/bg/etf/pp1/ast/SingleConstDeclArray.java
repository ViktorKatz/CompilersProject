// generated with ast extension for cup
// version 0.8
// 4/3/2022 1:14:14


package rs.ac.bg.etf.pp1.ast;

public class SingleConstDeclArray extends ConstDeclArray {

    private String constIdent;
    private ConstExpr ConstExpr;

    public SingleConstDeclArray (String constIdent, ConstExpr ConstExpr) {
        this.constIdent=constIdent;
        this.ConstExpr=ConstExpr;
        if(ConstExpr!=null) ConstExpr.setParent(this);
    }

    public String getConstIdent() {
        return constIdent;
    }

    public void setConstIdent(String constIdent) {
        this.constIdent=constIdent;
    }

    public ConstExpr getConstExpr() {
        return ConstExpr;
    }

    public void setConstExpr(ConstExpr ConstExpr) {
        this.ConstExpr=ConstExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstExpr!=null) ConstExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstExpr!=null) ConstExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstExpr!=null) ConstExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleConstDeclArray(\n");

        buffer.append(" "+tab+constIdent);
        buffer.append("\n");

        if(ConstExpr!=null)
            buffer.append(ConstExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConstDeclArray]");
        return buffer.toString();
    }
}

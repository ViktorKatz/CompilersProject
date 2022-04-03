// generated with ast extension for cup
// version 0.8
// 4/3/2022 1:14:14


package rs.ac.bg.etf.pp1.ast;

public class MultiConstDeclArray extends ConstDeclArray {

    private ConstDeclArray ConstDeclArray;
    private String constIdent;
    private ConstExpr ConstExpr;

    public MultiConstDeclArray (ConstDeclArray ConstDeclArray, String constIdent, ConstExpr ConstExpr) {
        this.ConstDeclArray=ConstDeclArray;
        if(ConstDeclArray!=null) ConstDeclArray.setParent(this);
        this.constIdent=constIdent;
        this.ConstExpr=ConstExpr;
        if(ConstExpr!=null) ConstExpr.setParent(this);
    }

    public ConstDeclArray getConstDeclArray() {
        return ConstDeclArray;
    }

    public void setConstDeclArray(ConstDeclArray ConstDeclArray) {
        this.ConstDeclArray=ConstDeclArray;
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
        if(ConstDeclArray!=null) ConstDeclArray.accept(visitor);
        if(ConstExpr!=null) ConstExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclArray!=null) ConstDeclArray.traverseTopDown(visitor);
        if(ConstExpr!=null) ConstExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclArray!=null) ConstDeclArray.traverseBottomUp(visitor);
        if(ConstExpr!=null) ConstExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultiConstDeclArray(\n");

        if(ConstDeclArray!=null)
            buffer.append(ConstDeclArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+constIdent);
        buffer.append("\n");

        if(ConstExpr!=null)
            buffer.append(ConstExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultiConstDeclArray]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 4/3/2022 1:14:14


package rs.ac.bg.etf.pp1.ast;

public class TypePrimitive extends Type {

    private PrimitiveType PrimitiveType;

    public TypePrimitive (PrimitiveType PrimitiveType) {
        this.PrimitiveType=PrimitiveType;
        if(PrimitiveType!=null) PrimitiveType.setParent(this);
    }

    public PrimitiveType getPrimitiveType() {
        return PrimitiveType;
    }

    public void setPrimitiveType(PrimitiveType PrimitiveType) {
        this.PrimitiveType=PrimitiveType;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(PrimitiveType!=null) PrimitiveType.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(PrimitiveType!=null) PrimitiveType.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(PrimitiveType!=null) PrimitiveType.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TypePrimitive(\n");

        if(PrimitiveType!=null)
            buffer.append(PrimitiveType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypePrimitive]");
        return buffer.toString();
    }
}

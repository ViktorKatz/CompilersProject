// generated with ast extension for cup
// version 0.8
// 16/1/2022 1:45:32


package rs.ac.bg.etf.pp1.ast;

public class MinusMultiAddOpExpr extends Expr {

    private Term Term;
    private AdditionTermList AdditionTermList;

    public MinusMultiAddOpExpr (Term Term, AdditionTermList AdditionTermList) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.AdditionTermList=AdditionTermList;
        if(AdditionTermList!=null) AdditionTermList.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
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
        if(Term!=null) Term.accept(visitor);
        if(AdditionTermList!=null) AdditionTermList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(AdditionTermList!=null) AdditionTermList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(AdditionTermList!=null) AdditionTermList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MinusMultiAddOpExpr(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
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

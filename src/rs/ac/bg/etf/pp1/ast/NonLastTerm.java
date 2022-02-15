// generated with ast extension for cup
// version 0.8
// 15/1/2022 0:4:49


package rs.ac.bg.etf.pp1.ast;

public class NonLastTerm extends AdditionTermList {

    private AdditionTermList AdditionTermList;
    private AddOp AddOp;
    private Term Term;

    public NonLastTerm (AdditionTermList AdditionTermList, AddOp AddOp, Term Term) {
        this.AdditionTermList=AdditionTermList;
        if(AdditionTermList!=null) AdditionTermList.setParent(this);
        this.AddOp=AddOp;
        if(AddOp!=null) AddOp.setParent(this);
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
    }

    public AdditionTermList getAdditionTermList() {
        return AdditionTermList;
    }

    public void setAdditionTermList(AdditionTermList AdditionTermList) {
        this.AdditionTermList=AdditionTermList;
    }

    public AddOp getAddOp() {
        return AddOp;
    }

    public void setAddOp(AddOp AddOp) {
        this.AddOp=AddOp;
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AdditionTermList!=null) AdditionTermList.accept(visitor);
        if(AddOp!=null) AddOp.accept(visitor);
        if(Term!=null) Term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AdditionTermList!=null) AdditionTermList.traverseTopDown(visitor);
        if(AddOp!=null) AddOp.traverseTopDown(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AdditionTermList!=null) AdditionTermList.traverseBottomUp(visitor);
        if(AddOp!=null) AddOp.traverseBottomUp(visitor);
        if(Term!=null) Term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NonLastTerm(\n");

        if(AdditionTermList!=null)
            buffer.append(AdditionTermList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddOp!=null)
            buffer.append(AddOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NonLastTerm]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 28/4/2022 1:45:11


package rs.ac.bg.etf.pp1.ast;

public class StatementWithLabel extends Statement {

    private Label Label;
    private NonLabeledStatement NonLabeledStatement;

    public StatementWithLabel (Label Label, NonLabeledStatement NonLabeledStatement) {
        this.Label=Label;
        if(Label!=null) Label.setParent(this);
        this.NonLabeledStatement=NonLabeledStatement;
        if(NonLabeledStatement!=null) NonLabeledStatement.setParent(this);
    }

    public Label getLabel() {
        return Label;
    }

    public void setLabel(Label Label) {
        this.Label=Label;
    }

    public NonLabeledStatement getNonLabeledStatement() {
        return NonLabeledStatement;
    }

    public void setNonLabeledStatement(NonLabeledStatement NonLabeledStatement) {
        this.NonLabeledStatement=NonLabeledStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Label!=null) Label.accept(visitor);
        if(NonLabeledStatement!=null) NonLabeledStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Label!=null) Label.traverseTopDown(visitor);
        if(NonLabeledStatement!=null) NonLabeledStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Label!=null) Label.traverseBottomUp(visitor);
        if(NonLabeledStatement!=null) NonLabeledStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementWithLabel(\n");

        if(Label!=null)
            buffer.append(Label.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NonLabeledStatement!=null)
            buffer.append(NonLabeledStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementWithLabel]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 4/3/2022 1:14:14


package rs.ac.bg.etf.pp1.ast;

public class StatementWithoutLabel extends Statement {

    private NonLabeledStatement NonLabeledStatement;

    public StatementWithoutLabel (NonLabeledStatement NonLabeledStatement) {
        this.NonLabeledStatement=NonLabeledStatement;
        if(NonLabeledStatement!=null) NonLabeledStatement.setParent(this);
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
        if(NonLabeledStatement!=null) NonLabeledStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NonLabeledStatement!=null) NonLabeledStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NonLabeledStatement!=null) NonLabeledStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementWithoutLabel(\n");

        if(NonLabeledStatement!=null)
            buffer.append(NonLabeledStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementWithoutLabel]");
        return buffer.toString();
    }
}

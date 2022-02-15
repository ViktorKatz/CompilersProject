// generated with ast extension for cup
// version 0.8
// 15/1/2022 3:19:3


package rs.ac.bg.etf.pp1.ast;

public class DoWhileStmt extends NonLabeledStatement {

    private StatementOrBlockOfStatements StatementOrBlockOfStatements;
    private Condition Condition;

    public DoWhileStmt (StatementOrBlockOfStatements StatementOrBlockOfStatements, Condition Condition) {
        this.StatementOrBlockOfStatements=StatementOrBlockOfStatements;
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
    }

    public StatementOrBlockOfStatements getStatementOrBlockOfStatements() {
        return StatementOrBlockOfStatements;
    }

    public void setStatementOrBlockOfStatements(StatementOrBlockOfStatements StatementOrBlockOfStatements) {
        this.StatementOrBlockOfStatements=StatementOrBlockOfStatements;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileStmt(\n");

        if(StatementOrBlockOfStatements!=null)
            buffer.append(StatementOrBlockOfStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoWhileStmt]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 15/1/2022 0:4:49


package rs.ac.bg.etf.pp1.ast;

public class IfStmt extends NonLabeledStatement {

    private Condition Condition;
    private StatementOrBlockOfStatements StatementOrBlockOfStatements;

    public IfStmt (Condition Condition, StatementOrBlockOfStatements StatementOrBlockOfStatements) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.StatementOrBlockOfStatements=StatementOrBlockOfStatements;
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public StatementOrBlockOfStatements getStatementOrBlockOfStatements() {
        return StatementOrBlockOfStatements;
    }

    public void setStatementOrBlockOfStatements(StatementOrBlockOfStatements StatementOrBlockOfStatements) {
        this.StatementOrBlockOfStatements=StatementOrBlockOfStatements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfStmt(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementOrBlockOfStatements!=null)
            buffer.append(StatementOrBlockOfStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfStmt]");
        return buffer.toString();
    }
}

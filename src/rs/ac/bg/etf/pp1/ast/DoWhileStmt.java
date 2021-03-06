// generated with ast extension for cup
// version 0.8
// 28/4/2022 1:45:11


package rs.ac.bg.etf.pp1.ast;

public class DoWhileStmt extends NonLabeledStatement {

    private DoCheckpoint DoCheckpoint;
    private StatementOrBlockOfStatements StatementOrBlockOfStatements;
    private Condition Condition;

    public DoWhileStmt (DoCheckpoint DoCheckpoint, StatementOrBlockOfStatements StatementOrBlockOfStatements, Condition Condition) {
        this.DoCheckpoint=DoCheckpoint;
        if(DoCheckpoint!=null) DoCheckpoint.setParent(this);
        this.StatementOrBlockOfStatements=StatementOrBlockOfStatements;
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
    }

    public DoCheckpoint getDoCheckpoint() {
        return DoCheckpoint;
    }

    public void setDoCheckpoint(DoCheckpoint DoCheckpoint) {
        this.DoCheckpoint=DoCheckpoint;
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
        if(DoCheckpoint!=null) DoCheckpoint.accept(visitor);
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoCheckpoint!=null) DoCheckpoint.traverseTopDown(visitor);
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoCheckpoint!=null) DoCheckpoint.traverseBottomUp(visitor);
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileStmt(\n");

        if(DoCheckpoint!=null)
            buffer.append(DoCheckpoint.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

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

// generated with ast extension for cup
// version 0.8
// 28/4/2022 1:45:11


package rs.ac.bg.etf.pp1.ast;

public class IfElseStmt extends NonLabeledStatement {

    private Condition Condition;
    private StatementOrBlockOfStatements StatementOrBlockOfStatements;
    private ElseCheckpoint ElseCheckpoint;
    private StatementOrBlockOfStatements StatementOrBlockOfStatements1;

    public IfElseStmt (Condition Condition, StatementOrBlockOfStatements StatementOrBlockOfStatements, ElseCheckpoint ElseCheckpoint, StatementOrBlockOfStatements StatementOrBlockOfStatements1) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.StatementOrBlockOfStatements=StatementOrBlockOfStatements;
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.setParent(this);
        this.ElseCheckpoint=ElseCheckpoint;
        if(ElseCheckpoint!=null) ElseCheckpoint.setParent(this);
        this.StatementOrBlockOfStatements1=StatementOrBlockOfStatements1;
        if(StatementOrBlockOfStatements1!=null) StatementOrBlockOfStatements1.setParent(this);
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

    public ElseCheckpoint getElseCheckpoint() {
        return ElseCheckpoint;
    }

    public void setElseCheckpoint(ElseCheckpoint ElseCheckpoint) {
        this.ElseCheckpoint=ElseCheckpoint;
    }

    public StatementOrBlockOfStatements getStatementOrBlockOfStatements1() {
        return StatementOrBlockOfStatements1;
    }

    public void setStatementOrBlockOfStatements1(StatementOrBlockOfStatements StatementOrBlockOfStatements1) {
        this.StatementOrBlockOfStatements1=StatementOrBlockOfStatements1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.accept(visitor);
        if(ElseCheckpoint!=null) ElseCheckpoint.accept(visitor);
        if(StatementOrBlockOfStatements1!=null) StatementOrBlockOfStatements1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.traverseTopDown(visitor);
        if(ElseCheckpoint!=null) ElseCheckpoint.traverseTopDown(visitor);
        if(StatementOrBlockOfStatements1!=null) StatementOrBlockOfStatements1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(StatementOrBlockOfStatements!=null) StatementOrBlockOfStatements.traverseBottomUp(visitor);
        if(ElseCheckpoint!=null) ElseCheckpoint.traverseBottomUp(visitor);
        if(StatementOrBlockOfStatements1!=null) StatementOrBlockOfStatements1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfElseStmt(\n");

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

        if(ElseCheckpoint!=null)
            buffer.append(ElseCheckpoint.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementOrBlockOfStatements1!=null)
            buffer.append(StatementOrBlockOfStatements1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfElseStmt]");
        return buffer.toString();
    }
}

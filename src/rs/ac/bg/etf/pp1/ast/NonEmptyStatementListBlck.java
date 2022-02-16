// generated with ast extension for cup
// version 0.8
// 16/1/2022 1:43:53


package rs.ac.bg.etf.pp1.ast;

public class NonEmptyStatementListBlck extends StatementList {

    private StatementList StatementList;
    private BlockOfStatements BlockOfStatements;

    public NonEmptyStatementListBlck (StatementList StatementList, BlockOfStatements BlockOfStatements) {
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
        this.BlockOfStatements=BlockOfStatements;
        if(BlockOfStatements!=null) BlockOfStatements.setParent(this);
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public BlockOfStatements getBlockOfStatements() {
        return BlockOfStatements;
    }

    public void setBlockOfStatements(BlockOfStatements BlockOfStatements) {
        this.BlockOfStatements=BlockOfStatements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementList!=null) StatementList.accept(visitor);
        if(BlockOfStatements!=null) BlockOfStatements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
        if(BlockOfStatements!=null) BlockOfStatements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        if(BlockOfStatements!=null) BlockOfStatements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NonEmptyStatementListBlck(\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(BlockOfStatements!=null)
            buffer.append(BlockOfStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NonEmptyStatementListBlck]");
        return buffer.toString();
    }
}

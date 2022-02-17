// generated with ast extension for cup
// version 0.8
// 17/1/2022 6:20:0


package rs.ac.bg.etf.pp1.ast;

public class BlockOfStatements implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private BlockOfStatementsStart BlockOfStatementsStart;
    private StatementList StatementList;

    public BlockOfStatements (BlockOfStatementsStart BlockOfStatementsStart, StatementList StatementList) {
        this.BlockOfStatementsStart=BlockOfStatementsStart;
        if(BlockOfStatementsStart!=null) BlockOfStatementsStart.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public BlockOfStatementsStart getBlockOfStatementsStart() {
        return BlockOfStatementsStart;
    }

    public void setBlockOfStatementsStart(BlockOfStatementsStart BlockOfStatementsStart) {
        this.BlockOfStatementsStart=BlockOfStatementsStart;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(BlockOfStatementsStart!=null) BlockOfStatementsStart.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(BlockOfStatementsStart!=null) BlockOfStatementsStart.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(BlockOfStatementsStart!=null) BlockOfStatementsStart.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BlockOfStatements(\n");

        if(BlockOfStatementsStart!=null)
            buffer.append(BlockOfStatementsStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BlockOfStatements]");
        return buffer.toString();
    }
}

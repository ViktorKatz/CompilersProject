// generated with ast extension for cup
// version 0.8
// 17/1/2022 6:20:0


package rs.ac.bg.etf.pp1.ast;

public class MultiStatement extends StatementOrBlockOfStatements {

    private BlockOfStatements BlockOfStatements;

    public MultiStatement (BlockOfStatements BlockOfStatements) {
        this.BlockOfStatements=BlockOfStatements;
        if(BlockOfStatements!=null) BlockOfStatements.setParent(this);
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
        if(BlockOfStatements!=null) BlockOfStatements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(BlockOfStatements!=null) BlockOfStatements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(BlockOfStatements!=null) BlockOfStatements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultiStatement(\n");

        if(BlockOfStatements!=null)
            buffer.append(BlockOfStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultiStatement]");
        return buffer.toString();
    }
}

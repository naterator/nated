// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class WhileStatementNode extends StatementNode
{

    public BoolexpNode boolexp;

    public StatementNode statement;

    public int acceptAll(Visitor v)
    {
        this.boolexp.acceptAll(v);
        this.statement.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public WhileStatementNode(BoolexpNode b, StatementNode s)
    {
        boolexp = b;
        statement = s;
    }
    
    public String toString()
    {
        return "WhileStatementNode (line " + line + ")";
    }
}
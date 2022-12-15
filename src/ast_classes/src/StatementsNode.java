// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class StatementsNode extends StatementNode
{

    public StatementNode statements;

    public StatementNode statement;

    public int acceptAll(Visitor v)
    {
        this.statements.acceptAll(v);
        this.statement.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public StatementsNode(StatementNode ss, StatementNode s)
    {
        statements = ss;
        statement = s;
    }
    
    public String toString()
    {
        return "StatementsNode (line " + line + ")";
    }
}
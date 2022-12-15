// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class IfelseStatementNode extends IfStatementNode
{

    public StatementNode statement2;

    public int acceptAll(Visitor v)
    {
        this.boolexp.acceptAll(v);
        this.statement.acceptAll(v);
        this.statement2.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public IfelseStatementNode(BoolexpNode b, StatementNode s1, StatementNode s2)
    {
        super(b, s1);
        statement2 = s2;
    }
    
    public String toString()
    {
        return "IfelseStatementNode (line " + line + ")";
    }
}

// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ReturnStatementNode extends StatementNode
{

    public ExpNode exp;

    public int acceptAll(Visitor v)
    {
        this.exp.acceptAll(v);
        return v.visit(this);
    }

    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public ReturnStatementNode(ExpNode e)
    {
        exp = e;
    }

    public String toString()
    {
        return "ReturnStatementNode (line " + line + ")";
    }
}

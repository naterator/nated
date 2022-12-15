// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ExpFactorNode extends FactorNode
{

    public ExpNode e;

    public int acceptAll(Visitor v)
    {
        this.e.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public ExpFactorNode(ExpNode e)
    {
        this.e = e;
    }
    
    public String toString()
    {
        return "ExpFactorNode (line " + line + ")";
    }
}
// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ExpsNode extends BaseExpNode
{
    public ExpNode e;
    
    public int count;

    public int acceptAll(Visitor v)
    {
        this.e.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public ExpsNode(ExpNode e)
    {
        this.e = e;
        this.count = 1;
    }
    
    public String toString()
    {
        return "ExpsNode (line " + line + ")";
    }
}
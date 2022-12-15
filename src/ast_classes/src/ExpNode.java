// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ExpNode extends BaseExpNode
{

    public TermNode t;

    public ExpNode(TermNode t)
    {
        this.t = t;
    }

    public int acceptAll(Visitor v)
    {
        this.t.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }
    
    public String toString()
    {
        return "ExpNode (line " + line + ")";
    }
}
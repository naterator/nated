// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class GtRelexpNode extends RelexpNode
{

    public int acceptAll(Visitor v)
    {
        this.e1.acceptAll(v);
        this.e2.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public GtRelexpNode(ExpNode e1, ExpNode e2)
    {
        super(e1,e2);
    }
    
    public String toString()
    {
        return "GtRelexpNode (line " + line + ")";
    }
}
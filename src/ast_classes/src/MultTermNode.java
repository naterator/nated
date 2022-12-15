// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class MultTermNode extends TermNode
{

    public TermNode t;

    public int acceptAll(Visitor v)
    {
        this.f.acceptAll(v);
        this.t.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public MultTermNode(TermNode t, FactorNode f)
    {
        super(f);
        this.t = t;
    }
    
    public String toString()
    {
        return "MultTermNode (line " + line + ")";
    }
}
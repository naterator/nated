// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ExpsExpsNode extends ExpsNode
{

    public ExpsNode es;

    public int acceptAll(Visitor v)
    {
        this.e.acceptAll(v);
        this.es.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public ExpsExpsNode(ExpNode e, ExpsNode es)
    {
        super(e);
        this.es = es;
        this.count = es.count + 1;
    }
    
    public String toString()
    {
        return "ExpsExpsNode (line " + line + ")";
    }
}
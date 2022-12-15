// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ExpsFunctionFactorNode extends FunctionFactorNode
{

    public ExpsNode e;

    public int acceptAll(Visitor v)
    {
        this.e.acceptAll(v);
        return v.visit(this);
    }

    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public ExpsFunctionFactorNode(String id, ExpsNode e)
    {
        super(id);
        this.e = e;
    }

    public String toString()
    {
        return "ExpsFunctionFactorNode (line " + line + ") - " + id;
    }
}

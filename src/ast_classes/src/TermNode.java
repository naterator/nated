// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class TermNode extends ASTNode
{
    public int dataType = DataType.UNKNOWN;

    public FactorNode f;

    public int acceptAll(Visitor v)
    {
        this.f.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public TermNode(FactorNode f)
    {
        this.f = f;
    }
    
    public String toString()
    {
        return "TermNode (line " + line + ")";
    }
}
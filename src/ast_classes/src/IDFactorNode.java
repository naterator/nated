// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class IDFactorNode extends FactorNode
{

    public String id;

    public IDFactorNode(String id)
    {
        this.id = id;
    }

    public int acceptAll(Visitor v)
    {
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }
    
    public String toString()
    {
        return "IDFactorNode (line " + line + ") - " + id;
    }
}
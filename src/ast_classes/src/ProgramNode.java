// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ProgramNode extends ASTNode
{
    public FunctiondefNode functiondef;

    public int acceptAll(Visitor v)
    {
        this.functiondef.acceptAll(v);
        return v.visit(this);
    }

    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public ProgramNode(FunctiondefNode f)
    {
        functiondef = f;
    }

    public String toString()
    {
        return "ProgramNode (line " + line + ")";
    }
}

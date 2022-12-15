// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ProgramProgramNode extends ProgramNode
{

    public ProgramNode program;

    public int acceptAll(Visitor v)
    {
        this.program.acceptAll(v);
        this.functiondef.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public ProgramProgramNode(FunctiondefNode f, ProgramNode p)
    {
        super(f);
        program = p;
    }
    
    public String toString()
    {
        return "ProgramProgramNode (line " + line + ")";
    }
}
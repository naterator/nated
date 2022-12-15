// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class IntFunctiondefNode extends FunctiondefNode
{

    public int acceptAll(Visitor v)
    {
        this.statements.acceptAll(v);
        return v.visit(this);
    }

    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public IntFunctiondefNode(String i, StatementNode ss)
    {
        super(i, ss);
    }

    public String toString()
    {
        return "IntfunctiondefNode (line " + line + ") - int " + id + "()";
    }

}

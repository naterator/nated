// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ParametersIntFunctiondefNode extends IntFunctiondefNode
{

    public ParameterNode parameters;

    public int acceptAll(Visitor v)
    {
        this.parameters.acceptAll(v);
        this.statements.acceptAll(v);
        return v.visit(this);
    }

    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public ParametersIntFunctiondefNode(String i, ParameterNode ps,
            StatementNode ss)
    {
        super(i, ss);
        parameters = ps;
    }

    public String toString()
    {
        return "ParametersIntFunctiondefNode (line " + line + ") - int " + id
                + "(parms)";
    }
}

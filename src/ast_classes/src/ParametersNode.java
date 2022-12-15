// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ParametersNode extends ParameterNode
{

    public ParameterNode parameters;

    public ParameterNode parameter;

    public int acceptAll(Visitor v)
    {
        this.parameter.acceptAll(v);
        this.parameters.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public ParametersNode(ParameterNode ps, ParameterNode p)
    {
        parameters = ps;
        parameter = p;
    }
    
    public String toString()
    {
        return "ParametersNode (line " + line + ")";
    }
}
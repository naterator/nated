// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ParametersDeclarationsIntFunctiondefNode extends
        IntFunctiondefNode
{

    public DeclarationNode declarations;

    public ParameterNode parameters;

    public int acceptAll(Visitor v)
    {
        this.parameters.acceptAll(v);
        this.declarations.acceptAll(v);
        this.statements.acceptAll(v);
        return v.visit(this);
    }

    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public ParametersDeclarationsIntFunctiondefNode(String i, ParameterNode ps,
            DeclarationNode ds, StatementNode ss)
    {
        super(i, ss);
        declarations = ds;
        parameters = ps;
    }

    public String toString()
    {
        return "ParametersDeclarationsIntFunctiondefNode (line " + line
                + ") - int " + id + "(parms)";
    }
}

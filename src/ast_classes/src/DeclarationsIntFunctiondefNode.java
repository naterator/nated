// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class DeclarationsIntFunctiondefNode extends IntFunctiondefNode
{

    public DeclarationNode declarations;

    public int acceptAll(Visitor v)
    {
        this.declarations.acceptAll(v);
        this.statements.acceptAll(v);
        return v.visit(this);
    }

    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public DeclarationsIntFunctiondefNode(String i, DeclarationNode ds,
            StatementNode ss)
    {
        super(i, ss);
        declarations = ds;
    }

    public String toString()
    {
        return "DeclarationsIntFunctiondefNode (line " + line + ") - int " + id
                + "()";
    }
}

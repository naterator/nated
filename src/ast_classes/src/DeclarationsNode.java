// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class DeclarationsNode extends DeclarationNode
{

    public DeclarationNode declaration;

    public DeclarationNode declarations;

    public DeclarationsNode(DeclarationNode ds, DeclarationNode d)
    {
        declaration = d;
        declarations = ds;
    }

    public int acceptAll(Visitor v)
    {
        this.declaration.acceptAll(v);
        this.declarations.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }
    
    public String toString()
    {
        return "DeclarationsNode (line " + line + ")";
    }
}
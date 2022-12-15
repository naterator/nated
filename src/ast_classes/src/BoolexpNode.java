// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class BoolexpNode extends ASTNode
{
    public int dataType = DataType.UNKNOWN;

    public RelexpNode r;

    public int acceptAll(Visitor v)
    {
        this.r.acceptAll(v);
        return v.visit(this);
    }
    
    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public BoolexpNode(RelexpNode r)
    {
        this.r = r;
    }
    
    public String toString()
    {
        return "BoolexpNode (line " + line + ")";
    }

}
// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public abstract class RelexpNode extends ASTNode
{
    public int dataType = DataType.UNKNOWN;

    public ExpNode e1;

    public ExpNode e2;

    public RelexpNode(ExpNode e1, ExpNode e2)
    {
        this.e1 = e1;
        this.e2 = e2;
    }
}
// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class SymbolTableNode
{
    // ////////////////////////////////////////////////////////////////////////
    // local state data
    public ASTNode astNodeObject;
    public int appearanceCount;
    public String name;
    public int dataType;
    public int offset;

    // ////////////////////////////////////////////////////////////////////////
    // constructors
    public SymbolTableNode()
    {
        astNodeObject = null;
        appearanceCount = 0;
        dataType = DataType.UNKNOWN;
        name = new String("");
        offset = 0;
    }

    public SymbolTableNode(String name, int dataType, ASTNode astNodeObject,
            int appearanceCount, int offset)
    {
        this();
        this.name = name;
        this.dataType = dataType;
        this.astNodeObject = astNodeObject;
        this.appearanceCount = appearanceCount;
        this.offset = offset;
    }

    // ////////////////////////////////////////////////////////////////////////
    // utility functions
    public String toString()
    {
        String tmp1 = Compiler.pad(name, 20);
        String tmp2 = DataType.toString(dataType);
        tmp2 = Compiler.pad(tmp2, 13);
        String tmp3 = Integer.toString(offset);
        tmp3 = Compiler.pad(tmp3, 15);
        String tmp = new String(tmp1+tmp2+tmp3);
        return tmp;
    }
}

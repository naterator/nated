// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class IntliteralFactorNode extends FactorNode
{

    public int intliteral;

    public int acceptAll(Visitor v)
    {
        return v.visit(this);
    }

    public int acceptMe(Visitor v)
    {
        return v.visit(this);
    }

    public IntliteralFactorNode(int intliteral)
    {
        this.intliteral = intliteral;
    }

    public String toString()
    {
        Integer i = intliteral;
        return "IntliteralFactorNode (line " + line + ") - " + i.toString();
    }
}

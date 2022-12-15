// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

import java.util.ArrayList;

public abstract class FunctiondefNode extends ASTNode
{
    public SymbolTable symbolTableTemplate;
    
    public ArrayList<String> requiredParametersName;
    
    public ArrayList<Integer> requiredParametersDataType;
    
    public String id;

    public StatementNode statements;

    public FunctiondefNode(String i, StatementNode ss)
    {
        id = i;
        statements = ss;
        requiredParametersName = new ArrayList<String>();
        requiredParametersDataType = new ArrayList<Integer>();
        symbolTableTemplate = new SymbolTable();
    }
}
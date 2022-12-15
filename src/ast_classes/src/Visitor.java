// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public interface Visitor
{
    public int visit(BoolexpNode a);

    public int visit(DeclarationsIntFunctiondefNode a);

    public int visit(DeclarationsNode a);

    public int visit(EqeqRelexpNode a);

    public int visit(ExpFactorNode a);

    public int visit(ExpNode a);

    public int visit(ExpsExpsNode a);

    public int visit(ExpsFunctionFactorNode a);

    public int visit(ExpsNode a);
    
    public int visit(FunctionFactorNode a);

    public int visit(GtRelexpNode a);

    public int visit(IDFactorNode a);

    public int visit(IfStatementNode a);

    public int visit(IfelseStatementNode a);

    public int visit(IntDeclarationNode a);

    public int visit(IntFunctiondefNode a);

    public int visit(IntParameterNode a);

    public int visit(IntliteralFactorNode a);

    public int visit(MinusExpNode a);

    public int visit(MultTermNode a);

    public int visit(NotBoolexpNode a);

    public int visit(ParametersDeclarationsIntFunctiondefNode a);

    public int visit(ParametersIntFunctiondefNode a);

    public int visit(ParametersNode a);

    public int visit(PlusExpNode a);

    public int visit(ProgramNode a);

    public int visit(ProgramProgramNode a);
    
    public int visit(ReturnStatementNode a);

    public int visit(SetvalueStatementNode a);

    public int visit(StatementsNode a);

    public int visit(TermNode a);

    public int visit(WhileStatementNode a);
}
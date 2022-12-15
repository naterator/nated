// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

// Phase3CompileVisitor
// 
// Yes, an entire visitor dedicated just to checking for main(). But seriously,
// who cares?  It's so simple to add in and its pretty quick too! Oh, also,
// we calculate offsets for local variables here.

public class Phase3CompileVisitor implements Visitor
{

    // /////////////////////////////////////////////////////////////////////////
    // local state data
    public Compiler state;

    // /////////////////////////////////////////////////////////////////////////
    // private helper functions
    private void checkForMain()
    {
        if (state.functions.has("main"))
        {
            FunctiondefNode fdn = (FunctiondefNode) (state.functions
                    .get("main"));
            if (fdn.requiredParametersName.size() != 0)
            {
                String tmp = "N/A: Function \"main()\" cannot contain parameters.";
                state.errors.add(0, tmp);
            }
        }
        else
        {
            String tmp = "N/A: Function \"main()\" must be declared, but was not.";
            state.errors.add(0, tmp);
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // constructors
    public Phase3CompileVisitor()
    {
    }

    public Phase3CompileVisitor(Compiler state)
    {
        if (state != null)
            state.wrongPhase(this.getClass().toString());
    }

    public Phase3CompileVisitor(Compiler state, Boolean authorized)
    {
        if (state != null)
            this.state = state;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PROGRAM nodes
    public int visit(ProgramNode a)
    {
        checkForMain();
        return 0;
    }

    public int visit(ProgramProgramNode a)
    {
        checkForMain();
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // FUNCTIONDEF nodes
    public int visit(IntFunctiondefNode a)
    {
        return 0;
    }

    public int visit(ParametersIntFunctiondefNode a)
    {
        return 0;
    }

    public int visit(DeclarationsIntFunctiondefNode a)
    {
        return 0;
    }

    public int visit(ParametersDeclarationsIntFunctiondefNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PARAMETERS node
    public int visit(ParametersNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PARAMETER node
    public int visit(IntParameterNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // DECLARATIONS node
    public int visit(DeclarationsNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // DECLARATION node
    public int visit(IntDeclarationNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // STATEMENTS node
    public int visit(StatementsNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // STATEMENT nodes
    public int visit(SetvalueStatementNode a)
    {
        return 0;
    }

    public int visit(ReturnStatementNode a)
    {
        return 0;
    }

    public int visit(IfStatementNode a)
    {
        return 0;
    }

    public int visit(IfelseStatementNode a)
    {
        return 0;
    }

    public int visit(WhileStatementNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // BOOLEXP nodes
    public int visit(BoolexpNode a)
    {
        return 0;
    }

    public int visit(NotBoolexpNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // RELEXP nodes
    public int visit(EqeqRelexpNode a)
    {
        return 0;
    }

    public int visit(GtRelexpNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // EXP nodes
    public int visit(ExpNode a)
    {
        return 0;
    }

    public int visit(PlusExpNode a)
    {
        return 0;
    }

    public int visit(MinusExpNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // TERM nodes
    public int visit(TermNode a)
    {
        return 0;
    }

    public int visit(MultTermNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // FACTOR nodes
    public int visit(IDFactorNode a)
    {
        return 0;
    }

    public int visit(IntliteralFactorNode a)
    {
        return 0;
    }

    public int visit(ExpFactorNode a)
    {
        return 0;
    }

    public int visit(FunctionFactorNode a)
    {
        return 0;
    }

    public int visit(ExpsFunctionFactorNode a)
    {
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // EXPS nodes
    public int visit(ExpsNode a)
    {
        return 0;
    }

    public int visit(ExpsExpsNode a)
    {
        return 0;
    }

}

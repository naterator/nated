// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

// Phase5CompileVisitor
// 
// This Visitor goes through and prints out error messages or success message
// at the end of the compilation attempt.

public class Phase5CompileVisitor implements Visitor
{

    // /////////////////////////////////////////////////////////////////////////
    // local state data
    public Compiler state;
    private boolean finalsCalled = false;

    // /////////////////////////////////////////////////////////////////////////
    // private helper functions
    private void printErrors()
    {
        // if we actually have error messages...
        if (state.errors.size() > 0)
        {

            // have some intelligence and display proper grammar to the user
            String questionable = new String("s were");
            if (state.errors.size() == 1)
                questionable = new String(" was");

            // display a note to the user that there were some errors
            System.err.println(Integer.toString(state.errors.size()) + " error"
                    + questionable + " encountered during compilation:");
            System.err.println("");

            System.err.println("[Line #]: [Description]");
            
            // iterate through each error message and print it out
            java.util.Collections.sort(state.errors);
            for (int i = 0; i < state.errors.size(); i++)
            {
                System.err.println(state.errors.get(i));
            }
        }
        
        // all done!
        return;
    }
    
    private void finals()
    {
        // only call once
        if(finalsCalled)
            return;
        finalsCalled = true;
        
        // do stuff
        printCompletion();
    }
    
    private void printCompletion()
    {
        // print final messages, depending on error status
        if( state.errors.size() > 0)
        {
            printErrors();
        }
        else
        {
            System.err.println("Compilation completed successfully with 0 errors!");
        }
        System.err.println("");
   
        // all done!
        return;
    }

    // /////////////////////////////////////////////////////////////////////////
    // constructors
    public Phase5CompileVisitor()
    {
    }

    public Phase5CompileVisitor(Compiler state)
    {
        if (state != null)
            state.wrongPhase(this.getClass().toString());
    }

    public Phase5CompileVisitor(Compiler state, Boolean authorized)
    {
        if (state != null)
            this.state = state;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PROGRAM nodes
    public int visit(ProgramNode a)
    {
        finals();
        return 0;
    }

    public int visit(ProgramProgramNode a)
    {
        finals();
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

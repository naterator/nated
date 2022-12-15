// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

// Phase4PrintVisitor
//
// This class is a dependency of Phase4CompileVisitor. It recursively
// visits children and properly prints out code for the commenting
// of the final outputted ASM file.

public class Phase4PrintVisitor implements Visitor
{

    // /////////////////////////////////////////////////////////////////////////
    // private state data
    public int part = 1;

    // /////////////////////////////////////////////////////////////////////////
    // private helper functions
    private void printWithoutTabs( String inTarget, boolean lineBreak )
    {
        String target = new String("; " + inTarget);
        print( target, lineBreak );
    }
    
    private void printWithTabs( String inTarget, boolean lineBreak )
    {
        String target = new String("    ; " + inTarget);
        print( target, lineBreak );
    }
    
    private void printFunctionDivider()
    {
        print("",true);
        print(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;",false);
        print(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;",true);
    }
    
    private void print( String inTarget, boolean lineBreak )
    {
        if( lineBreak )
            System.out.println(inTarget);
        else
            System.out.print(inTarget);
    }

    // /////////////////////////////////////////////////////////////////////////
    // constructors
    public Phase4PrintVisitor()
    {
    }
    
    public Phase4PrintVisitor(Compiler state)
    {
        if (state != null)
            state.wrongPhase(this.getClass().toString());
    }

    // /////////////////////////////////////////////////////////////////////////
    // PROGRAM nodes
    public int visit(ProgramNode a)
    {
        a.functiondef.acceptMe(this);
        return 0;
    }

    public int visit(ProgramProgramNode a)
    {
        a.program.acceptMe(this);
        a.functiondef.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // FUNCTIONDEF nodes
    public int visit(IntFunctiondefNode a)
    {
        printFunctionDivider();
        printWithoutTabs("int " + a.id + "()",true);
        printWithoutTabs("locals: [NONE]", true);
        return 0;
    }

    public int visit(ParametersIntFunctiondefNode a)
    {
        printFunctionDivider();
        printWithoutTabs("int " + a.id + "(", false);
        a.parameters.acceptMe(this);
        print(")", true);
        printWithoutTabs("locals: [NONE]", true);
        return 0;
    }

    public int visit(DeclarationsIntFunctiondefNode a)
    {
        printFunctionDivider();
        printWithoutTabs("int " + a.id + "()", true);
        printWithoutTabs("locals: ", false);
        a.declarations.acceptMe(this);
        print("",true);
        return 0;
    }

    public int visit(ParametersDeclarationsIntFunctiondefNode a)
    {
        printFunctionDivider();
        printWithoutTabs("int " + a.id + "(", false);
        a.parameters.acceptMe(this);
        print(")", true);
        printWithoutTabs("locals: ", false);
        a.declarations.acceptMe(this);
        print("",true);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PARAMETERS node
    public int visit(ParametersNode a)
    {
        a.parameters.acceptMe(this);
        print(", ", false);
        a.parameter.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PARAMETER node
    public int visit(IntParameterNode a)
    {
        print("int " + a.id, false);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // DECLARATIONS node
    public int visit(DeclarationsNode a)
    {
        a.declarations.acceptMe(this);
        print(", ", false);
        a.declaration.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // DECLARATION node
    public int visit(IntDeclarationNode a)
    {
        print("int " + a.id, false);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // STATEMENTS node
    public int visit(StatementsNode a)
    {
        a.statements.acceptMe(this);
        a.statement.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // STATEMENT nodes
    public int visit(SetvalueStatementNode a)
    {
        printWithTabs(a.id + " = ", false);
        a.exp.acceptMe(this);
        print(";", true);
        return 0;
    }

    public int visit(ReturnStatementNode a)
    {
        printWithTabs("return ", false);
        a.exp.acceptMe(this);
        print(";", true);
        return 0;
    }

    public int visit(IfStatementNode a)
    {
        printWithTabs("if( ", false);
        a.boolexp.acceptMe(this);
        print(" )", true);
        return 0;
    }

    public int visit(IfelseStatementNode a)
    {
        if( part == 1 )
        {
            printWithTabs("if( ", false);
            a.boolexp.acceptMe(this);
            print(" )", true);
        }
        else if( part == 2 )
        {
            // part = 1;
            // a.statement.acceptMe(this);
            // part = 2;
        }
        else if( part == 3 )
        {
            printWithTabs("else", true);
            //part = 1;
            // a.statement2.acceptMe(this);
            // printWithTabs("SOME BULLSHIT HERE",true);
            //part = 3;
        }

        return 0;
    }

    public int visit(WhileStatementNode a)
    {
        printWithTabs("while( ", false);
        a.boolexp.acceptMe(this);
        print(" )", true);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // BOOLEXP nodes
    public int visit(BoolexpNode a)
    {
        a.r.acceptMe(this);
        return 0;
    }

    public int visit(NotBoolexpNode a)
    {
        print("!( ", false);
        a.r.acceptMe(this);
        print(" )", false);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // RELEXP nodes
    public int visit(EqeqRelexpNode a)
    {
        a.e1.acceptMe(this);
        print(" == ", false);
        a.e2.acceptMe(this);
        return 0;
    }

    public int visit(GtRelexpNode a)
    {
        a.e1.acceptMe(this);
        print(" > ", false);
        a.e2.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // EXP nodes
    public int visit(ExpNode a)
    {
        a.t.acceptMe(this);
        return 0;
    }

    public int visit(PlusExpNode a)
    {
        a.e.acceptMe(this);
        print(" + ", false);
        a.t.acceptMe(this);
        return 0;
    }

    public int visit(MinusExpNode a)
    {
        a.e.acceptMe(this);
        print(" - ", false);
        a.t.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // TERM nodes
    public int visit(TermNode a)
    {
        a.f.acceptMe(this);
        return 0;
    }

    public int visit(MultTermNode a)
    {
        a.t.acceptMe(this);
        print(" * ", false);
        a.f.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // FACTOR nodes
    public int visit(IDFactorNode a)
    {
        print(a.id, false);
        return 0;
    }

    public int visit(IntliteralFactorNode a)
    {
        Integer i = a.intliteral;
        print(i.toString(), false);
        return 0;
    }

    public int visit(ExpFactorNode a)
    {
        print("( ", false);
        a.e.acceptMe(this);
        print(" )", false);
        return 0;
    }

    public int visit(FunctionFactorNode a)
    {
        print(a.id + "()", false);
        return 0;
    }

    public int visit(ExpsFunctionFactorNode a)
    {
        print(a.id + "( ", false);
        a.e.acceptMe(this);
        print(" )", false);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // EXPS nodes
    public int visit(ExpsNode a)
    {
        a.e.acceptMe(this);
        return 0;
    }

    public int visit(ExpsExpsNode a)
    {
        a.es.acceptMe(this);
        print(", ", false);
        a.e.acceptMe(this);
        return 0;
    }

}

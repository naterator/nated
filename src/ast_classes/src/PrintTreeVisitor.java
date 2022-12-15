// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class PrintTreeVisitor implements Visitor
{

    // /////////////////////////////////////////////////////////////////////////
    // private state data
    private int indent = 0;

    // /////////////////////////////////////////////////////////////////////////
    // private helper functions
    private String getTabs()
    {
        String tmp = new String("");
        for (int i = 0; i < indent; i++)
            tmp = tmp.concat("    ");
        return tmp;
    }

    private String replaceLast(String find, String replace, String source)
    {
        String result = "";

        // find and replace the last set of tabs with ""
        if (source.indexOf(find) >= 0)
        {
            result += source.substring(0, source.lastIndexOf(find)) + replace;
            source = source.substring(source.lastIndexOf(find) + find.length());
        }
        result += source;

        return result;
    }

    private void printWithTabs( String target )
    {
        String tmp = new String(target);
        
        // replace all line breaks with line break + tabs
        tmp = tmp.replaceAll( "\r\n", "\r\n"+getTabs() );
        tmp = tmp.replaceAll( "\r", "\r"+getTabs() );
        tmp = tmp.replaceAll( "\n", "\n"+getTabs() );
        
        // get rid of the tabs on the last line
        tmp = replaceLast( getTabs(), "", tmp );
        
        // prepend the proper tabs that weren't inserted at the beginning
        tmp = getTabs().concat(tmp);
        
        // display to screen for user
        System.out.println(tmp);
    }

    // /////////////////////////////////////////////////////////////////////////
    // constructors
    public PrintTreeVisitor()
    {
    }

    public PrintTreeVisitor(Compiler c)
    {
        c.head.acceptMe(this);
    }

    // /////////////////////////////////////////////////////////////////////////
    // PROGRAM nodes
    public int visit(ProgramNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.functiondef.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(ProgramProgramNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.functiondef.acceptMe(this);
        a.program.acceptMe(this);
        indent--;
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // FUNCTIONDEF nodes
    public int visit(IntFunctiondefNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.statements.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(ParametersIntFunctiondefNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.parameters.acceptMe(this);
        a.statements.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(DeclarationsIntFunctiondefNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.declarations.acceptMe(this);
        a.statements.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(ParametersDeclarationsIntFunctiondefNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.parameters.acceptMe(this);
        a.declarations.acceptMe(this);
        a.statements.acceptMe(this);
        indent--;
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PARAMETERS node
    public int visit(ParametersNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.parameter.acceptMe(this);
        a.parameters.acceptMe(this);
        indent--;
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PARAMETER node
    public int visit(IntParameterNode a)
    {
        printWithTabs(a.toString());
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // DECLARATIONS node
    public int visit(DeclarationsNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.declaration.acceptMe(this);
        a.declarations.acceptMe(this);
        indent--;
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // DECLARATION node
    public int visit(IntDeclarationNode a)
    {
        printWithTabs(a.toString());
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // STATEMENTS node
    public int visit(StatementsNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.statement.acceptMe(this);
        a.statements.acceptMe(this);
        indent--;
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // STATEMENT nodes
    public int visit(SetvalueStatementNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.exp.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(ReturnStatementNode a)
    {
        printWithTabs(a.toString());
        return 0;
    }

    public int visit(IfStatementNode a)
    {
        printWithTabs(a.toString());
        return 0;
    }

    public int visit(IfelseStatementNode a)
    {
        printWithTabs(a.toString());
        return 0;
    }

    public int visit(WhileStatementNode a)
    {
        printWithTabs(a.toString());
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // BOOLEXP nodes
    public int visit(BoolexpNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.r.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(NotBoolexpNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.r.acceptMe(this);
        indent--;
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // RELEXP nodes
    public int visit(EqeqRelexpNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.e1.acceptMe(this);
        a.e2.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(GtRelexpNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.e1.acceptMe(this);
        a.e2.acceptMe(this);
        indent--;
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // EXP nodes
    public int visit(ExpNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.t.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(PlusExpNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.t.acceptMe(this);
        a.e.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(MinusExpNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.t.acceptMe(this);
        a.e.acceptMe(this);
        indent--;
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // TERM nodes
    public int visit(TermNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.f.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(MultTermNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.f.acceptMe(this);
        a.t.acceptMe(this);
        indent--;
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // FACTOR nodes
    public int visit(IDFactorNode a)
    {
        printWithTabs(a.toString());
        return 0;
    }

    public int visit(IntliteralFactorNode a)
    {
        printWithTabs(a.toString());
        return 0;
    }

    public int visit(ExpFactorNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.e.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(FunctionFactorNode a)
    {
        printWithTabs(a.toString());
        return 0;
    }

    public int visit(ExpsFunctionFactorNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.e.acceptMe(this);
        indent--;
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // EXPS nodes
    public int visit(ExpsNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.e.acceptMe(this);
        indent--;
        return 0;
    }

    public int visit(ExpsExpsNode a)
    {
        printWithTabs(a.toString());
        indent++;
        a.e.acceptMe(this);
        a.es.acceptMe(this);
        indent--;
        return 0;
    }

}

// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class PrintCodeVisitor implements Visitor
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
    
    private void print( String target, boolean lineBreak )
    {
        if( lineBreak )
            System.out.println(target);
        else
            System.out.print(target);
    }

    private void printWithTabs( String target, boolean lineBreak )
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
        if( lineBreak )
            System.out.println(tmp);
        else
            System.out.print(tmp);
    }

    // /////////////////////////////////////////////////////////////////////////
    // constructors
    public PrintCodeVisitor()
    {
    }

    public PrintCodeVisitor(Compiler c)
    {
        c.head.acceptMe(this);
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
        printWithTabs("int " + a.id + "()",true);
        printWithTabs("{",true);
        indent++;
        a.statements.acceptMe(this);
        indent--;
        printWithTabs("}",true);
        print("",true);
        return 0;
    }

    public int visit(ParametersIntFunctiondefNode a)
    {
        printWithTabs("int " + a.id + "(",false);
        a.parameters.acceptMe(this);
        print(")",true);
        printWithTabs("{",true);
        indent++;
        a.statements.acceptMe(this);
        indent--;
        printWithTabs("}",true);
        print("",true);
        return 0;
    }

    public int visit(DeclarationsIntFunctiondefNode a)
    {
        printWithTabs("int " + a.id + "()",true);
        printWithTabs("{",true);
        indent++;
        a.declarations.acceptMe(this);
        a.statements.acceptMe(this);
        indent--;
        printWithTabs("}",true);
        print("",true);
        return 0;
    }

    public int visit(ParametersDeclarationsIntFunctiondefNode a)
    {
        printWithTabs("int " + a.id + "(",false);
        a.parameters.acceptMe(this);
        print(")",true);
        printWithTabs("{",true);
        indent++;
        a.declarations.acceptMe(this);
        a.statements.acceptMe(this);
        indent--;
        printWithTabs("}",true);
        print("",true);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PARAMETERS node
    public int visit(ParametersNode a)
    {
        a.parameters.acceptMe(this);
        print(", ",false);
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
        a.declaration.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // DECLARATION node
    public int visit(IntDeclarationNode a)
    {
        printWithTabs("int " + a.id + ";",true);
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
        printWithTabs(a.id + " = ",false);
        a.exp.acceptMe(this);
        print(";",true);
        return 0;
    }

    public int visit(ReturnStatementNode a)
    {
        printWithTabs("return ",false);
        a.exp.acceptMe(this);
        print(";",true);
        return 0;
    }

    public int visit(IfStatementNode a)
    {
        printWithTabs("if( ",false);
        a.boolexp.acceptMe(this);
        print(" )",true);
        printWithTabs("{",true);
        indent++;
        a.statement.acceptMe(this);
        indent--;
        printWithTabs("}",true);
        return 0;
    }

    public int visit(IfelseStatementNode a)
    {
        printWithTabs("if( ",false);
        a.boolexp.acceptMe(this);
        print(" )",true);
        printWithTabs("{",true);
        indent++;
        a.statement.acceptMe(this);
        indent--;
        printWithTabs("}",true);
        printWithTabs("else",true);
        printWithTabs("{",true);
        indent++;
        a.statement2.acceptMe(this);
        indent--;
        printWithTabs("}",true);
        return 0;
    }

    public int visit(WhileStatementNode a)
    {
        printWithTabs("while( ",false);
        a.boolexp.acceptMe(this);
        print(" )",true);
        printWithTabs("{",true);
        indent++;
        a.statement.acceptMe(this);
        indent--;
        printWithTabs("}",true);
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
        print("!( ",false);
        a.r.acceptMe(this);
        print(" )",false);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // RELEXP nodes
    public int visit(EqeqRelexpNode a)
    {
        a.e1.acceptMe(this);
        print(" == ",false);
        a.e2.acceptMe(this);
        return 0;
    }

    public int visit(GtRelexpNode a)
    {
        a.e1.acceptMe(this);
        print(" > ",false);
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
        print(" + ",false);
        a.t.acceptMe(this);
        return 0;
    }

    public int visit(MinusExpNode a)
    {
        a.e.acceptMe(this);
        print(" - ",false);
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
        print(" * ",false);
        a.f.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // FACTOR nodes
    public int visit(IDFactorNode a)
    {
        print(a.id,false);
        return 0;
    }

    public int visit(IntliteralFactorNode a)
    {
        Integer i = a.intliteral;
        print(i.toString(),false);
        return 0;
    }

    public int visit(ExpFactorNode a)
    {
        print("( ",false);
        a.e.acceptMe(this);
        print(" )",false);        
        return 0;
    }

    public int visit(FunctionFactorNode a)
    {
        print(a.id + "()",false);
        return 0;
    }

    public int visit(ExpsFunctionFactorNode a)
    {
        print(a.id + "( ",false);
        a.e.acceptMe(this);
        print(" )",false);
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
        print(", ",false);
        a.e.acceptMe(this);
        return 0;
    }

}

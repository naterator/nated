// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

// Phase4CompileVisitor
// 
// This visitor has the privilege of generating >= 386 MASM assembly. The
// output destination is the console, since this is easy and this project
// isn't intended to be an introduction to creating file handles and writing
// to them.

import java.text.*;
import java.util.*;

public class Phase4CompileVisitor implements Visitor
{

    // /////////////////////////////////////////////////////////////////////////
    // local state data
    public Compiler state;
    int labelCount = 0;
    boolean mainVisited = false;
    private Phase4PrintVisitor codePrinter;

    // /////////////////////////////////////////////////////////////////////////
    // private helper functions
    private String makeLabel(String prepend)
    {
        String temp = new String(prepend + "_" + Integer.toString(labelCount));
        labelCount++;
        return temp;
    }

    private void writeLabel(String l)
    {
        writeRaw(l + ":");
        return;
    }

    private void writeCode(String instruction, String parameters, String comment)
    {
        String tmp = new String("");
        tmp = tmp.concat("    ");
        tmp = tmp.concat(Compiler.pad(instruction, 6));
        tmp = tmp.concat(Compiler.pad(parameters, 22));
        tmp = tmp.concat("; " + comment);
        writeRaw(tmp);
        return;
    }

    private void writeCode(String instruction, String comment)
    {
        String tmp = new String("");
        tmp = tmp.concat("    ");
        tmp = tmp.concat(Compiler.pad(instruction, 28));
        tmp = tmp.concat("; " + comment);
        writeRaw(tmp);
        return;
    }

    private void writeCode(String instruction)
    {
        String tmp = new String("");
        tmp = tmp.concat("    ");
        tmp = tmp.concat(instruction);
        writeRaw(tmp);
        return;
    }

    // generic function to output the code, so we can re-target the code
    // output to a file at a later date of development, like when I have a free
    // second in my life
    private void writeRaw(String outputContents)
    {
        System.out.println(outputContents);
    }

    private void writeBlankLine()
    {
        writeRaw("");
    }

    private void boilerPlateStart()
    {
        // get the current date
        SimpleDateFormat formatter = new SimpleDateFormat(
                "EEE MMM dd yyyy hh:mm:ss z");
        Date today = new Date();
        String result = formatter.format(today);

        // spit out boilerplate introduction code
        writeRaw(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;"
                + ";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        writeRaw("; Source D File: " + state.fileName);
        writeRaw("; Converted to MASM by Nate Schmoll's D Translator");
        writeRaw("; " + result);
        writeBlankLine();
        writeRaw("; boilerplate preamble");
        writeCode(".386", "use 32-bit flat address model");
        writeCode(".model flat,c", "with C external linkage conventions");
        writeCode("public asm_main", "entry point - start of compiled code");
        writeCode("extern get:near","function get() exists elsewhere...");
        writeCode("extern put:near","function put() exists elsewhere...");
        writeCode(".code","here be codes...");
        writeBlankLine();
        return;
    }

    private void boilerPlateEnd()
    {
        writeRaw("; END OF CODE");
        writeCode("end");
        writeRaw(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;"
                + ";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
        writeBlankLine();
        return;
    }

    private void writeComment(String comment, boolean tab)
    {
        String tmp = new String("");
        if (tab)
            tmp = tmp.concat("    ");
        tmp = tmp.concat("; ");
        tmp = tmp.concat(comment);
        writeRaw(tmp);
        return;
    }

    private void writeFunctionLabel(String functionName)
    {
        // there is a special case for "main"
        String tmp = new String(functionName);
        if (tmp.equals("main"))
            tmp = tmp.replace("main", "asm_main");
        else
            tmp = new String("f_" + tmp);

        // writeRaw("; function '" + functionName + "'");
        writeRaw(tmp + ":");
        return;
    }

    private int executeProgramNode(ProgramNode a, boolean isPPN)
    {
        // if there are errors, we aren't going to do anything
        if (state.errors.size() > 0)
            return 0;

        // run start boilerplate
        if (a == state.head)
            boilerPlateStart();

        // visit main first!!
        state.functions.get("main").acceptMe(this);

        // check children
        if (isPPN)
            ((ProgramProgramNode) a).program.acceptMe(this);
        a.functiondef.acceptMe(this);

        // run finish boilerplate
        if (a == state.head)
            boilerPlateEnd();

        return 0;
    }

    private void functionBoilerPlateStart(FunctiondefNode a)
    {
        // boilerplate assembly
        int n = a.symbolTableTemplate.currentOffset;
        n = n * -1;
        String o = Integer.toString(n);
        writeBlankLine();
        writeCode("push", "ebp", "save old frame pointer");
        writeCode("mov", "ebp,esp", "new frame pointer");
        writeCode("sub", "esp," + o, "allocate local variables");

        // we don't need these because we don't use them!
        // but i will leave them here for posterity
        // writeCode("push", "ebx", "save callee-saved register");
        // writeCode("push", "edi", "save callee-saved register");
        // writeCode("push", "esi", "save callee-saved register");

        return;
    }

    // /////////////////////////////////////////////////////////////////////////
    // constructors
    public Phase4CompileVisitor()
    {
        codePrinter = new Phase4PrintVisitor();
    }

    public Phase4CompileVisitor(Compiler state)
    {
        this();
        if (state != null)
            state.wrongPhase(this.getClass().toString());
    }

    public Phase4CompileVisitor(Compiler state, Boolean authorized)
    {
        this();
        if (state != null)
            this.state = state;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PROGRAM nodes
    public int visit(ProgramNode a)
    {
        return executeProgramNode(a, false);
    }

    public int visit(ProgramProgramNode a)
    {
        return executeProgramNode(a, true);
    }

    // /////////////////////////////////////////////////////////////////////////
    // FUNCTIONDEF nodes
    public int visit(IntFunctiondefNode a)
    {
        if (!a.id.equals("main") || !mainVisited)
        {
            a.acceptMe(codePrinter);
            state.currentFunction = a;
            state.currentScope = a.symbolTableTemplate;
            writeFunctionLabel(a.id);
            functionBoilerPlateStart(a);
            a.statements.acceptMe(this);
            writeBlankLine();

            if (a.id.equals("main"))
                mainVisited = true;
        }
        return 0;
    }

    public int visit(ParametersIntFunctiondefNode a)
    {
        a.acceptMe(codePrinter);
        state.currentFunction = a;
        state.currentScope = a.symbolTableTemplate;
        writeFunctionLabel(a.id);
        functionBoilerPlateStart(a);
        a.statements.acceptMe(this);
        writeBlankLine();
        return 0;
    }

    public int visit(DeclarationsIntFunctiondefNode a)
    {
        if (!a.id.equals("main") || !mainVisited)
        {
            a.acceptMe(codePrinter);
            state.currentFunction = a;
            state.currentScope = a.symbolTableTemplate;
            writeFunctionLabel(a.id);
            functionBoilerPlateStart(a);
            a.declarations.acceptMe(this);
            a.statements.acceptMe(this);
            writeBlankLine();

            if (a.id.equals("main"))
                mainVisited = true;
        }
        return 0;
    }

    public int visit(ParametersDeclarationsIntFunctiondefNode a)
    {
        a.acceptMe(codePrinter);
        state.currentFunction = a;
        state.currentScope = a.symbolTableTemplate;
        writeFunctionLabel(a.id);
        functionBoilerPlateStart(a);
        a.declarations.acceptMe(this);
        a.statements.acceptMe(this);
        writeBlankLine();
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
        a.statements.acceptMe(this);
        a.statement.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // STATEMENT nodes
    public int visit(SetvalueStatementNode a)
    {
        writeBlankLine();
        a.acceptMe(codePrinter);
        a.exp.acceptMe(this);
        int offset = state.currentScope.getNode(a.id).offset;
        String oset;
        if (offset >= 0)
            oset = new String("+" + Integer.toString(offset));
        else
            oset = new String(Integer.toString(offset));
        writeCode("mov", "[ebp" + oset + "],eax", "set '" + a.id
                + "' to value in eax");
        return 0;
    }

    public int visit(ReturnStatementNode a)
    {
        writeBlankLine();
        a.acceptMe(codePrinter);
        a.exp.acceptMe(this);
        writeCode("", "", "result should be in eax!");

        // we don't need these because we don't use them!
        // but i will leave them here for posterity
        // writeCode("pop", "esi", "restore callee-saved register");
        // writeCode("pop", "edi", "restore callee-saved register");
        // writeCode("pop", "ebx", "restore callee-saved register");

        writeCode("mov", "esp,ebp", "restore base pointer");
        writeCode("pop", "ebp", "restore base pointer");
        writeCode("ret", "LEAVING THIS FUNCTION...");
        writeBlankLine();
        return 0;
    }

    public int visit(IfStatementNode a)
    {
        writeBlankLine();
        a.acceptMe(codePrinter);
        String lbl = makeLabel("if");
        a.boolexp.acceptMe(this);
        writeCode("cmp", "eax,0", "compare eax with zero");
        writeCode("jz", lbl, "find out if boolexp returned false");
        a.statement.acceptMe(this);
        writeLabel(lbl);
        return 0;
    }

    public int visit(IfelseStatementNode a)
    {
        writeBlankLine();
        codePrinter.part = 1;
        a.acceptMe(codePrinter);
        String lbl1 = makeLabel("if");
        String lbl2 = makeLabel("else");
        a.boolexp.acceptMe(this);
        writeCode("cmp", "eax,0", "compare eax with zero");
        writeCode("jz", lbl1, "find out if boolexp returned false");
        codePrinter.part = 2;
        a.acceptMe(codePrinter);
        a.statement.acceptMe(this);
        writeCode("jmp", lbl2, "unconditionally go to " + lbl2);
        // writeBlankLine();
        writeLabel(lbl1);
        codePrinter.part = 3;
        a.acceptMe(codePrinter);
        a.statement2.acceptMe(this);
        writeLabel(lbl2);
        return 0;
    }

    public int visit(WhileStatementNode a)
    {
        writeBlankLine();
        a.acceptMe(codePrinter);
        String lbl1 = makeLabel("whiletest");
        String lbl2 = makeLabel("whilestmt");
        writeCode("jmp", lbl1, "jump straight to test condition");
        writeLabel(lbl2);
        a.statement.acceptMe(this);
        writeLabel(lbl1);
        a.boolexp.acceptMe(this);
        writeCode("cmp", "eax,0", "compare eax with zero");
        writeCode("jnz", lbl2, "find out if boolexp returned false");
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
        a.r.acceptMe(this);

        // negate
        String lbl1 = makeLabel("notboolexp");
        String lbl2 = makeLabel("notboolexp");
        writeCode("cmp", "eax,0", "compare eax with zero");
        writeCode("jz", lbl1, "find out current value");
        writeCode("mov", "eax,0", "set eax to 0 (negation)");
        writeCode("jmp", lbl2, "unconditionally go to " + lbl2);
        writeLabel(lbl1);
        writeCode("mov", "eax,1", "set eax to 1 (negation)");
        writeLabel(lbl2);

        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // RELEXP nodes
    public int visit(EqeqRelexpNode a)
    {
        String lbl1 = makeLabel("eqeqrelexp");
        String lbl2 = makeLabel("eqeqrelexp");

        a.e1.acceptMe(this);
        writeCode("push", "eax", "store result from exp1");
        a.e2.acceptMe(this);
        writeCode("pop", "edx", "get result from exp1");
        writeCode("cmp", "edx,eax", "compare exp1 and exp2");

        writeCode("je", lbl1, "jump if equal");
        writeCode("mov", "eax,0", "set eax to 0 (stmt is false)");
        writeCode("jmp", lbl2, "unconditionally go to " + lbl2);
        writeLabel(lbl1);
        writeCode("mov", "eax,1", "set eax to 1 (stmt is true)");
        writeLabel(lbl2);

        return 0;
    }

    public int visit(GtRelexpNode a)
    {
        String lbl1 = makeLabel("gtrelexp");
        String lbl2 = makeLabel("gtrelexp");

        a.e1.acceptMe(this);
        writeCode("push", "eax", "store result from exp1");
        a.e2.acceptMe(this);
        writeCode("pop", "edx", "get result from exp1");
        writeCode("cmp", "edx,eax", "compare exp1 and exp2");

        writeCode("jg", lbl1, "jump if greater than");
        writeCode("mov", "eax,0", "set eax to 0 (stmt is false)");
        writeCode("jmp", lbl2, "unconditionally go to " + lbl2);
        writeLabel(lbl1);
        writeCode("mov", "eax,1", "set eax to 1 (stmt is true)");
        writeLabel(lbl2);

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
        writeCode("push", "eax", "store result from exp1");
        a.t.acceptMe(this);
        writeCode("pop", "edx", "get result from exp1");
        writeCode("add", "eax,edx", "add exp1+exp2 into eax");

        return 0;
    }

    public int visit(MinusExpNode a)
    {
        a.t.acceptMe(this);
        writeCode("push", "eax", "store result from exp1");
        a.e.acceptMe(this);
        writeCode("pop", "edx", "get result from exp1");
        writeCode("sub", "eax,edx", "subtract exp2 from eax");

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
        writeCode("push", "eax", "store result from exp1");
        a.f.acceptMe(this);
        writeCode("pop", "edx", "get result from exp1");
        writeCode("imul", "eax,edx", "multiply exp2 into exp1 (eax)");

        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // FACTOR nodes
    public int visit(IDFactorNode a)
    {
        int offset = state.currentScope.getNode(a.id).offset;
        String oset;
        if (offset >= 0)
            oset = new String("+" + Integer.toString(offset));
        else
            oset = new String(Integer.toString(offset));
        writeCode("mov", "eax,[ebp" + oset + "]", "set eax to value in '"
                + a.id + "'");

        return 0;
    }

    public int visit(IntliteralFactorNode a)
    {
        String val = Integer.toString(a.intliteral);
        writeCode("mov", "eax," + val, "set eax to " + val);

        return 0;
    }

    public int visit(ExpFactorNode a)
    {
        a.e.acceptMe(this);
        return 0;
    }

    public int visit(FunctionFactorNode a)
    {
        String prepend;
        if (!(a.id.equals("get") || a.id.equals("put")))
            prepend = new String("f_");
        else
            prepend = new String("");
        writeCode("call", prepend + a.id, "execute function '" + a.id + "'");
        return 0;
    }

    public int visit(ExpsFunctionFactorNode a)
    {
        // we need to find the number of bytes being placed
        // on the stack for function parameters
        FunctiondefNode f = (FunctiondefNode) (state.functions.get(a.id));
        int totalBytes = 0;
        int dataType = DataType.UNKNOWN;
        for (int i = 0; i < f.requiredParametersDataType.size(); i++)
        {
            dataType = f.requiredParametersDataType.get(i);
            totalBytes += DataType.byteCount(dataType);
        }

        // now visit the parameters to print the "pushes"
        a.e.acceptMe(this);

        // now run the function
        String prepend;
        if (!(a.id.equals("get") || a.id.equals("put")))
            prepend = new String("f_");
        else
            prepend = new String("");
        writeCode("call", prepend + a.id, "execute function '" + a.id + "'");
        writeCode("", "", "result should be in eax!");

        // and pop the params off the stack
        if (totalBytes > 0)
        {
            String totalBytesS = Integer.toString(totalBytes);
            writeCode("add", "esp," + totalBytesS, "pop off function parms");
        }

        // all done!
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // EXPS nodes
    public int visit(ExpsNode a)
    {
        a.e.acceptMe(this);
        writeCode("push", "eax", "shove it on the stack");
        return 0;
    }

    public int visit(ExpsExpsNode a)
    {
        a.e.acceptMe(this);
        writeCode("push", "eax", "shove it on the stack");
        a.es.acceptMe(this);
        return 0;
    }

}

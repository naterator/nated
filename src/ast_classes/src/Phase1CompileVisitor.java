// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

// Phase1CompileVisitor
// 
// This Visitor goes through and creates template symbol tables and verifies
// that symbols are properly declared, which includes parameter declarations
// and top-of-function declarations

import java.util.Iterator;

public class Phase1CompileVisitor implements Visitor
{

    // /////////////////////////////////////////////////////////////////////////
    // local state data
    public Compiler state;
    private boolean getPutAdded = false;

    // /////////////////////////////////////////////////////////////////////////
    // private helper functions
    
    private void calculateOffsets( FunctiondefNode a )
    {
        SymbolTableNode c;
        SymbolTable s = a.symbolTableTemplate;
        int currentOffset = 8;
        
        // go through and figure out the offsets for parameters
        for( int i = 0; i < a.requiredParametersName.size(); i++ )
        {
            c = s.getNode(a.requiredParametersName.get(i));
            c.offset = currentOffset;
            currentOffset += DataType.byteCount(c.dataType);
        }
        
        // go through and figure out the offsets for non-parameters
        Iterator<String> j = s.iterator();
        while (j.hasNext())
        {            
            // get the next SymbolTableNode
            c = s.nodes().get(j.next());
            
            // basically, if this isn't a parameter, set an offset
            if( c.offset == 0 )
            {
                s.currentOffset -= DataType.byteCount(c.dataType);
                c.offset = s.currentOffset;
            }
        }
    }

    // this goes through and adds pre-defined functions in the language
    private void addGetPut()
    {
        // only run this once!
        if (getPutAdded)
            return;
        getPutAdded = true;

        // add get()
        IntFunctiondefNode getNode = new IntFunctiondefNode("get", null);
        state.functions.put("get", DataType.INT, getNode);

        // add put(int)
        ParametersIntFunctiondefNode putNode = new ParametersIntFunctiondefNode(
                "put", null, null);
        IntParameterNode ipNode = new IntParameterNode("i");
        putNode.requiredParametersName.add("i");
        putNode.requiredParametersDataType.add(DataType.INT);
        putNode.symbolTableTemplate.put("i", DataType.INT, ipNode);
        SymbolTableNode s = putNode.symbolTableTemplate.getNode("i");
        s.offset = 8;
        state.functions.put("put", DataType.INT, putNode);

        // all done!
        return;
    }

    // /////////////////////////////////////////////////////////////////////////
    // constructors
    public Phase1CompileVisitor()
    {
    }

    public Phase1CompileVisitor(Compiler state)
    {
        if (state != null)
            state.wrongPhase(this.getClass().toString());
    }

    public Phase1CompileVisitor(Compiler state, Boolean authorized)
    {
        if (state != null)
            this.state = state;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PROGRAM nodes
    public int visit(ProgramNode a)
    {
        addGetPut();
        a.functiondef.acceptMe(this);
        return 0;
    }

    public int visit(ProgramProgramNode a)
    {
        addGetPut();
        a.program.acceptMe(this);
        a.functiondef.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // FUNCTIONDEF nodes
    public int visit(IntFunctiondefNode a)
    {
        state.currentFunction = a;
        if (!state.functions.put(a.id, DataType.INT, a))
            state.err(a.line, ErrorStrings.DUPLICATE_FUNCTION_NAME, a.id);

        // calculate local variable offsets
        calculateOffsets(a);

        return 0;
    }

    public int visit(ParametersIntFunctiondefNode a)
    {
        state.currentFunction = a;
        if (!state.functions.put(a.id, DataType.INT, a))
            state.err(a.line, ErrorStrings.DUPLICATE_FUNCTION_NAME, a.id);
        a.parameters.acceptMe(this);

        // calculate local variable offsets
        calculateOffsets(a);

        return 0;
    }

    public int visit(DeclarationsIntFunctiondefNode a)
    {
        state.currentFunction = a;
        if (!state.functions.put(a.id, DataType.INT, a))
            state.err(a.line, ErrorStrings.DUPLICATE_FUNCTION_NAME, a.id);
        a.declarations.acceptMe(this);

        // calculate local variable offsets
        calculateOffsets(a);

        return 0;
    }

    public int visit(ParametersDeclarationsIntFunctiondefNode a)
    {
        state.currentFunction = a;
        if (!state.functions.put(a.id, DataType.INT, a))
            state.err(a.line, ErrorStrings.DUPLICATE_FUNCTION_NAME, a.id);
        a.parameters.acceptMe(this);
        a.declarations.acceptMe(this);

        // calculate local variable offsets
        calculateOffsets(a);

        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PARAMETERS node
    public int visit(ParametersNode a)
    {
        a.parameters.acceptMe(this);
        a.parameter.acceptMe(this);

        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PARAMETER node
    public int visit(IntParameterNode a)
    {
        if (!state.currentFunction.requiredParametersName.contains(a.id))
        {
            state.currentFunction.requiredParametersName.add(a.id);
            state.currentFunction.requiredParametersDataType.add(DataType.INT);
        }
        if (!state.currentFunction.symbolTableTemplate.put(a.id, DataType.INT,
                a))
            state.err(a.line, ErrorStrings.DUPLICATE_PARAMETER_NAME, a.id);

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
        if (!state.currentFunction.symbolTableTemplate.put(a.id, DataType.INT,
                a))
            state.err(a.line, ErrorStrings.DUPLICATE_VARIABLE_NAME, a.id);
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

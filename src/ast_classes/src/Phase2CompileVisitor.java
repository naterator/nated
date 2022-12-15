// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

// Phase2CompileVisitor
// 
// This Visitor goes through and analyzes method bodies, doing all the dirty
// work of actually comparing types and whatnot

import java.util.Iterator;

public class Phase2CompileVisitor implements Visitor
{

    // /////////////////////////////////////////////////////////////////////////
    // local state data
    public Compiler state;
    private boolean functionSymbolTablePrinted = false;
    private boolean getPutPrinted = false;
    private boolean mostRecentStatementNodeWasReturn = false;

    // /////////////////////////////////////////////////////////////////////////
    // private helper functions
    private void checkIfReturnIsLast(FunctiondefNode a)
    {
        if (!mostRecentStatementNodeWasReturn)
        {
            state.err(state.currentLine, ErrorStrings.FUNCTION_RETURN, a.id);
        }
    }

    private int checkTypes(int c1, int c2, int line, String errString)
    {
        if (c1 != c2)
        {
            if (!(errString == ErrorStrings.INCOMPATIBLE_TYPES && (c1 == DataType.UNKNOWN || c2 == DataType.UNKNOWN)))
            {
                state.err(line, errString, DataType.toString(c1), DataType
                        .toString(c2));
            }
            return DataType.UNKNOWN;
        }
        else
        {
            return c1;
        }
    }
    
    private void printFunctionSymbolTable()
    {
        if (functionSymbolTablePrinted)
            return;

        functionSymbolTablePrinted = true;

        // if this parameter was passed, print out my symbol table...
        if (state.printSymbolTables)
        {
            String tmp = state.functions.toString();
            System.err.println(tmp.replace("%s", "Functions"));
        }

        return;
    }

    private void printGetPut()
    {
        if (getPutPrinted)
            return;

        getPutPrinted = true;

        if (state.printSymbolTables)
        {
            // if this parameter was passed, print out my symbol table...
            if (state.printSymbolTables)
            {
                IntFunctiondefNode getNode;
                getNode = (IntFunctiondefNode) state.functions.get("get");
                String tmp = getNode.symbolTableTemplate.toString();
                System.err.println(tmp.replace("%s", "get()"));
            }

            // if this parameter was passed, print out my symbol table...
            if (state.printSymbolTables)
            {
                ParametersIntFunctiondefNode putNode;
                putNode = (ParametersIntFunctiondefNode) state.functions
                        .get("put");
                String tmp = putNode.symbolTableTemplate.toString();
                System.err.println(tmp.replace("%s", "put()"));
            }
        }

        return;
    }

    // /////////////////////////////////////////////////////////////////////////
    // constructors
    public Phase2CompileVisitor()
    {
    }

    public Phase2CompileVisitor(Compiler state)
    {
        if (state != null)
            state.wrongPhase(this.getClass().toString());
    }

    public Phase2CompileVisitor(Compiler state, Boolean authorized)
    {
        if (state != null)
            this.state = state;
    }

    // /////////////////////////////////////////////////////////////////////////
    // PROGRAM nodes
    public int visit(ProgramNode a)
    {
        printFunctionSymbolTable();
        printGetPut();
        state.currentLine = a.line;
        a.functiondef.acceptMe(this);
        return 0;
    }

    public int visit(ProgramProgramNode a)
    {
        printFunctionSymbolTable();
        printGetPut();
        state.currentLine = a.line;
        a.program.acceptMe(this);
        a.functiondef.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // FUNCTIONDEF nodes
    public int visit(IntFunctiondefNode a)
    {
        state.currentScope = a.symbolTableTemplate;
        state.currentFunction = a;
        state.currentLine = a.line;
        a.statements.acceptMe(this);

        // if this parameter was passed, print out my symbol table...
        if (state.printSymbolTables)
        {
            String tmp = a.symbolTableTemplate.toString();
            System.err.println(tmp.replace("%s", a.id + "()"));
        }

        // check to make sure we finish with a "return"
        checkIfReturnIsLast(a);
        
        return 0;
    }

    public int visit(ParametersIntFunctiondefNode a)
    {
        state.currentScope = a.symbolTableTemplate;
        state.currentFunction = a;
        state.currentLine = a.line;
        a.statements.acceptMe(this);

        // if this parameter was passed, print out my symbol table...
        if (state.printSymbolTables)
        {
            String tmp = a.symbolTableTemplate.toString();
            System.err.println(tmp.replace("%s", a.id + "()"));
        }

        return 0;
    }

    public int visit(DeclarationsIntFunctiondefNode a)
    {
        state.currentScope = a.symbolTableTemplate;
        state.currentFunction = a;
        state.currentLine = a.line;
        a.statements.acceptMe(this);

        // if this parameter was passed, print out my symbol table...
        if (state.printSymbolTables)
        {
            String tmp = a.symbolTableTemplate.toString();
            System.err.println(tmp.replace("%s", a.id + "()"));
        }

        // check to make sure we finish with a "return"
        checkIfReturnIsLast(a);

        return 0;
    }

    public int visit(ParametersDeclarationsIntFunctiondefNode a)
    {
        state.currentScope = a.symbolTableTemplate;
        state.currentFunction = a;
        state.currentLine = a.line;
        a.statements.acceptMe(this);

        // if this parameter was passed, print out my symbol table...
        if (state.printSymbolTables)
        {
            String tmp = a.symbolTableTemplate.toString();
            System.err.println(tmp.replace("%s", a.id + "()"));
        }

        // check to make sure we finish with a "return"
        checkIfReturnIsLast(a);
        
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
        state.currentLine = a.line;
        a.statements.acceptMe(this);
        a.statement.acceptMe(this);
        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // STATEMENT nodes
    public int visit(SetvalueStatementNode a)
    {
        state.currentLine = a.line;
        mostRecentStatementNodeWasReturn = false;

        // visit child
        a.exp.acceptMe(this);

        // check for existence in the symbol table
        if (!state.currentScope.has(a.id))
        {
            state.currentScope.put(a.id, DataType.UNKNOWN, a);
            state.err(a.line, ErrorStrings.UNDECLARED_SYMBOL, a.id);
        }

        // check for data types
        int c1 = a.exp.dataType;
        int c2 = state.currentScope.getDataType(a.id);
        checkTypes(c1, c2, a.line, ErrorStrings.INCOMPATIBLE_TYPES);

        return 0;
    }

    public int visit(ReturnStatementNode a)
    {
        state.currentLine = a.line;
        mostRecentStatementNodeWasReturn = true;

        // children always come first!
        a.exp.acceptMe(this);

        // make sure we return the value type that the function
        // is declared to return!
        String c = state.currentFunction.id;
        int i = state.functions.getDataType(c);
        if (i != a.exp.dataType)
        {
            state.err(a.line, ErrorStrings.FUNCTION_RETURN_TYPE, c, DataType
                    .toString(a.exp.dataType));
        }

        return 0;
    }

    public int visit(IfStatementNode a)
    {
        state.currentLine = a.line;
        mostRecentStatementNodeWasReturn = false;

        // children always come first!
        a.boolexp.acceptMe(this);
        a.statement.acceptMe(this);

        // check for boolexp type
        if (a.boolexp.dataType != DataType.BOOL)
        {
            state.err(a.line, ErrorStrings.IF_STATEMENT_NONBOOL, DataType
                    .toString(a.boolexp.dataType));
        }

        return 0;
    }

    public int visit(IfelseStatementNode a)
    {
        state.currentLine = a.line;
        mostRecentStatementNodeWasReturn = false;

        // children always come first!
        a.boolexp.acceptMe(this);
        a.statement.acceptMe(this);
        a.statement2.acceptMe(this);

        // check for boolexp type
        if (a.boolexp.dataType != DataType.BOOL)
        {
            state.err(a.line, ErrorStrings.IF_STATEMENT_NONBOOL, DataType
                    .toString(a.boolexp.dataType));
        }

        return 0;
    }

    public int visit(WhileStatementNode a)
    {
        state.currentLine = a.line;
        mostRecentStatementNodeWasReturn = false;

        // children always come first!
        a.boolexp.acceptMe(this);
        a.statement.acceptMe(this);

        // check for boolexp type
        if (a.boolexp.dataType != DataType.BOOL)
        {
            state.err(a.line, ErrorStrings.IF_STATEMENT_NONBOOL, DataType
                    .toString(a.boolexp.dataType));
        }

        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // BOOLEXP nodes
    public int visit(BoolexpNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.r.acceptMe(this);

        // this is a static type, so we can specify it here
        a.dataType = DataType.BOOL;

        // check for compatible comparison types
        if (a.r.dataType != DataType.BOOL)
        {
            state.err(a.r.line, ErrorStrings.INCOMPATIBLE_DATA_BOOLEAN);
        }

        return 0;
    }

    public int visit(NotBoolexpNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.r.acceptMe(this);

        // this is a static type, so we can specify it here
        a.dataType = DataType.BOOL;

        // check for compatible comparison types
        if (a.r.dataType != DataType.BOOL)
        {
            state.err(a.r.line, ErrorStrings.INCOMPATIBLE_DATA_BOOLEAN);
        }

        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // RELEXP nodes
    public int visit(EqeqRelexpNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.e1.acceptMe(this);
        a.e2.acceptMe(this);

        // this is a static type, so we can specify it here
        a.dataType = DataType.BOOL;

        // check for compatible comparison types
        if (a.e1.dataType != DataType.INT)
        {
            state.err(a.e1.line, ErrorStrings.INCOMPATIBLE_TYPES_COMPARISON,
                    DataType.toString(a.e1.dataType));
        }

        if (a.e2.dataType != DataType.INT)
        {
            state.err(a.e2.line, ErrorStrings.INCOMPATIBLE_TYPES_COMPARISON,
                    DataType.toString(a.e2.dataType));
        }

        return 0;
    }

    public int visit(GtRelexpNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.e1.acceptMe(this);
        a.e2.acceptMe(this);

        // this is a static type, so we can specify it here
        a.dataType = DataType.BOOL;

        // check for compatible comparison types
        if (a.e1.dataType != DataType.INT)
        {
            state.err(a.e1.line, ErrorStrings.INCOMPATIBLE_TYPES_COMPARISON,
                    DataType.toString(a.e1.dataType));
        }

        if (a.e2.dataType != DataType.INT)
        {
            state.err(a.e2.line, ErrorStrings.INCOMPATIBLE_TYPES_COMPARISON,
                    DataType.toString(a.e2.dataType));
        }

        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // EXP nodes
    public int visit(ExpNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.t.acceptMe(this);

        // copy over data type
        a.dataType = a.t.dataType;

        return 0;
    }

    public int visit(PlusExpNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.e.acceptMe(this);
        a.t.acceptMe(this);

        // check for data types
        int c1 = a.e.dataType;
        int c2 = a.t.dataType;
        a.dataType = checkTypes(c1, c2, a.line, ErrorStrings.INCOMPATIBLE_TYPES);

        return 0;
    }

    public int visit(MinusExpNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.e.acceptMe(this);
        a.t.acceptMe(this);

        // check for data types
        int c1 = a.e.dataType;
        int c2 = a.t.dataType;
        a.dataType = checkTypes(c1, c2, a.line, ErrorStrings.INCOMPATIBLE_TYPES);

        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // TERM nodes
    public int visit(TermNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.f.acceptMe(this);

        // copy over data type
        a.dataType = a.f.dataType;

        return 0;
    }

    public int visit(MultTermNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.t.acceptMe(this);
        a.f.acceptMe(this);

        // check for data types
        int c1 = a.t.dataType;
        int c2 = a.f.dataType;
        a.dataType = checkTypes(c1, c2, a.line, ErrorStrings.INCOMPATIBLE_TYPES);

        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // FACTOR nodes
    public int visit(IDFactorNode a)
    {
        state.currentLine = a.line;

        // if the ID doesn't exist, create an unknown version of it
        if (!state.currentScope.has(a.id))
        {
            state.currentScope.put(a.id, DataType.UNKNOWN, a);
            state.err(a.line, ErrorStrings.UNDECLARED_SYMBOL, a.id);
        }

        // lookup the ID and find out what kind of data type it is
        a.dataType = state.currentScope.getDataType(a.id);

        return 0;
    }

    public int visit(IntliteralFactorNode a)
    {
        state.currentLine = a.line;

        // this type is static based on the object
        a.dataType = DataType.INT;

        return 0;
    }

    public int visit(ExpFactorNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.e.acceptMe(this);

        // copy over data type
        a.dataType = a.e.dataType;

        return 0;
    }

    public int visit(FunctionFactorNode a)
    {
        state.currentLine = a.line;

        // if the function doesn't exist...
        if (!state.functions.has(a.id))
        {
            // create it
            state.functions.put(a.id, DataType.UNKNOWN, a);
            state.err(a.line, ErrorStrings.UNDECLARED_SYMBOL, a.id);
        }
        else
        {
            // go through and compare all of the parameters to the actual
            // function declarations...tricky stuff
            ASTNode ast = state.functions.get(a.id);
            FunctiondefNode fdn = (FunctiondefNode) ast;

            if (fdn.requiredParametersName.size() != 0)
            {
                state.err(a.line, ErrorStrings.INCORRECT_PARAMETER_COUNT,
                        fdn.id);
            }
        }

        // copy over data type
        a.dataType = state.functions.getDataType(a.id);

        return 0;
    }

    public int visit(ExpsFunctionFactorNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.e.acceptMe(this);

        // if the function doesn't exist...
        if (!state.functions.has(a.id))
        {
            // create it
            state.functions.put(a.id, DataType.UNKNOWN, a);
            state.err(a.line, ErrorStrings.UNDECLARED_SYMBOL, a.id);
        }
        else
        {
            // go through and compare all of the parameters to the actual
            // function declarations...tricky stuff
            ASTNode ast = state.functions.get(a.id);
            FunctiondefNode fdn = (FunctiondefNode) ast;

            if (fdn.requiredParametersName.size() != a.e.count)
            {
                state.err(a.line, ErrorStrings.INCORRECT_PARAMETER_COUNT,
                        fdn.id);
            }
            /*
             * ## THERE USED TO BE A CHECK HERE FOR TYPES IN PARAMETERS, ## BUT
             * THEN I REALIZED THE SPECIFICIATION DOESN'T CALL ## FOR THIS AS A
             * REQUIREMENT! AND WE TALKED IN CLASS ABOUT ## THROWING IT OUT AS A
             * REQUIREMENT...
             * 
             * else { int icur = 0; int iupper = a.e.count; ExpsExpsNode ecur =
             * (ExpsExpsNode) (a.e); ExpsNode bleh = a.e; if (iupper > 1) {
             * 
             * while (iupper > 1) { if( icur != 0) { ecur =
             * (ExpsExpsNode)(ecur.es); } int i =
             * fdn.requiredParametersDataType.get(icur); String k =
             * fdn.requiredParametersName.get(icur); int j = ecur.e.dataType; if
             * (i != j) { state.err(a.line,
             * ErrorStrings.INCOMPTABILE_TYPES_PARAMETER, DataType.toString(i),
             * k); } iupper--; icur++; } } if(iupper==1) { ExpsNode ecur =
             * ecur.es); int i = fdn.requiredParametersDataType.get(icur);
             * String k = fdn.requiredParametersName.get(icur); int j =
             * ecur.e.dataType; if (i != j) { state.err(a.line,
             * ErrorStrings.INCOMPTABILE_TYPES_PARAMETER, DataType.toString(i),
             * k); } } }
             */
        }

        /*
         * System.err.println(a.id + "() w/ " + Integer.toString(a.e.count) + "
         * parameters");
         */

        // copy over data type
        a.dataType = state.functions.getDataType(a.id);

        return 0;
    }

    // /////////////////////////////////////////////////////////////////////////
    // EXPS nodes
    public int visit(ExpsNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.e.acceptMe(this);

        // copy over data type
        a.dataType = a.e.dataType;

        return 0;
    }

    public int visit(ExpsExpsNode a)
    {
        state.currentLine = a.line;

        // children always come first!
        a.es.acceptMe(this);
        a.e.acceptMe(this);

        return 0;
    }

}

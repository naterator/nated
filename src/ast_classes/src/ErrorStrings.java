// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public class ErrorStrings
{
    public static final String DUPLICATE_FUNCTION_NAME = "Function \"%s1()\" is declared more than once.";
    public static final String DUPLICATE_PARAMETER_NAME = "Parameter \"%s1\" is declared more than once.";
    public static final String DUPLICATE_VARIABLE_NAME = "Variable \"%s1\" is declared more than once.";
    public static final String UNDECLARED_SYMBOL = "Symbol \"%s1\" was used, but is undefined. Auto-adding to symtable...";
    public static final String INCOMPATIBLE_TYPES = "Types were used in conjunction, but are incompatible: %s1, %s2";
    public static final String INCORRECT_PARAMETER_COUNT = "Incorrect number of parameters supplied to function \"%s1()\"";
    public static final String INCOMPTABILE_TYPES_PARAMETER = "Mismatched parameter type. Requires \"%s1 %s2\".";
    public static final String INCOMPATIBLE_TYPES_COMPARISON = "Incompatible type being tested in comparison operation: \"%s1\"";
    public static final String INCOMPATIBLE_DATA_BOOLEAN = "Incompatible data was passed in a boolean expression.";
    public static final String FUNCTION_RETURN_TYPE = "Function \"%s1()\" returned wrong type: \"%s2\"";
    public static final String IF_STATEMENT_NONBOOL = "\"if\" statements must be type \"bool\", but \"%s1\" was given";
    public static final String VISITOR_CLASS_INELIGIBLE = "Sorry, but the Visitor class \"%s1\" isn't eligible%s2to be ran in this context.";
    public static final String VISITOR_CLASS_DOESNT_EXIST = "Visitor class \"%s1\" doesn't exist!";
    public static final String ERROR_STACK_TRACE = "Sorry, an error occurred. Here's a stock trace:";
    public static final String EXITING = "Exiting...";
    public static final String USAGE = "Usage";
    public static final String AVAILABLE_VISITOR_CLASSES = "Available Visitor Classes";
    public static final String FUNCTION_RETURN = "Final statement in function \"%s1()\" must be \"return\", but isn't.";
}

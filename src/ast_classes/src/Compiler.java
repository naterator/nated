import java.lang.reflect.*;
import java.util.*;
import java.text.*;
// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

// Compiler
//
// This class retains the entire state of the compiler throughout
// the entire compilation process.

public class Compiler
{
    // /////////////////////////////////////////////////////////////////////////
    // general compiler state data
    public LinkedList<String> errors;
    public SymbolTable functions;
    public ProgramNode head;
    public Stack<SymbolTable> stack;
    public SymbolTable currentScope;
    public FunctiondefNode currentFunction;
    public int phaseMax;
    public int phaseCurrent;
    public static String nl = System.getProperty("line.separator");
    public boolean printSymbolTables = false;
    public int currentLine = 0;
    public String fileName;

    // /////////////////////////////////////////////////////////////////////////
    // constructors
    public Compiler()
    {
        this.functions = new SymbolTable();
        this.errors = new LinkedList<String>();
        this.stack = new Stack<SymbolTable>();
        this.phaseCurrent = 1;
    }

    public Compiler(ProgramNode head)
    {
        this();
        this.head = head;
    }

    // /////////////////////////////////////////////////////////////////////////
    // helper functions
    public void executePhases()
    {
        boolean stopRunning = false;

        while (!stopRunning)
        {
            String t = new String("Phase" + Integer.toString(phaseCurrent)
                    + "CompileVisitor");
            try
            {
                Class a = Class.forName(t);
                Class partypes[] = new Class[2];
                partypes[0] = Compiler.class;
                partypes[1] = Boolean.class;
                Constructor ac = a.getConstructor(partypes);
                Object arglist[] = new Object[2];
                arglist[0] = this;
                arglist[1] = true;
                Visitor v = (Visitor) (ac.newInstance(arglist));
                this.head.acceptMe(v);
            }
            catch (Exception e)
            {
                stopRunning = true;
            }
            phaseCurrent++;
        }
    }

    public void wrongPhase(String className)
    {
        className = className.replace("class ", "");
        String err = ErrorStrings.VISITOR_CLASS_INELIGIBLE;
        err = err.replace("%s1", className);
        err = err.replace("%s2", nl);
        System.out.println(err);
    }

    public void err(int line, String stringToDisplay, String parm, String parm2)
    {
        String tmp = new String(Integer.toString(line) + ": " + stringToDisplay);
        tmp = tmp.replace("%s1", parm);
        tmp = tmp.replace("%s2", parm2);
        errors.add(tmp);
        return;
    }

    public void err(int line, String stringToDisplay, String parm)
    {
        String tmp = new String(Integer.toString(line) + ": " + stringToDisplay);
        tmp = tmp.replace("%s1", parm);
        errors.add(tmp);
        return;
    }

    public void err(String stringToDisplay, String parm)
    {
        String tmp = new String("NA: " + stringToDisplay);
        tmp = tmp.replace("%s1", parm);
        errors.add(tmp);
        return;
    }

    public void err(int line, String stringToDisplay)
    {
        String tmp = new String(Integer.toString(line) + ": " + stringToDisplay);
        errors.add(tmp);
        return;
    }

    public void err(String stringToDisplay)
    {
        errors.add(stringToDisplay);
        return;
    }

    public static String pad(String stringToPad, int size)
    {
        StringCharacterIterator sci;
        StringBuffer strb;
        String padder = new String(" ");

        if (padder.length() == 0)
        {
            return stringToPad;
        }
        strb = new StringBuffer(stringToPad);
        sci = new StringCharacterIterator(padder);

        while (strb.length() < size)
        {
            for (char ch = sci.first(); ch != CharacterIterator.DONE; ch = sci
                    .next())
            {
                if (strb.length() < size)
                {
                    strb.append(String.valueOf(ch));
                }
            }
        }
        return strb.toString();
    }
}

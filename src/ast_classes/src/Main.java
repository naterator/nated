import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.jar.*;

// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

// Main
//
// This is the primary class which is, by default, invoked upon
// the execution of the JAR. Sure, it's a bit messy and could
// use some commenting, but that is not the purpose behind this
// project.

public class Main
{
    static public void main(String argv[])
    {
        System.err.println("");
        System.err.println("Nate Schmoll's D Translator for MASM >=386");
        System.err.println("");

        // find out the current JAR file's name
        java.net.URL classUrl = new Main().getClass()
                .getResource("/Main.class");
        String myName = classUrl.toString();
        myName = myName.replaceAll("!/Main.class", "");
        myName = myName.substring(myName.lastIndexOf("/") + 1, myName
                .lastIndexOf(".jar") + 4);

        String s = new String("-s");

        /* Start the parser */
        if (argv.length == 1 || (argv.length == 2 && s.equals(argv[0])))
        {
            String file;
            boolean showSymbolTables;
            if (argv.length == 2)
            {
                file = argv[1];
                showSymbolTables = true;
            }
            else
            {
                file = argv[0];
                showSymbolTables = false;
            }

            try
            {
                NSParser p = new NSParser(new NSScanner(new FileReader(file)));
                Object result = p.parse().value;
                ProgramNode head = (ProgramNode) result;

                // create a compiler state object
                Compiler state = new Compiler(head);
                state.fileName = new String(file);

                // decide if we want to print symbol tables
                if (showSymbolTables)
                    state.printSymbolTables = true;
                else
                    state.printSymbolTables = false;

                // call the visitors
                state.executePhases();

                System.err.println(ErrorStrings.EXITING);
            }
            catch (Exception e)
            {
                System.err.println(ErrorStrings.ERROR_STACK_TRACE);
                System.err.println("");
                e.printStackTrace();
            }
        }
        else if (argv.length == 2 || (argv.length == 3 && s.equals(argv[0])))
        {
            String file;
            String cls;
            if (argv.length == 3)
            {
                file = argv[1];
                cls = argv[2];
            }
            else
            {
                file = argv[0];
                cls = argv[1];
            }

            try
            {
                NSParser p = new NSParser(new NSScanner(new FileReader(file)));

                // get the top of the AST
                Object result = p.parse().value;
                ProgramNode head = (ProgramNode) result;

                // create a compiler state object
                Compiler state = new Compiler(head);
                state.fileName = new String(file);

                // sick the Visitor on the AST!!
                try
                {
                    // go through and attempt to load the specified Visitor
                    // class
                    Class a = Class.forName(cls);
                    Class partypes[] = new Class[1];
                    partypes[0] = Compiler.class;
                    Constructor ac = a.getConstructor(partypes);
                    Object arglist[] = new Object[1];
                    arglist[0] = state;
                    ac.newInstance(arglist);
                    System.err.println("");
                    System.err.println(ErrorStrings.EXITING);
                }
                catch (Exception e)
                {
                    String vcdne = new String(
                            ErrorStrings.VISITOR_CLASS_DOESNT_EXIST);
                    vcdne = vcdne.replace("%s1", cls);
                    System.err.println(vcdne);
                }

            }
            catch (Exception e)
            {
                System.err.println(ErrorStrings.ERROR_STACK_TRACE);
                System.err.println("");
                e.printStackTrace();
            }
        }
        else
        {
            System.err.println(ErrorStrings.USAGE + ": java -jar " + myName
                    + " [-s] <source_file> [<Visitor>]");
            System.err.println("");
            System.err.println("Options:");
            System.err
                    .println("   -s   Show function and local symbol tables (invalid when");
            System.err.println("        specifying a visitor)");

            System.err.println("");
            
            System.err.println("Code is output to stdout; all other output is directed to");
            System.err.println("stderr. With this in mind, you can redirect the console");
            System.err.println("assembly output to a file. For example:");
            System.err.println("");
            System.err.println("   java -jar " + myName + " sample.d > sample.asm");
            
            System.err.println("");

            System.err.println(ErrorStrings.AVAILABLE_VISITOR_CLASSES + ":");

            try
            {
                JarFile jar = new JarFile(myName);
                Enumeration en = jar.entries();
                while (en.hasMoreElements())
                {
                    JarEntry smack = (JarEntry) en.nextElement();
                    String name = smack.getName().toString();
                    if (name.endsWith(".class"))
                    {
                        name = name.substring(0, name.length() - 6);
                        try
                        {
                            // Try to create an instance of the object
                            Object o = Class.forName(name).newInstance();
                            if (o instanceof Visitor)
                            {
                                System.err.println(name);
                            }
                        }
                        catch (ClassNotFoundException cnfex)
                        {
                            // System.err.println(cnfex);
                        }
                        catch (InstantiationException iex)
                        {
                            // We try to instantiate an interface
                            // or an object that does not have a
                            // default constructor
                        }
                        catch (IllegalAccessException iaex)
                        {
                            // The class is not public
                        }
                        catch (Exception e)
                        {
                            // all others
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        System.err.println("");
    }
}

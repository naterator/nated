// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

import java.util.*;

public class SymbolTable
{
    // ////////////////////////////////////////////////////////////////////////
    // local state data
    private HashMap<String, SymbolTableNode> nodes;
    public static String nl = System.getProperty("line.separator");
    public int currentOffset = 0;

    // ////////////////////////////////////////////////////////////////////////
    // constructors
    public SymbolTable()
    {
        nodes = new HashMap<String, SymbolTableNode>();
    }

    // ////////////////////////////////////////////////////////////////////////
    // utility functions
    public String toString()
    {
        // the final result (output string)
        String o = new String();
        
        // let's give everything some titles
        o = o.concat("================================================"+nl);
        o = o.concat("%s"+nl);
        o = o.concat("================================================"+nl);
        o = o.concat(Compiler.pad("Variable Name", 20));
        o = o.concat(Compiler.pad("Data Type", 13));
        o = o.concat(Compiler.pad("Offset", 15)+nl);
        o = o.concat("------------------------------------------------"+nl);
        
        // now iterate through each ST entry and print it out
        Iterator<String> j = nodes.keySet().iterator();
        while(j.hasNext())
        {
            o = o.concat(nodes.get(j.next()).toString()+nl);
        }
        
        return o;
    }

    // ////////////////////////////////////////////////////////////////////////
    // public functions

    // returns false if the key already exists and this is the second entry
    // true otherwise; this is really purposed to instruct the compiler to
    // display an error message or not
    public Iterator<String> iterator()
    {
        return nodes.keySet().iterator();
    }
    
    public HashMap<String,SymbolTableNode> nodes()
    {
        return nodes;
    }
    
    public boolean put(String name, int type, ASTNode obj)
    {
        if (has(name))
        {
            nodes.get(name).appearanceCount++;
            if (nodes.get(name).appearanceCount == 2)
                return false;
            else
                return true;
        }
        else
        {
            SymbolTableNode tmp = new SymbolTableNode();
            tmp.appearanceCount = 1;
            tmp.astNodeObject = obj;
            tmp.dataType = type;
            tmp.name = name;
            nodes.put(name, tmp);
            return true;
        }
    }

    public boolean put(String name, int type, ASTNode obj, int offset)
    {
        boolean has = has(name);
        boolean ret = put(name, type, obj);
        if (!has)
            nodes.get(name).offset = offset;
        return ret;
    }

    // returns false if the key doesn't exist, returns true otherwise
    public boolean has(String name)
    {
        return nodes.containsKey(name);
    }

    // if exists, returns ASTNode object, otherwise null; we need the extra test
    // in here, because HashMap can return null even if a key exists, in the
    // case that the key's value is null
    public ASTNode get(String name)
    {
        if (has(name))
        {
            return nodes.get(name).astNodeObject;
        }
        else
        {
            return null;
        }
    }
    
    public SymbolTableNode getNode(String name)
    {
        if (has(name))
        {
            return nodes.get(name);
        }
        else
        {
            return null;
        }
    }
    
    public int getDataType(String name)
    {
        if(has(name))
        {
            return nodes.get(name).dataType;
        }
        else
        {
            return DataType.UNKNOWN;
        }
    }
}

// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

// DataType
//
// This class contains the various datatypes necessary to interpret
// code in this D language subset.

public class DataType
{
    public static final int UNKNOWN = 0;
    public static final int INT = 1;
    public static final int BOOL = 2;
    
    public static int byteCount( int dt )
    {
        switch(dt)
        {
            case UNKNOWN:
                return 0;
            case INT:
                return 4;
            case BOOL:
                return 0;
            default:
                return -1;
        }
    }
    
    public static String toString( int dt )
    {
        switch(dt)
        {
            case UNKNOWN:
                return "unknown";
            case INT:
                return "int";
            case BOOL:
                return "bool";
            default:
                return "[unknown datatype specified]";
        }
    }
}

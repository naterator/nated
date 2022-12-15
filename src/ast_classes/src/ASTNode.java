// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

public abstract class ASTNode
{
    // current location data, for providing useful feedback to the user
    public int line = -1;
    
    // all of my children must implement these
    abstract public int acceptAll(Visitor v);
    abstract public int acceptMe(Visitor v);
    abstract public String toString();
}

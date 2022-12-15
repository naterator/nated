// Nate Schmoll's D Translator for MASM >=386
// Copyright (c) 2007, Nate Schmoll. All rights reserved.
// This code may not be reused or reproduced without the 
// explicit written consent of its author.

/* D Scanner, by Nate Schmoll */

/* BUILT FOR JFLEX */

import java_cup.runtime.*;

%%

%class NSScanner
%unicode
%cup
%line
%column
%standalone
%pack

%{

	static int tokensPrinted = 0;
	static int tokensProcessed = 0;

	static boolean debugMode = false;

	private void debugPrint( String s )
	{
		if( debugMode )
		{
			if( tokensPrinted != 0 )
				System.err.println("---------------------------");

			System.err.println( "  token: " + s );
			System.err.println( "row,col: " + (yyline+1) + "," + (yycolumn+1) );
			tokensPrinted++;
		}
		tokensProcessed++;
	}

	private void debugPrint( String s, String v )
	{
		if( debugMode )
		{
			debugPrint( s );
			System.err.println( "  value: " + v );
		}
	}

	StringBuffer string = new StringBuffer();

	private Symbol symbol(int type)
	{
		return new Symbol(type, yyline, yycolumn);
	}

	private Symbol symbol(int type, Object value)
	{
		return new Symbol(type, yyline, yycolumn, value);
	}

%}

/* basics */
LINETERMINATOR = \r|\n|\r\n
INPUTCHARACTER = [^\r\n]
WHITESPACE     = {LINETERMINATOR} | [ \t\f\b\012]
COMMENT        = "//" {INPUTCHARACTER}* {LINETERMINATOR}

/* undefined nonterminals */
ID             = [a-zA-Z][0-9a-zA-Z_]*
INTLITERAL     = [0-9]+

%state YYINITIAL

%%

/* all of the shenanigans starts here */
<YYINITIAL> {

	/* keywords */
	"else"			{ debugPrint("ELSE"); return symbol( sym.ELSE ); }
	"if"			{ debugPrint("IF"); return symbol( sym.IF ); }
	"int"			{ debugPrint("INT"); return symbol( sym.INT ); }
	"return"		{ debugPrint("RETURN"); return symbol( sym.RETURN ); }
	"while"			{ debugPrint("WHILE"); return symbol( sym.WHILE ); }

	/* seperators */
	"("				{ debugPrint("LPAREN"); return symbol( sym.LPAREN ); }
	")"             { debugPrint("RPAREN"); return symbol( sym.RPAREN ); }
	"{"             { debugPrint("LBRACE"); return symbol( sym.LBRACE ); }
	"}"             { debugPrint("RBRACE"); return symbol( sym.RBRACE ); }
	";"             { debugPrint("SEMICOLON"); return symbol( sym.SEMICOLON ); }
	","             { debugPrint("COMMA"); return symbol( sym.COMMA ); }

	/* operators */
	"=="            { debugPrint("EQEQ"); return symbol( sym.EQEQ ); }
	"="             { debugPrint("EQ"); return symbol( sym.EQ ); }
	"!"             { debugPrint("NOT"); return symbol( sym.NOT ); }
	"+"             { debugPrint("PLUS"); return symbol( sym.PLUS ); }
	"-"             { debugPrint("MINUS"); return symbol( sym.MINUS ); }
	"*"             { debugPrint("MULT"); return symbol( sym.MULT ); }
	">"				{ debugPrint("GT"); return symbol( sym.GT ); }

	/* integers */
	{INTLITERAL}	{ debugPrint("INTLITERAL", yytext());
					  return symbol( sym.INTLITERAL, new Integer(yytext()) ); }

	/* comments */
	{COMMENT}		{ /* ignore */ }

	/* whitespace */
	{WHITESPACE}	{ /* ignore */ }

	/* identifiers */
	{ID}			{ debugPrint("ID", yytext());
					  return symbol( sym.ID, yytext() ); }

}

/* error handling */
.|\n				{ if( tokensPrinted != 0 )
						System.err.println("---------------------------");
						System.err.println("Syntax error at line "+(yyline+1)+
						", column "+(yycolumn+1)+" <"+yytext()+">");
						tokensPrinted++; }

/*
<<EOF>>				{ System.out.println("Valid Tokens: " + 
                        Integer.toString(tokensProcessed));
                        return symbol( sym.EOF ); }

*/
<<EOF>>             { return symbol( sym.EOF ); }
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}


%%


" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext());}
"read" 		{ return new_symbol(sym.READ, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"const" 	{ return new_symbol(sym.CONST, yytext()); }

"if" 		{ return new_symbol(sym.IF, yytext()); }
"else" 		{ return new_symbol(sym.ELSE, yytext()); }
"do" 		{ return new_symbol(sym.DO, yytext()); }
"while" 	{ return new_symbol(sym.WHILE, yytext()); }
"break" 	{ return new_symbol(sym.BREAK, yytext()); }
"continue" 	{ return new_symbol(sym.CONTINUE, yytext()); }
"goto" 		{ return new_symbol(sym.GOTO, yytext()); }

"true" 		{ return new_symbol(sym.TRUE, yytext()); }
"false" 	{ return new_symbol(sym.FALSE, yytext()); }

"class" 	{ return new_symbol(sym.CLASS, yytext()); }
"extends" 	{ return new_symbol(sym.EXTENDS, yytext()); }
"new" 		{ return new_symbol(sym.NEW, yytext()); }
"." 		{ return new_symbol(sym.DOT, yytext()); }
"record" 	{ return new_symbol(sym.RECORD, yytext()); }
"this" 		{ return new_symbol(sym.THIS, yytext()); }
"super" 	{ return new_symbol(sym.SUPER, yytext()); }

"==" 		{ return new_symbol(sym.IS_EQ, yytext()); }
"!=" 		{ return new_symbol(sym.IS_NEQ, yytext()); }
">" 		{ return new_symbol(sym.IS_GRE, yytext()); }
">=" 		{ return new_symbol(sym.IS_GEQ, yytext()); }
"<" 		{ return new_symbol(sym.IS_LESS, yytext()); }
"<=" 		{ return new_symbol(sym.IS_LEQ, yytext()); }
"&&" 		{ return new_symbol(sym.AND_AND, yytext()); }
"||" 		{ return new_symbol(sym.OR_OR, yytext()); }

"++" 		{ return new_symbol(sym.PLUS_PLUS, yytext()); }
"--" 		{ return new_symbol(sym.MINUS_MINUS, yytext()); }

"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"*" 		{ return new_symbol(sym.TIMES, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"%" 		{ return new_symbol(sym.PERCENT, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
":" 		{ return new_symbol(sym.COLON, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"[" 		{ return new_symbol(sym.LBRACKET, yytext()); }
"]"			{ return new_symbol(sym.RBRACKET, yytext()); }

"int"		{ return new_symbol(sym.INT, yytext()); }
"bool"		{ return new_symbol(sym.BOOL, yytext()); }
"char"		{ return new_symbol(sym.CHAR, yytext()); }

"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

/*
"\""[ -~]*"\"" { String s = yytext(); return new_symbol (sym.STRING, s.substring(1, s.length() -1) ); }
*/

"\'"[ -~]"\'" { String s = yytext(); return new_symbol (sym.CHARACTER, new Character(s.charAt(1))); }
[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
([a-z]|[A-Z])[a-zA-Z0-9_]* 	{return new_symbol (sym.IDENT, yytext()); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1) + ", u koloni "+ yycolumn ); }









grammar Expr;

@lexer::header {
    package model.Expr;
}

@parser::header {
    package model.Expr;
}

expresion
:
    expresion AND expresion
    | expresion OR expresion
    | NOT expresion
;


AND
:
    'and' | '&&' | '&'
;

OR
:
    'or' | '||' | '|'

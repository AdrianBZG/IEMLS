grammar Expr;

@lexer::header {
    package model.Expr;
}

@parser::header {
    package model.Expr;
}

add
    :    NUMBER PLUS NUMBER
    ;

NUMBER
    :    ('0'..'9')+
    ;

PLUS
    :    ('+')
    ;


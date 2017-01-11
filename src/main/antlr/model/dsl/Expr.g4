grammar Expr;

@lexer::header {
    package model.dsl.Expr;
}

@parser::header {
    package model.dsl.Expr;
    import util.Directions;
    import model.dsl.IEval;
}


expresion returns [IEval e]
:
    expr EOF
        { $e = $expr.e; }
;


expr returns [IEval e] @init{ $e = new IEval(); }
:
    FREE atom
        { $e.free($atom.d); }
    | PARENOPEN expr PARENCLOSE // don't do anything why it should be identity action (no modified $e)
    | VISITED atom
        { $e.visited($atom.d); }
    | e1=expr AND e2=expr
        { $e = $e1.e.and($e2.e); }
    | e1=expr OR e2=expr
        { $e = $e1.e.or($e2.e); }
    | NOT expr
        { $e.not($expr.e); }
;

atom returns [Directions d]
:
    'left' { $d = Directions.LEFT; }
    | 'right' { $d = Directions.RIGHT; }
    | 'up' { $d = Directions.UP; }
    | 'down' { $d = Directions.DOWN; }
;

// Tokens

FREE
:
    'free'
;

VISITED
:
    'visited'
;

AND
:
    'and' | '&&' | '&'
;

OR
:
    'or' | '||' | '|'
;

NOT
:
    'not' | '!'
;

PARENOPEN
:
    '('
;

PARENCLOSE
:
    ')'
;

COMMENT: '//' ~[\r\n]* -> skip ;

BLANK: [ \t\n\r]+ -> skip ;

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
:   { $e = new IEval(); }
    FREE atom
        { $e.free($atom.d); }
    | VISITED atom
        { $e.visited($atom.d); }
    | e1=expresion AND e2=expresion
        { $e.and($e1.e, $e2.e); }
    | e1=expresion OR e2=expresion
        { $e.or($e1.e, $e2.e); }
    | NOT expresion
        { $e.not($expresion.e); }
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

COMMENT: '//' ~[\r\n]* -> skip ;

BLANK: [ \t\n\r]+ -> skip ;

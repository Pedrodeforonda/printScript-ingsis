definition    → expression ;  
expression     → literal | unary | binary | grouping ;  
literal        → NUMBER | STRING | "true" | "false" | "nil" ;  
grouping       → "(" expression ")" ;  
unary          → ( "-" | "!" ) expression ;  
binary         → expression operator expression ;  
operator       → "==" | "!=" | "<" | "<=" | ">" | ">=" | "+"  | "-"  | "*" | "/" ;

### The grammar is ambiguous, so we need to resolve it by adding precedence rules.

sentence    → definition ;
definition  → assignment | letDeclaration | typeDefinition | expression ;
expression     → equality ;
equality       → comparison ( ( "!=" | "==" ) comparison )* ;
comparison     → addition ( ( ">" | ">=" | "<" | "<=" ) addition )* ;
addition       → multiplication ( ( "-" | "+" ) multiplication )* ;
multiplication → unary ( ( "/" | "*" ) unary )* ;
unary          → ( "-" | "!" ) unary | literal ;
literal        → NUMBER | STRING | "true" | "false" | "nil" | grouping ;
grouping       → "(" expression ")" ;


then we can generate an interface for nodes and each precedence level will be a class that implements this interface.

```puml
@startuml
interface Expr {
    accept(visitor: Visitor<R>): R
}
class Binary {
    left: Expr
    operator: main.Token
    right: Expr
    accept(visitor: Visitor<R>): R
}
class Grouping {
    expression: Expr
    accept(visitor: Visitor<R>): R
}
class Literal {
    value: Object
    accept(visitor: Visitor<R>): R
}
class Unary {
    operator: main.Token
    right: Expr
    accept(visitor: Visitor<R>): R
}
class Visitor<R> {
    visitBinaryExpr(expr: Binary): R
    visitGroupingExpr(expr: Grouping): R
    visitLiteralExpr(expr: Literal): R
    visitUnaryExpr(expr: Unary): R
}
Expr <|-- Binary
Expr <|-- Grouping
Expr <|-- Literal
Expr <|-- Unary
@enduml
```

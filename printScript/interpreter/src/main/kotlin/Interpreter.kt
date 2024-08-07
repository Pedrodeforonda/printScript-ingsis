package org.example

import org.example.nodes.Node
import org.example.visitors.EvalVisitor

class Interpreter {

    fun interpret(expression: Node): Any {
         val evalVisitor: EvalVisitor = EvalVisitor()
         expression.accept(evalVisitor)

        return Unit
    }
}
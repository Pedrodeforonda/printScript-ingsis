package org.example

import org.example.nodes.Node
import org.example.visitors.EvalVisitor

class Interpreter {

    fun interpret(expression: List<Node>): Any {
        val variableMap = mutableMapOf<String, Any>()
         val evalVisitor: EvalVisitor = EvalVisitor(variableMap)
         for(exp in expression){
             exp.accept(evalVisitor)
         }

        return Unit
    }
}
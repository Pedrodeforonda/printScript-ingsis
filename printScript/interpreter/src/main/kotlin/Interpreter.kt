package org.example

import nodes.Node

class Interpreter {

    private var variableMap = mutableMapOf<String, Any>()

    fun interpret(expression: List<Node>): Any {
         val evalVisitor = EvalVisitor(variableMap)
         for(exp in expression){
             exp.accept(evalVisitor)
         }

        return Unit
    }

    fun getVariableMap(): Map<String, Any> {
        return variableMap
    }
}
package org.example

import org.example.nodes.Node

class Interpreter {

    private val variableMap = mutableMapOf<String, Any>()

    fun interpret(expression: List<Node>): Any {
        val evalVisitor = EvalVisitor(variableMap)
        for(exp in expression){
            exp.accept(evalVisitor)
        }

        return Unit
    }

    fun getVariableMap(): MutableMap<String, Any> {
        return variableMap
    }
}
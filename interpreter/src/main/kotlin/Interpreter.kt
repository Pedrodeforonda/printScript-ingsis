package org.example

import nodes.Node

class Interpreter {

    private var variableMap = mutableMapOf<Pair<String, String>, Any>()

    fun interpret(expression: List<Node>): Any {
        val evalVisitor = EvalVisitor(variableMap)
        for (exp in expression) {
            exp.accept(evalVisitor)
        }

        return Unit
    }

    fun getVariableMap(): Map<Pair<String, String>, Any> {
        return variableMap
    }
}

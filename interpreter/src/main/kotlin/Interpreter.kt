package org.example

import nodes.Node

class Interpreter {

    private var variableMap = mutableMapOf<Pair<String, String>, Any>()

    fun interpret(astNodes: Sequence<Node>): Any {
        val iterator = astNodes.iterator()
        val evalVisitor = EvalVisitor(variableMap)
        for (exp in iterator) {
            exp.accept(evalVisitor)
        }

        return Unit
    }

    fun getVariableMap(): Map<Pair<String, String>, Any> {
        return variableMap
    }
}
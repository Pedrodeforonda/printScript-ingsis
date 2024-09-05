package org.example.interpreter

import utils.InterpreterResult
import utils.ParsingResult
import utils.PrintlnCollector

class Interpreter {

    private var variableMap = mutableMapOf<Pair<String, String>, Any>()
    private val printlnCollector = PrintlnCollector()

    fun interpret(astNodes: Sequence<ParsingResult>): Sequence<InterpreterResult> = sequence {
        val iterator = astNodes.iterator()
        val evalVisitor = EvalVisitor(variableMap, printlnCollector)
        while (iterator.hasNext()) {
            val exp = iterator.next()
            if (!exp.hasError()) {
                try {
                    exp.getAst().accept(evalVisitor)
                } catch (e: Exception) {
                    yield(InterpreterResult(null, e))
                }
            }
            if (printlnCollector.hasPrints()) {
                printlnCollector.yieldPrints().forEach {
                    yield(InterpreterResult(it, null))
                }
            } else if (exp.hasError()) {
                yield(InterpreterResult(null, exp.getError()))
            }
        }
    }

    fun getVariableMap(): Map<Pair<String, String>, Any> {
        return variableMap
    }
}

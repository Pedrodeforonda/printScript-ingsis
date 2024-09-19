package interpreter

import dataObjects.ParsingResult
import utils.InterpreterResult
import utils.PercentageCollector
import utils.PrintlnCollector
import utils.StringInputProvider

class Interpreter(
    private val percentageCollector: PercentageCollector,
    private val inputValues: StringInputProvider,
    private val envVariables: Map<String, String>,
    private val canPrint: Boolean = true,
) {

    private var variableMap = mutableMapOf<String, Triple<Any, String, Boolean>>()
    private val printlnCollector = PrintlnCollector()

    fun interpret(astNodes: Sequence<ParsingResult>): Sequence<InterpreterResult> = sequence {
        val iterator = astNodes.iterator()
        val evalVisitor = EvalVisitor(variableMap, printlnCollector, inputValues, envVariables, canPrint)
        while (iterator.hasNext()) {
            val exp = iterator.next()
            if (!parsingError(exp)) {
                try {
                    exp.getAst().accept(evalVisitor)
                } catch (e: Exception) {
                    yield(InterpreterResult(null, e, percentageCollector.getPercentage()))
                }
            }
            if (printlnCollector.hasPrints()) {
                printlnCollector.yieldPrints().forEach {
                    yield(InterpreterResult(it, null, percentageCollector.getPercentage()))
                }
                printlnCollector.clearPrints()
            } else if (parsingError(exp)) {
                yield(InterpreterResult(null, exp.getError(), percentageCollector.getPercentage()))
            } else {
                yield(InterpreterResult(null, null, percentageCollector.getPercentage()))
            }
        }
    }

    private fun parsingError(exp: ParsingResult) = exp.hasError()

    fun getVariableMap(): Map<String, Triple<Any, String, Boolean>> {
        return variableMap
    }
}

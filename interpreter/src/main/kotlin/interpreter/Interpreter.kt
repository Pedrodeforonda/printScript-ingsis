package interpreter

import utils.InterpreterResult
import utils.ParsingResult
import utils.PercentageCollector
import utils.PrintlnCollector
import utils.StringInputProvider

class Interpreter(
    private val percentageCollector: PercentageCollector,
    private val inputValues: StringInputProvider,
    private val envVariables: Map<String, String>,
) {

    private var variableMap = mutableMapOf<Triple<String, String, Boolean>, Any>()
    private val printlnCollector = PrintlnCollector()

    fun interpret(astNodes: Sequence<ParsingResult>): Sequence<InterpreterResult> = sequence {
        val iterator = astNodes.iterator()
        val evalVisitor = EvalVisitor(variableMap, printlnCollector, inputValues, envVariables)
        while (iterator.hasNext()) {
            val exp = iterator.next()
            if (!exp.hasError()) {
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
            } else if (exp.hasError()) {
                yield(InterpreterResult(null, exp.getError(), percentageCollector.getPercentage()))
            } else {
                yield(InterpreterResult(null, null, percentageCollector.getPercentage()))
            }
        }
    }

    fun getVariableMap(): Map<Triple<String, String, Boolean>, Any> {
        return variableMap
    }

    fun getInputValues(): StringInputProvider {
        return inputValues
    }

    fun getEnvVariables(): Map<String, String> {
        return envVariables
    }
}

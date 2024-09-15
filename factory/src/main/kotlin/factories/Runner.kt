package factories

import runners.Runner
import utils.InterpreterResult
import utils.StringInputProvider
import java.io.InputStream

class Runner {

    private val runner: Runner = Runner()
    fun run(
        src: InputStream,
        version: String,
        inputProvider: StringInputProvider,
        envMap: Map<String, String>,
        canPrint: Boolean = true,
    ): Sequence<InterpreterResult> = sequence {
        runner.run(src, version, inputProvider, envMap, canPrint).forEach { yield(it) }
    }

    fun getPercentage() = runner.getPercentage()
}

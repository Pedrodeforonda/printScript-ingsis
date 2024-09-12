package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import main.Runner
import utils.InteractiveInputProvider
import utils.InterpreterResult
import java.io.File
import kotlin.math.round

class ExecutorOperation : CliktCommand(
    name = "execute",
    help = "Execute the source file.",
) {
    private val sourceFile: File by argument(help = "Source file to process.")
        .file(mustExist = true)

    private val envFile: File by argument(help = "Environment file to use.")
        .file(mustExist = false)

    private val version: String by argument(help = "Version of the language.")

    override fun run() {
        val inputStream = sourceFile.inputStream()
        val runner = Runner()
        val envMap = fileToMap(envFile)
        val results: Sequence<InterpreterResult> = runner.run(
            inputStream,
            version,
            InteractiveInputProvider(),
            envMap,
            false,
        )

        var currentPercentage = 0.0
        for (result in results) {
            val output = StringBuilder()

            if (result.hasException()) {
                output.append("\u001B[31m") // Set text color to red
                output.append(result.getException().message + " Fix errors to execute")
                output.append("\u001b[32m") // Set text color to green
                output.append(" -> ")
                output.append(runner.getPercentage().coerceAtMost(100.0).let { "%.2f".format(it) })
                output.append("\u001B[0m") // Reset text color to default
                println(output.toString())
                break
            }
            if (result.hasPrintln()) {
                if (result.getPrintln().isNotEmpty()) {
                    output.append(result.getPrintln().trim())
                }
            }
            val newPercentage = round(runner.getPercentage() * 100) / 100.0
            if (newPercentage - currentPercentage >= 5) {
                currentPercentage = newPercentage
                val percentageText = "percentage: %.2f%%".format(currentPercentage)
                val arrow = " -> "
                if (!result.hasPrintln()) {
                    output.append("\u001b[32m")
                    output.append(percentageText)
                    output.append("\u001b[0m") // Reset text color to default
                } else {
                    output.append("\u001b[32m") // Set text color to green
                    output.append(arrow)
                    output.append(percentageText)
                    output.append("\u001b[0m") // Reset text color to default
                }
            }
            if (output.isNotEmpty()) {
                println(output.toString())
            }
        }
    }

    private fun fileToMap(file: File): Map<String, String> {
        return file.readLines().associate {
            val (key, value) = it.split("=")
            key to value
        }
    }
}

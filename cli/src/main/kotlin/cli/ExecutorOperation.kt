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
        )

        var currentPercentage = 0.0
        for (result in results) {
            val output = StringBuilder()

            if (result.hasException()) {
                output.append(result.getException().message)
            }
            val newPercentage = round(runner.getPercentage() * 100) / 100.0
            if (newPercentage - currentPercentage >= 5) {
                currentPercentage = newPercentage
                val percentageText = "percentage: %.2f%%".format(currentPercentage)
                val arrow = " -> "
                if (output.isEmpty()) {
                    print("\u001b[32m")
                    print(percentageText)
                    print("\u001b[0m") // Reset text color to default
                } else {
                    val padding = " ".repeat(80 - output.length - percentageText.length - arrow.length)
                    output.append(padding)
                    output.append("\u001b[32m") // Set text color to green
                    output.append(arrow)
                    output.append(percentageText)
                    output.append("\u001b[0m") // Reset text color to default
                }
            }
            println(output.toString())
        }
        clearBelow()
    }

    private fun clearBelow() {
        print("\u001b[J")
        System.out.flush()
    }

    private fun fileToMap(file: File): Map<String, String> {
        return file.readLines().associate {
            val (key, value) = it.split("=")
            key to value
        }
    }
}

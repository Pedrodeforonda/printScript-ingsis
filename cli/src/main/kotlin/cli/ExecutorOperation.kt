package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import org.example.main.Runner
import java.io.File

class ExecutorOperation : CliktCommand(
    name = "execute",
    help = "Execute the source file.",
) {
    private val sourceFile: File by argument(help = "Source file to process.")
        .file(mustExist = true)

    override fun run() {
        Runner().run(sourceFile.absolutePath)
    }
}

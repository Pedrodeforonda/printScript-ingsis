import main.ConfigLoader
import main.FormatterConfigReader
import main.MainFormatter
import org.example.lexer.Lexer
import java.io.BufferedWriter
import java.io.File
import kotlin.test.Test

class TestFormatter {

    @Test
    fun test() {
        val inputPath: String = "src/test/resources/input.txt"
        val outputPath = "src/test/resources/output.txt"
        val outputWriter: BufferedWriter = File(outputPath).bufferedWriter()
        val formatter = MainFormatter()
        val configLoader = ConfigLoader()
        val standardConfig = configLoader.loadConfig<FormatterConfigReader>(
            File("src/test/resources/rules.json").inputStream(),
        )
        val lexer = Lexer(File(inputPath).inputStream().bufferedReader())
        val formattedText = formatter.formatCode(
            lexer.tokenizeAll(lexer),
            standardConfig,
            outputWriter,
        )
        outputWriter.close()
    }
}

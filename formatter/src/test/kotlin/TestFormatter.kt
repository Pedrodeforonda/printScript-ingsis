import lexer.LexerFactory
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
        val lexer = LexerFactory().createLexer(File(inputPath).inputStream().bufferedReader(), 1)
        val formattedText = formatter.formatCode(
            lexer.tokenizeAll(lexer),
            standardConfig,
            outputWriter,
        )
        outputWriter.close()
        val resultingFile = File(outputPath)
        val expectedFile = File("src/test/resources/expected.txt")
        assert(resultingFile.readText() == expectedFile.readText())
    }

    @Test
    fun testEnforceSpacing() {
        val inputPath: String = "src/test/resources/input.txt"
        val outputPath = "src/test/resources/output.txt"
        val outputWriter: BufferedWriter = File(outputPath).bufferedWriter()
        val formatter = MainFormatter()
        val configLoader = ConfigLoader()
        val standardConfig = configLoader.loadConfig<FormatterConfigReader>(
            File("src/test/resources/rules2.json").inputStream(),
        )
        val lexer = LexerFactory().createLexer(File(inputPath).inputStream().bufferedReader(), 1)
        val formattedText = formatter.formatCode(
            lexer.tokenizeAll(lexer),
            standardConfig,
            outputWriter,
        )
        outputWriter.close()
        val resultingFile = File(outputPath)
        val expectedFile = File("src/test/resources/expected2.txt")
        assert(resultingFile.readText() == expectedFile.readText())
    }
}

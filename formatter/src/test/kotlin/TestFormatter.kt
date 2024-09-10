import lexer.Lexer
import main.ConfigLoader
import main.FormatterConfigReader
import main.MainFormatter
import org.example.lexer.ClassicTokenStrategies
import org.junit.jupiter.api.BeforeEach
import utils.PercentageCollector
import java.io.BufferedWriter
import java.io.File
import kotlin.test.Test

class TestFormatter {

    val percentageCollector = PercentageCollector()

    @BeforeEach
    fun resetPercentageCollector() {
        percentageCollector.reset()
    }

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
        val lexer =
            Lexer(
                File(inputPath).inputStream().bufferedReader(),
                File(inputPath).inputStream().available(),
                percentageCollector,
                ClassicTokenStrategies(),
            )
        val formattedText = formatter.formatCode(
            lexer.tokenize(),
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
        val lexer =
            Lexer(
                File(inputPath).inputStream().bufferedReader(),
                File(inputPath).inputStream().available(),
                percentageCollector,
                ClassicTokenStrategies(),
            )
        val formattedText = formatter.formatCode(
            lexer.tokenize(),
            standardConfig,
            outputWriter,
        )
        outputWriter.close()
        val resultingFile = File(outputPath)
        val expectedFile = File("src/test/resources/expected2.txt")
        assert(resultingFile.readText() == expectedFile.readText())
    }
}

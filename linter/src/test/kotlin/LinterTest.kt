import main.ConfigParser
import main.Linter
import main.LinterConfig
import main.Parser
import main.ParserFactory
import main.Token
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import runners.LexerFactory
import utils.PercentageCollector
import java.nio.file.Files
import kotlin.io.path.Path

class LinterTest {

    private val collector = PercentageCollector()

    @BeforeEach
    fun resetPercentageCollector() {
        collector.reset()
    }

    private fun createParser1(tokens: Sequence<Token>): Parser {
        return ParserFactory().createParser("1.0", tokens.iterator())
    }

    private fun createParser2(tokens: Sequence<Token>): Parser {
        return ParserFactory().createParser("1.1", tokens.iterator())
    }

    @Test
    fun testConfigParser() {
        val input = Files.newInputStream(Path("src/test/resources/linterRulesTest.json"))
        val config = ConfigParser.parseConfigToMap(input)

        assertTrue(config.identifier_format == "" && !config.restrictPrintln && !config.restrictReadInput)

        val input2 = Files.newInputStream(Path("src/test/resources/linterRulesTest2.json"))
        val config2 = ConfigParser.parseConfigToMap(input2)

        assertTrue(config2.identifier_format == "camel case" && !config2.restrictPrintln && !config2.restrictReadInput)

        val input3 = Files.newInputStream(Path("src/test/resources/linterRulesTest3.json"))
        val config3 = ConfigParser.parseConfigToMap(input3)

        assertTrue(config3.identifier_format == "snake case" && config3.restrictPrintln && config3.restrictReadInput)

        val input4 = Files.newInputStream(Path("src/test/resources/linterRulesTest4.json"))
        val config4 = ConfigParser.parseConfigToMap(input4)

        assertTrue(config4.identifier_format == "" && config4.restrictPrintln && !config4.restrictReadInput)
    }

    @Test
    fun testCamelCaseRule() {
        val config = LinterConfig("camel case", false, false)

        val input = Files.newInputStream(Path("src/test/resources/linterCamelCaseTest.txt"))
        val tokens = LexerFactory().lex(input, "1.0", collector)
        val parser = createParser1(tokens)
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        assertTrue(errors.isEmpty())

        val input2 = Files.newInputStream(Path("src/test/resources/linterCamelCaseTest2.txt"))
        val tokens2 = LexerFactory().lex(input2, "1.0", collector)
        val parser2 = createParser1(tokens2)
        val astNodes2 = parser2.parseExpressions()
        val errors2 = Linter().lint(astNodes2, config).toList()

        assertTrue(errors2.size == 2)
    }

    @Test
    fun testSnakeCaseRule() {
        val config = LinterConfig("snake case", false, false)

        val input = Files.newInputStream(Path("src/test/resources/linterSnakeCaseTest.txt"))
        val tokens = LexerFactory().lex(input, "1.0", collector)
        val parser = createParser1(tokens)
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        assertTrue(errors.isEmpty())

        val input2 = Files.newInputStream(Path("src/test/resources/linterSnakeCaseTest2.txt"))
        val tokens2 = LexerFactory().lex(input2, "1.0", collector)
        val parser2 = createParser1(tokens2)
        val astNodes2 = parser2.parseExpressions()
        val errors2 = Linter().lint(astNodes2, config).toList()

        assertTrue(errors2.size == 3)
    }

    @Test
    fun testPrintlnRestrictionRule() {
        val config = LinterConfig("", true, false)

        val input = Files.newInputStream(Path("src/test/resources/linterPrintlnRestrictionTest.txt"))
        val tokens = LexerFactory().lex(input, "1.0", collector)
        val parser = createParser1(tokens)
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        assertTrue(errors.isEmpty())

        val input2 = Files.newInputStream(Path("src/test/resources/linterPrintlnRestrictionTest2.txt"))
        val tokens2 = LexerFactory().lex(input2, "1.0", collector)
        val parser2 = createParser1(tokens2)
        val astNodes2 = parser2.parseExpressions()
        val errors2 = Linter().lint(astNodes2, config).toList()

        assertTrue(errors2.size == 3)
    }

    @Test
    fun testParsingErrorsCase() {
        val config = LinterConfig("camel case", false, false)
        val input = Files.newInputStream(Path("src/test/resources/linterParsingErrorsTest.txt"))
        val tokens = LexerFactory().lex(input, "1.0", collector)
        val parser = createParser1(tokens)
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        for (error in errors) {
            println(error.getMessage())
        }

        assertTrue(errors.size == 5)
    }

    @Test
    fun testReadInputRestrictionRule() {
        val config = LinterConfig("", false, true)

        val input = Files.newInputStream(Path("src/test/resources/linterReadInputRestrictionTest.txt"))
        val tokens = LexerFactory().lex(input, "1.1", collector)
        val parser = createParser2(tokens)
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        assertTrue(errors.isEmpty())

        val input2 = Files.newInputStream(Path("src/test/resources/linterReadInputRestrictionTest2.txt"))
        val tokens2 = LexerFactory().lex(input2, "1.1", collector)
        val parser2 = createParser2(tokens2)
        val astNodes2 = parser2.parseExpressions()
        val errors2 = Linter().lint(astNodes2, config).toList()

        errors2.forEach { println(it.getMessage()) }
        println(errors2.size)
        assertTrue(errors2.size == 4)
    }
}

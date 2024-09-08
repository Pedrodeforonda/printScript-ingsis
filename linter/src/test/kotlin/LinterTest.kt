import main.ConfigParser
import main.LinterConfig
import main.Parser
import main.Token
import main.kotlin.main.Linter
import org.example.lexer.Lexer
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.PercentageCollector
import java.nio.file.Files
import kotlin.io.path.Path

class LinterTest {

    val collector = PercentageCollector()

    @BeforeEach
    fun resetPercentageCollector() {
        collector.reset()
    }

    @Test
    fun testConfigParser() {
        val input = Files.newInputStream(Path("src/test/resources/linterRulesTest.json"))
        val config = ConfigParser.parseConfig(input)

        assertTrue(config.identifier_format == "" && !config.restrictPrintln && !config.restrictReadInput)

        val input2 = Files.newInputStream(Path("src/test/resources/linterRulesTest2.json"))
        val config2 = ConfigParser.parseConfig(input2)

        assertTrue(config2.identifier_format == "camel case" && !config2.restrictPrintln && !config2.restrictReadInput)

        val input3 = Files.newInputStream(Path("src/test/resources/linterRulesTest3.json"))
        val config3 = ConfigParser.parseConfig(input3)

        assertTrue(config3.identifier_format == "snake case" && config3.restrictPrintln && config3.restrictReadInput)

        val input4 = Files.newInputStream(Path("src/test/resources/linterRulesTest4.json"))
        val config4 = ConfigParser.parseConfig(input4)

        assertTrue(config4.identifier_format == "" && config4.restrictPrintln && !config4.restrictReadInput)
    }

    @Test
    fun testCamelCaseRule() {
        val config = LinterConfig("camel case", false, false)

        val input = Files.newBufferedReader(Path("src/test/resources/linterCamelCaseTest.txt"))
        val size = Files.newInputStream(Path("src/test/resources/linterCamelCaseTest.txt")).available()
        val lexer = Lexer(input, size, collector)
        val tokens: Sequence<Token> = lexer.tokenize()
        val parser = Parser(tokens.iterator())
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        assertTrue(errors.isEmpty())

        val input2 = Files.newBufferedReader(Path("src/test/resources/linterCamelCaseTest2.txt"))
        val length = Files.newInputStream(Path("src/test/resources/linterCamelCaseTest2.txt")).available()
        val lexer2 = Lexer(input2, length, collector)
        val tokens2: Sequence<Token> = lexer2.tokenize()
        val parser2 = Parser(tokens2.iterator())
        val astNodes2 = parser2.parseExpressions()
        val errors2 = Linter().lint(astNodes2, config).toList()

        assertTrue(errors2.size == 2)
    }

    @Test
    fun testSnakeCaseRule() {
        val config = LinterConfig("snake case", false, false)

        val input = Files.newBufferedReader(Path("src/test/resources/linterSnakeCaseTest.txt"))
        val size = Files.newInputStream(Path("src/test/resources/linterSnakeCaseTest.txt")).available()
        val lexer = Lexer(input, size, collector)
        val tokens: Sequence<Token> = lexer.tokenize()
        val parser = Parser(tokens.iterator())
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        assertTrue(errors.isEmpty())

        val input2 = Files.newBufferedReader(Path("src/test/resources/linterSnakeCaseTest2.txt"))
        val length = Files.newInputStream(Path("src/test/resources/linterSnakeCaseTest2.txt")).available()
        val lexer2 = Lexer(input2, length, collector)
        val tokens2: Sequence<Token> = lexer2.tokenize()
        val parser2 = Parser(tokens2.iterator())
        val astNodes2 = parser2.parseExpressions()
        val errors2 = Linter().lint(astNodes2, config).toList()

        assertTrue(errors2.size == 3)
    }

    @Test
    fun testPrintlnRestrictionRule() {
        val config = LinterConfig("", true, false)

        val input = Files.newBufferedReader(Path("src/test/resources/linterPrintlnRestrictionTest.txt"))
        val length = Files.newInputStream(Path("src/test/resources/linterPrintlnRestrictionTest.txt")).available()
        val lexer = Lexer(input, length, collector)
        val tokens: Sequence<Token> = lexer.tokenize()
        val parser = Parser(tokens.iterator())
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        assertTrue(errors.isEmpty())

        val input2 = Files.newBufferedReader(Path("src/test/resources/linterPrintlnRestrictionTest2.txt"))
        val size = Files.newInputStream(Path("src/test/resources/linterPrintlnRestrictionTest2.txt")).available()
        val lexer2 = Lexer(input2, size, collector)
        val tokens2: Sequence<Token> = lexer2.tokenize()
        val parser2 = Parser(tokens2.iterator())
        val astNodes2 = parser2.parseExpressions()
        val errors2 = Linter().lint(astNodes2, config).toList()

        assertTrue(errors2.size == 3)
    }

    @Test
    fun testParsingErrorsCase() {
        val config = LinterConfig("camel case", false, false)

        val input = Files.newBufferedReader(Path("src/test/resources/linterParsingErrorsTest.txt"))
        val size = Files.newInputStream(Path("src/test/resources/linterParsingErrorsTest.txt")).available()
        val lexer = Lexer(input, size, collector)
        val tokens: Sequence<Token> = lexer.tokenize()
        val parser = Parser(tokens.iterator())
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        for (error in errors) {
            println(error.getMessage())
        }
        assertTrue(errors.size == 4)
    }

    /*@Test
    fun testReadInputRestrictionRule() {
        val config = LinterConfig("", false, true)

        val input = Files.newBufferedReader(Path("src/test/resources/linterReadInputRestrictionTest.txt"))
        val lexer = Lexer(input)
        val tokens: Sequence<Token> = lexer.tokenizeAll(lexer)
        val parser = Parser(tokens.iterator())
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        assertTrue(errors.isEmpty())

        val input2 = Files.newBufferedReader(Path("src/test/resources/linterReadInputRestrictionTest2.txt"))
        val lexer2 = Lexer(input2)
        val tokens2: Sequence<Token> = lexer2.tokenizeAll(lexer2)
        val parser2 = Parser(tokens2.iterator())
        val astNodes2 = parser2.parseExpressions()
        val errors2 = Linter().lint(astNodes2, config).toList()

        errors2.forEach { println(it.getMessage()) }
        assertTrue(errors2.size == 7)
    }*/
}

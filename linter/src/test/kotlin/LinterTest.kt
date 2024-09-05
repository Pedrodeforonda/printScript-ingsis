import main.Parser
import main.Token
import main.kotlin.main.Linter
import main.kotlin.main.LinterConfig
import org.example.lexer.Lexer
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.file.Files
import kotlin.io.path.Path

class LinterTest {

    @Test
    fun testCamelCaseRule() {
        val config = LinterConfig("camelCase", false)

        val input = Files.newBufferedReader(Path("src/test/resources/linterCamelCaseTest.txt"))
        val lexer = Lexer(input)
        val tokens: Sequence<Token> = lexer.tokenizeAll(lexer)
        val parser = Parser(tokens.iterator())
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        assertTrue(errors.isEmpty())

        val input2 = Files.newBufferedReader(Path("src/test/resources/linterCamelCaseTest2.txt"))
        val lexer2 = Lexer(input2)
        val tokens2: Sequence<Token> = lexer2.tokenizeAll(lexer2)
        val parser2 = Parser(tokens2.iterator())
        val astNodes2 = parser2.parseExpressions()
        val errors2 = Linter().lint(astNodes2, config).toList()

        assertTrue(errors2.size == 2)
    }

    @Test
    fun testSnakeCaseRule() {
        val config = LinterConfig("snake_case", false)

        val input = Files.newBufferedReader(Path("src/test/resources/linterSnakeCaseTest.txt"))
        val lexer = Lexer(input)
        val tokens: Sequence<Token> = lexer.tokenizeAll(lexer)
        val parser = Parser(tokens.iterator())
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        assertTrue(errors.isEmpty())

        val input2 = Files.newBufferedReader(Path("src/test/resources/linterSnakeCaseTest2.txt"))
        val lexer2 = Lexer(input2)
        val tokens2: Sequence<Token> = lexer2.tokenizeAll(lexer2)
        val parser2 = Parser(tokens2.iterator())
        val astNodes2 = parser2.parseExpressions()
        val errors2 = Linter().lint(astNodes2, config).toList()

        assertTrue(errors2.size == 3)
    }

    @Test
    fun testPrintlnRestrictionRule() {
        val config = LinterConfig("", true)

        val input = Files.newBufferedReader(Path("src/test/resources/linterPrintlnRestrictionTest.txt"))
        val lexer = Lexer(input)
        val tokens: Sequence<Token> = lexer.tokenizeAll(lexer)
        val parser = Parser(tokens.iterator())
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        assertTrue(errors.isEmpty())

        val input2 = Files.newBufferedReader(Path("src/test/resources/linterPrintlnRestrictionTest2.txt"))
        val lexer2 = Lexer(input2)
        val tokens2: Sequence<Token> = lexer2.tokenizeAll(lexer2)
        val parser2 = Parser(tokens2.iterator())
        val astNodes2 = parser2.parseExpressions()
        val errors2 = Linter().lint(astNodes2, config).toList()

        assertTrue(errors2.size == 3)
    }

    @Test
    fun testParsingErrorsCase() {
        val config = LinterConfig("camelCase", false)

        val input = Files.newBufferedReader(Path("src/test/resources/linterParsingErrorsTest.txt"))
        val lexer = Lexer(input)
        val tokens: Sequence<Token> = lexer.tokenizeAll(lexer)
        val parser = Parser(tokens.iterator())
        val astNodes = parser.parseExpressions()
        val errors = Linter().lint(astNodes, config).toList()

        for (error in errors) {
            println(error.getMessage())
        }
        assertTrue(errors.size == 4)
    }
}

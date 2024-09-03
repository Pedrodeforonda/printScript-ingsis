import main.kotlin.Linter
import main.kotlin.LinterConfig
import org.example.Lexer
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class LinterTest {

    @Test
    fun testCamelCaseRule() {
        val config = LinterConfig("camelCase", false)

        val input = File("src/test/resources/LinterCamelCaseTest.txt")
        val lexer = Lexer(input.inputStream().bufferedReader())
        val tokens = lexer.tokenizeAll(lexer)
        val errors = Linter().lint(tokens, config)

        assertTrue(errors.isEmpty())

        val input2 = File("src/test/resources/LinterCamelCaseTest2.txt")
        val lexer2 = Lexer(input2.inputStream().bufferedReader())
        val tokens2 = lexer2.tokenizeAll(lexer2)
        val errors2 = Linter().lint(tokens2, config)

        assertTrue(errors2.size == 2)
    }

    @Test
    fun testSnakeCaseRule() {
        val config = LinterConfig("snake_case", false)

        val input = File("src/test/resources/LinterSnakeCaseTest.txt")
        val lexer = Lexer(input.inputStream().bufferedReader())
        val tokens = lexer.tokenizeAll(lexer)
        val errors = Linter().lint(tokens, config)

        assertTrue(errors.isEmpty())

        val input2 = File("src/test/resources/LinterSnakeCaseTest2.txt")
        val lexer2 = Lexer(input2.inputStream().bufferedReader())
        val tokens2 = lexer2.tokenizeAll(lexer2)
        val errors2 = Linter().lint(tokens2, config)

        assertTrue(errors2.size == 3)
    }

    @Test
    fun testPrintlnRestrictionRule() {
        val config = LinterConfig("", true)

        val input = File("src/test/resources/LinterPrintlnRestrictionTest.txt")
        val lexer = Lexer(input.inputStream().bufferedReader())
        val tokens = lexer.tokenizeAll(lexer)
        val errors = Linter().lint(tokens, config)

        assertTrue(errors.isEmpty())

        val input2 = File("src/test/resources/LinterPrintlnRestrictionTest2.txt")
        val lexer2 = Lexer(input2.inputStream().bufferedReader())
        val tokens2 = lexer2.tokenizeAll(lexer2)
        val errors2 = Linter().lint(tokens2, config)

        assertTrue(errors2.size == 3)
    }
}

import main.kotlin.Linter
import main.kotlin.LinterConfig
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class LinterTest {

    @Test
    fun testCamelCaseRule() {
        val config = LinterConfig("camelCase", false)
        val input = File("src/test/resources/LinterCamelCaseTest.txt").readText()
        val errors = Linter().lint(input, config)
        assertTrue(errors.isEmpty())
        val input2 = File("src/test/resources/LinterCamelCaseTest2.txt").readText()
        val errors2 = Linter().lint(input2, config)
        assertTrue(errors2.size == 2)
    }

    @Test
    fun testSnakeCaseRule() {
        val config = LinterConfig("snake_case", false)
        val input = File("src/test/resources/LinterSnakeCaseTest.txt").readText()
        val errors = Linter().lint(input, config)
        assertTrue(errors.isEmpty())
        val input2 = File("src/test/resources/LinterSnakeCaseTest2.txt").readText()
        val errors2 = Linter().lint(input2, config)
        assertTrue(errors2.size == 3)
    }

    @Test
    fun testPrintlnRestrictionRule() {
        val config = LinterConfig("", true)
        val input = File("src/test/resources/LinterPrintlnRestrictionTest.txt").readText()
        val errors = Linter().lint(input, config)
        assertTrue(errors.isEmpty())
        val input2 = File("src/test/resources/LinterPrintlnRestrictionTest2.txt").readText()
        val errors2 = Linter().lint(input2, config)
        assertTrue(errors2.size == 3)
    }
}

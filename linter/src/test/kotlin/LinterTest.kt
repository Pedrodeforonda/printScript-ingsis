import main.kotlin.Linter
import main.kotlin.LinterConfig
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class LinterTest {

    @Test
    fun testCheckIdentifier() {
        val config = LinterConfig("camelCase", true)
        val linter = Linter(config)
        assertTrue(linter.checkIdentifier("validIdentifier"))
        assertFalse(linter.checkIdentifier("Invalid_identifier"))
    }

    @Test
    fun testCheckPrintlnUsage() {
        val config = LinterConfig("camelCase", true)
        val linter = Linter(config)
        assertTrue(linter.checkPrintlnUsage("""println(Hello)"""))
        assertFalse(linter.checkPrintlnUsage("""println(123abc)"""))
    }

    @Test
    fun testLintCamelCaseFile() {
        val config = LinterConfig("camelCase", true)
        val linter = Linter(config)
        val file = File("src/test/resources/LinterCamelCaseTest")
        val errors = linter.lintFile(file)
        assertTrue(errors.isEmpty())
    }

    @Test
    fun testLintSnakeCaseFile() {
        val config = LinterConfig("snake_case", true)
        val linter = Linter(config)
        val file = File("src/test/resources/LinterSnakeCaseTest")
        val errors = linter.lintFile(file)
        assertTrue(errors.isEmpty())
    }
}

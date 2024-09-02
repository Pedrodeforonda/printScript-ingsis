import main.kotlin.Linter
import main.kotlin.LinterConfig
import main.kotlin.strategies.CamelStrategy
import main.kotlin.strategies.SnakeStrategy
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class LinterTest {

    private val strategyMap = mapOf("camelCase" to CamelStrategy(), "snake_case" to SnakeStrategy())

    private val strategyMap = mapOf("camelCase" to CamelStrategy(), "snake_case" to SnakeStrategy())

    private val strategyMap = mapOf("camelCase" to CamelStrategy(), "snake_case" to SnakeStrategy())

    @Test
    fun testCheckIdentifier() {
        val config = LinterConfig("camelCase", true)
        val linter = Linter(config, strategyMap)
        assertTrue(linter.checkIdentifierStrategies("validIdentifier"))
        assertFalse(linter.checkIdentifierStrategies("Invalid_identifier"))
    }

    @Test
    fun testCheckPrintlnUsage() {
        val config = LinterConfig("camelCase", true)
        val linter = Linter(config, strategyMap)
        assertTrue(linter.checkPrintlnUsage("""println(Hello)"""))
        assertFalse(linter.checkPrintlnUsage("""println(123abc)"""))
    }

    @Test
    fun testLintCamelCaseFile() {
        val config = LinterConfig("camelCase", true)
        val linter = Linter(config, strategyMap)
        val file = File("src/test/resources/LinterCamelCaseTest")
        val errors = linter.lintFile(file)
        assertTrue(errors.isEmpty())
    }

    @Test
    fun testLintSnakeCaseFile() {
        val config = LinterConfig("snake_case", true)
        val linter = Linter(config, strategyMap)
        val file = File("src/test/resources/LinterSnakeCaseTest")
        val errors = linter.lintFile(file)
        assertTrue(errors.isEmpty())
    }
}

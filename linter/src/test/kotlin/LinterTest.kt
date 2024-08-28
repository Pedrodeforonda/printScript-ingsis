import main.kotlin.Linter
import main.kotlin.LinterConfig
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class LinterTest {

    private val config = LinterConfig("camelCase", true)
    private val linter = Linter(config)

    @Test
    fun testCheckIdentifier() {
        assertTrue(linter.checkIdentifier("validIdentifier"))
        assertFalse(linter.checkIdentifier("Invalid_identifier"))
    }

    @Test
    fun testCheckPrintlnUsage() {
        assertTrue(linter.checkPrintlnUsage("""println("Hello")"""))
        assertFalse(linter.checkPrintlnUsage("""println(123abc)"""))
    }

    @Test
    fun testLintFile() {
        val file = File("path/to/testFile.kt")
        val errors = linter.lintFile(file)
        assertTrue(errors.isNotEmpty())
    }
}

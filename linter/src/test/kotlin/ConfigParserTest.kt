import main.kotlin.ConfigParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConfigParserTest {

    @Test
    fun testParseCamelCaseConfig() {
        val config = ConfigParser.parseConfig("src/test/resources/LinterCamelCaseTest")
        assertEquals("camelCase", config.identifierFormat)
        assertEquals(true, config.restrictPrintln)
    }

    @Test
    fun testParseSnakeCaseConfig() {
        val config = ConfigParser.parseConfig("src/test/resources/LinterSnakeCaseTest")
        assertEquals("snake_case", config.identifierFormat)
        assertEquals(true, config.restrictPrintln)
    }
}
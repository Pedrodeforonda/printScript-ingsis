import main.kotlin.ConfigParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConfigParserTest {

    @Test
    fun testParseConfig() {
        val config = ConfigParser.parseConfig("path/to/config.json")
        assertEquals("camelCase", config.identifierFormat)
        assertEquals(true, config.restrictPrintln)
    }
}

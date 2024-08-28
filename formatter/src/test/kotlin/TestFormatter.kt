import java.io.File
import java.io.InputStream
import kotlin.test.Test

class TestFormatter {

    @Test
    fun test() {
        val inputStream: InputStream? = this::class.java.classLoader.getResourceAsStream("input.txt")
        val outputPath = "formatter/src/test/resources/output.txt"
        val formatter = Formatter()

        if (inputStream == null) {
            throw IllegalStateException("Resource 'input.txt' not found.")
        }

        val inputText = inputStream.bufferedReader().use { it.readText() }

        val config = loadConfig("src/test/resources/rules.json")

        val formattedText = formatter.formatCode(inputText, config)

        File(outputPath).writeText(formattedText)
    }
}

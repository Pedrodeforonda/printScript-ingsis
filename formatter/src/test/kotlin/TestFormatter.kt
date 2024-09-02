import java.io.File
import java.io.InputStream
import kotlin.test.Test

class TestFormatter {

    @Test
    fun test() {
        val inputStream: InputStream? = this::class.java.classLoader.getResourceAsStream("input.txt")
        val outputPath = "src/test/resources/output.txt"
        val formatter = MainFormatter()

        if (inputStream == null) {
            throw IllegalStateException("Resource 'input.txt' not found.")
        }

        val inputText = inputStream.bufferedReader().use { it.readText() }

        val standardConfig = loadConfig<FormatterConfigReader>("src/test/resources/rules.json")

        val formattedText = formatter.formatCode(inputText, standardConfig)

        File(outputPath).writeText(formattedText)
    }
}

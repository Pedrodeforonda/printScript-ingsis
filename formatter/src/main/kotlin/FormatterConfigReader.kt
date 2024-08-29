import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

data class FormatterConfigReader(
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceBeforeAssignment: Boolean,
    val spaceAfterAssignment: Boolean,
)

fun loadConfig(configPath: String): FormatterConfigReader {
    val mapper = jacksonObjectMapper()
    val file = File(configPath)
    return mapper.readValue(file)
}

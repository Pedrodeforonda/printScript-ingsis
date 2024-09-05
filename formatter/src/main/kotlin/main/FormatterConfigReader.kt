package main

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream

open class FormatterConfigReader(
    val spaceBeforeColon: Boolean?,
    val spaceAfterColon: Boolean?,
    val spaceBeforeAssignment: Boolean?,
    val spaceAfterAssignment: Boolean?,
    val linesBeforePrintln: Int?,
    val newLineAfterSemiColon: Boolean?,
    val enforceSpacingBetweenTokens: Boolean?,
)

class ConfigLoader {
    inline fun <reified T : FormatterConfigReader> loadConfig(config: InputStream): T {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(config)
    }
}

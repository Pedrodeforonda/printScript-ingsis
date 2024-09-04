package main

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

open class FormatterConfigReader(
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceBeforeAssignment: Boolean,
    val spaceAfterAssignment: Boolean,
    val linesBeforePrintln: Int,
)

inline fun <reified T : FormatterConfigReader> loadConfig(configPath: String): T {
    val mapper = jacksonObjectMapper()
    val file = File(configPath)
    return mapper.readValue(file)
}

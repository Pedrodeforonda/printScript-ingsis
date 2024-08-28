package main.kotlin

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File


class ConfigParser {
    fun parseConfig(filePath: String): LinterConfig {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(File(filePath))
    }
}

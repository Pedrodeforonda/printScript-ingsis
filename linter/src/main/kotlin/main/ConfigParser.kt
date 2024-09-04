package main.kotlin.main

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class ConfigParser {
    companion object {
        fun parseConfig(filePath: String): LinterConfig {
            val mapper = jacksonObjectMapper()
            return mapper.readValue(File(filePath))
        }
    }
}

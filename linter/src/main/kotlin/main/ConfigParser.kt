package main.kotlin.main

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream

class ConfigParser {
    companion object {
        fun parseConfig(input: InputStream): LinterConfig {
            val mapper = jacksonObjectMapper()
            return mapper.readValue(input)
        }
    }
}

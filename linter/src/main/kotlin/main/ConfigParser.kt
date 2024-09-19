package main

import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader

class ConfigParser {
    companion object {
        fun parseConfigToMap(input: InputStream): LinterConfig {
            val gson = Gson()
            val reader = InputStreamReader(input)
            return gson.fromJson(reader, LinterConfig::class.java)
        }
    }
}

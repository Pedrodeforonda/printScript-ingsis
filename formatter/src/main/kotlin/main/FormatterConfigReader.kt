package main

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream

open class FormatterConfigReader(
    @JsonProperty("enforce-spacing-before-colon-in-declaration")val spaceBeforeColon: Boolean = false,
    @JsonProperty("enforce-spacing-after-colon-in-declaration")val spaceAfterColon: Boolean = false,
    @JsonProperty("enforce-spacing-around-equals") val spaceAroundEquals: Boolean = false,
    @JsonProperty("enforce-no-spacing-around-equals") val noSpaceAroundEquals: Boolean = false,
    @JsonProperty("line-breaks-after-println") val linesBeforePrintln: Int = 0,
    @JsonProperty("mandatory-line-break-after-statement") val newLineAfterSemiColon: Boolean = false,
    @JsonProperty("mandatory-single-space-separation") val enforceSpacingBetweenTokens: Boolean = false,
    @JsonProperty("mandatory-space-surrounding-operations")val enforceSpaceSurroundingOperators: Boolean = false,
)

class ConfigLoader {
    inline fun <reified T : FormatterConfigReader> loadConfig(config: InputStream): T {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(config)
    }
}

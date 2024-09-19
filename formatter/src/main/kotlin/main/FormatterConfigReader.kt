package main

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.InputStream
import java.io.InputStreamReader

open class FormatterConfigReader(
    @SerializedName("enforce-spacing-before-colon-in-declaration")
    val spaceBeforeColon: Boolean = false,
    @SerializedName("enforce-spacing-after-colon-in-declaration")
    val spaceAfterColon: Boolean = false,
    @SerializedName("enforce-spacing-around-equals")
    val spaceAroundEquals: Boolean = false,
    @SerializedName("enforce-no-spacing-around-equals")
    val noSpaceAroundEquals: Boolean = false,
    @SerializedName("line-breaks-after-println")
    val linesBeforePrintln: Int = 0,
    @SerializedName("mandatory-line-break-after-statement")
    val newLineAfterSemiColon: Boolean = false,
    @SerializedName("mandatory-single-space-separation")
    val enforceSpacingBetweenTokens: Boolean = false,
    @SerializedName("mandatory-space-surrounding-operations")
    val enforceSpaceSurroundingOperators: Boolean = false,
    @SerializedName("if-brace-below-line")
    val ifBraceBelowLine: Boolean = false,
    @SerializedName("if-brace-same-line")
    val ifBraceSameLine: Boolean = !ifBraceBelowLine,
    @SerializedName("indent-inside-braces")
    val indentInsideBraces: Int = 2,
)

class ConfigLoader {
    inline fun <reified T : FormatterConfigReader> loadConfig(config: InputStream): T {
        val gson = Gson()
        val reader = InputStreamReader(config)
        return gson.fromJson(reader, T::class.java)
    }
}

package formatters

import Formatter
import FormatterConfigReader

class NewlineBeforePrintlnFormatter : Formatter {
    override fun formatCode(input: String, config: FormatterConfigReader): String {
        val newlineString = "\n".repeat(config.linesBeforePrintln)
        val replacement = "${newlineString}println"
        return input.replace(Regex("(?<!\\n)\\n*println"), replacement)
    }
}

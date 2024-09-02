package formatters

import Formatter
import FormatterConfigReader

class NewlineAfterSemicolonFormatter : Formatter {
    override fun formatCode(input: String, config: FormatterConfigReader): String {
        return input.replace(";", ";\n")
    }
}

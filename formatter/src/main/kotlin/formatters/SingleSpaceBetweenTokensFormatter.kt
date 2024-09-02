package formatters

import Formatter
import FormatterConfigReader

class SingleSpaceBetweenTokensFormatter : Formatter {
    override fun formatCode(input: String, config: FormatterConfigReader): String {
        return input.replace(Regex("\\s+"), " ")
    }
}

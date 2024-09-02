package formatters

import Formatter
import FormatterConfigReader

class SpaceAfterColonFormatter : Formatter {
    override fun formatCode(input: String, config: FormatterConfigReader): String {
        return if (config.spaceAfterColon) {
            input.replace(Regex(":\\s*"), ": ")
        } else {
            input.replace(Regex(":\\s*"), ":")
        }
    }
}

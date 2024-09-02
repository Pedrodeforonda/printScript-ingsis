package formatters

import Formatter
import FormatterConfigReader

class SpaceBeforeColonFormatter : Formatter {
    override fun formatCode(input: String, config: FormatterConfigReader): String {
        return if (config.spaceBeforeColon) {
            input.replace(Regex("\\s*:"), " :")
        } else {
            input.replace(Regex("\\s*:"), ":")
        }
    }
}

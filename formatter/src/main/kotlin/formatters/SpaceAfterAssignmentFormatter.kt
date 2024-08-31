package formatters

import Formatter
import FormatterConfigReader

class SpaceAfterAssignmentFormatter : Formatter {
    override fun formatCode(input: String, config: FormatterConfigReader): String {
        return if (config.spaceAfterAssignment) {
            input.replace(Regex("=\\s*"), "= ")
        } else {
            input.replace(Regex("=\\s*"), "=")
        }
    }
}

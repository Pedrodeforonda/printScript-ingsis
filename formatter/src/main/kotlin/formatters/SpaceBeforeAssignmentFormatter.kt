package formatters

import Formatter
import FormatterConfigReader

class SpaceBeforeAssignmentFormatter : Formatter {
    override fun formatCode(input: String, config: FormatterConfigReader): String {
        return if (config.spaceBeforeAssignment) {
            input.replace(Regex("\\s*="), " =")
        } else {
            input.replace(Regex("\\s*="), "=")
        }
    }
}

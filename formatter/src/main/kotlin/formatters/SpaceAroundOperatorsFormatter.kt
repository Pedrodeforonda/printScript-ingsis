package formatters

import Formatter
import FormatterConfigReader

class SpaceAroundOperatorsFormatter : Formatter {
    override fun formatCode(input: String, config: FormatterConfigReader): String {
        return input.replace(Regex("\\s*(\\+|-|\\*|/)\\s*"), " $1 ")
    }
}

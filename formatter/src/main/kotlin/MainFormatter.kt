import formatters.NewlineAfterSemicolonFormatter
import formatters.NewlineBeforePrintlnFormatter
import formatters.SingleSpaceBetweenTokensFormatter
import formatters.SpaceAfterAssignmentFormatter
import formatters.SpaceAfterColonFormatter
import formatters.SpaceAroundOperatorsFormatter
import formatters.SpaceBeforeAssignmentFormatter
import formatters.SpaceBeforeColonFormatter

class MainFormatter : Formatter {

    private val formatters = listOf(
        SingleSpaceBetweenTokensFormatter(),
        NewlineAfterSemicolonFormatter(),
        SpaceAroundOperatorsFormatter(),
        SpaceAfterAssignmentFormatter(),
        SpaceAfterColonFormatter(),
        SpaceBeforeAssignmentFormatter(),
        SpaceBeforeColonFormatter(),
        NewlineBeforePrintlnFormatter(),
    )

    override fun formatCode(input: String, config: FormatterConfigReader): String {
        var formattedCode = input
        for (formatter in formatters) {
            formattedCode = formatter.formatCode(formattedCode, config)
        }
        return formattedCode.trim()
    }
}

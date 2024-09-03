package formatters

import Formatter
import FormatterConfigReader
import FormatterResult
import Token
import java.io.BufferedWriter

class SpaceAfterAssignmentFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: BufferedWriter,
    ): FormatterResult {
        if (tokens.getType() == TokenType.ASSIGNATION) {
            if (config.spaceAfterAssignment) {
                fileOutputWriter.write(" ")
                return FormatterResult("success", false)
            } else {
                return FormatterResult("fail", true)
            }
        }
        return FormatterResult("fail", true)
    }
}

package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.BufferedWriter

class SpaceBeforeAssignmentFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: BufferedWriter,
    ): FormatterResult {
        if (tokens.getType() == TokenType.ASSIGNATION) {
            if (config.spaceBeforeAssignment) {
                fileOutputWriter.write(" ")
            }
            fileOutputWriter.write("=")
            return FormatterResult("success", hasError = false, hasNext = true)
        }
        return FormatterResult("fail", hasError = true, hasNext = true)
    }
}

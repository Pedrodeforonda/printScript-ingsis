package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.BufferedWriter

class SpaceAfterColonFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: BufferedWriter,
    ): FormatterResult {
        if (config.spaceAfterColon == null) {
            return FormatterResult("fail", true)
        }
        if (tokens.getType() == TokenType.TYPE_ASSIGNATION) {
            if (config.spaceAfterColon) {
                fileOutputWriter.write(" ")
                return FormatterResult("success", false)
            }
            return FormatterResult("fail", true)
        }
        return FormatterResult("fail", true)
    }
}

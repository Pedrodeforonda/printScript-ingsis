package formatters

import Formatter
import FormatterConfigReader
import FormatterResult
import Token
import java.io.BufferedWriter

class SpaceAfterColonFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: BufferedWriter,
    ): FormatterResult {
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

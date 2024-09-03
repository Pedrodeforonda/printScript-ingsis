package formatters

import Formatter
import FormatterConfigReader
import FormatterResult
import Token
import java.io.BufferedWriter

class NewlineAfterSemicolonFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: BufferedWriter,
    ): FormatterResult {
        if (tokens.getType() == TokenType.SEMICOLON) {
            fileOutputWriter.write(";\n")
            return FormatterResult("success", false)
        }
        return FormatterResult("fail", true)
    }
}

package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.BufferedWriter

class NewlineAfterSemicolonFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: BufferedWriter,
    ): FormatterResult {
        if (tokens.getType() == TokenType.SEMICOLON) {
            fileOutputWriter.write(";")
            if (config.newLineAfterSemiColon == null) {
                return FormatterResult("fail", true)
            }
            if (config.newLineAfterSemiColon) {
                fileOutputWriter.newLine()
            }
            return FormatterResult("success", false)
        }
        return FormatterResult("fail", true)
    }
}

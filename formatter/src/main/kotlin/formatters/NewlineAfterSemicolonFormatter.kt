package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.Writer

class NewlineAfterSemicolonFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: Writer,
    ): FormatterResult {
        if (tokens.getType() == TokenType.SEMICOLON) {
            fileOutputWriter.write(";")
            if (config.newLineAfterSemiColon) {
                fileOutputWriter.write("\n")
            }
            return FormatterResult("success", false)
        }
        return FormatterResult("fail", true)
    }
}

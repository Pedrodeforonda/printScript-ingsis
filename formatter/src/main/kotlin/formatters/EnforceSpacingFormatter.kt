package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.Writer

class EnforceSpacingFormatter : Formatter {
    var wasNewLine = false
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: Writer,
    ): FormatterResult {
        if (config.enforceSpacingBetweenTokens) {
            if (tokens.getPosition().getLine() == 1 && tokens.getPosition().getColumn() == 1) {
                fileOutputWriter.write(tokens.getText())
                return FormatterResult("success", false)
            } else if (tokens.getType() == TokenType.SEMICOLON) {
                fileOutputWriter.write(tokens.getText())
                fileOutputWriter.write("\n")
                wasNewLine = true
                return FormatterResult("success", false)
            } else {
                if (wasNewLine) {
                    fileOutputWriter.write(tokens.getText())
                    wasNewLine = false
                    return FormatterResult("success", false)
                }
                if (tokens.getType() == TokenType.STRING_LITERAL) {
                    fileOutputWriter.write(" \"${tokens.getText()}\"")
                    return FormatterResult("success", false)
                }
                fileOutputWriter.write(" ${tokens.getText()}")
                return FormatterResult("success", false)
            }
        }
        return FormatterResult("fail", true)
    }
}

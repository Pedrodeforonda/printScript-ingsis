package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.Writer

class SpaceAroundOperatorsFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: Writer,
    ): FormatterResult {
        val isOperator = tokens.getType() == TokenType.PLUS ||
            tokens.getType() == TokenType.MINUS ||
            tokens.getType() == TokenType.ASTERISK ||
            tokens.getType() == TokenType.SLASH

        if (isOperator) {
            if (!config.enforceSpaceSurroundingOperators) {
                fileOutputWriter.write(tokens.getText())
                return FormatterResult("success", false)
            }
            fileOutputWriter.write(" ${tokens.getText()} ")
            return FormatterResult("success", false)
        }
        return FormatterResult("fail", true)
    }
}

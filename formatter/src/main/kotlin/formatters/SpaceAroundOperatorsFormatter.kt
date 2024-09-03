package formatters

import Formatter
import FormatterConfigReader
import FormatterResult
import Token
import TokenType
import java.io.BufferedWriter

class SpaceAroundOperatorsFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: BufferedWriter,
    ): FormatterResult {
        val isOperator = tokens.getType() == TokenType.PLUS ||
            tokens.getType() == TokenType.MINUS ||
            tokens.getType() == TokenType.ASTERISK ||
            tokens.getType() == TokenType.SLASH

        if (isOperator) {
            fileOutputWriter.write(" ${tokens.getText()} ")
            return FormatterResult("success", false)
        }
        return FormatterResult("fail", true)
    }
}

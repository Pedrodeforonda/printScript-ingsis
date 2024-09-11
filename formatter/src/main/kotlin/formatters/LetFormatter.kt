package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.Writer

class LetFormatter : Formatter {
    override fun formatCode(tokens: Token, config: FormatterConfigReader, fileOutputWriter: Writer): FormatterResult {
        if (tokens.getType() == TokenType.LET_KEYWORD) {
            fileOutputWriter.write("let ")
            return FormatterResult("success", false)
        }
        return FormatterResult("fail", true)
    }
}

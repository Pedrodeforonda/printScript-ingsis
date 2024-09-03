package formatters

import Formatter
import FormatterConfigReader
import FormatterResult
import Token
import java.io.BufferedWriter

class IsLiteral : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: BufferedWriter,
    ): FormatterResult {
        if (tokens.getType() == TokenType.STRING_LITERAL) {
            fileOutputWriter.write("\"${tokens.getText()}\"")
            return FormatterResult("success", false)
        }
        if (tokens.getType() == TokenType.NUMBER_LITERAL) {
            fileOutputWriter.write(tokens.getText())
            return FormatterResult("success", false)
        }
        return FormatterResult("fail", true)
    }
}

package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.Writer

class NewlineBeforePrintlnFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: Writer,
    ): FormatterResult {
        if (tokens.getType() == TokenType.CALL_FUNC) {
            val qty = config.linesBeforePrintln
            repeat(qty) {
                fileOutputWriter.write("\n")
            }
            fileOutputWriter.write(tokens.getText())
            return FormatterResult("success", false)
        }
        return FormatterResult("fail", true)
    }
}

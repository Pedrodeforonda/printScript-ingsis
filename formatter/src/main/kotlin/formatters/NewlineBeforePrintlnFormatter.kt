package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.BufferedWriter

class NewlineBeforePrintlnFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: BufferedWriter,
    ): FormatterResult {
        if (tokens.getType() == TokenType.CALL_FUNC) {
            if (config.linesBeforePrintln == null) {
                fileOutputWriter.write(tokens.getText())
                return FormatterResult("fail", true)
            }
            val qty = config.linesBeforePrintln
            repeat(qty) {
                fileOutputWriter.newLine()
            }
            fileOutputWriter.write(tokens.getText())
            return FormatterResult("success", false)
        }
        return FormatterResult("fail", true)
    }
}

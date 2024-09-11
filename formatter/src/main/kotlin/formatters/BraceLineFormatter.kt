package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.Writer

class BraceLineFormatter : Formatter {
    private var indent = 0
    override fun formatCode(tokens: Token, config: FormatterConfigReader, fileOutputWriter: Writer): FormatterResult {
        if (tokens.getType() == TokenType.LEFT_BRACE) {
            if (config.ifBraceSameLine) {
                fileOutputWriter.write(" {\n")
                return FormatterResult("success", false)
            } else if (config.ifBraceBelowLine) {
                fileOutputWriter.write("\n" + " ".repeat(indent) + "{\n")
                return FormatterResult("success", false)
            }
            return FormatterResult("fail", true)
        }
        if (tokens.getType() == TokenType.RIGHT_BRACE) {
            fileOutputWriter.write("}")
            fileOutputWriter.write("\n")
        }
        return FormatterResult("fail", true)
    }

    fun setIndent(indent: Int) {
        this.indent = indent
    }
}

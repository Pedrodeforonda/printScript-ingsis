package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.Writer

class EnforceSpacingAroundEquals : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: Writer,
    ): FormatterResult {
        if (tokens.getType() == TokenType.ASSIGNATION) {
            if (config.spaceAroundEquals) {
                fileOutputWriter.write(" = ")
            } else {
                fileOutputWriter.write("=")
            }

            return FormatterResult("success", false)
        }
        return FormatterResult("fail", true)
    }
}

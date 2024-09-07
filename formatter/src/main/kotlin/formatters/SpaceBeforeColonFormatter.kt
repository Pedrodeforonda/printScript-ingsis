package formatters

import main.Formatter
import main.FormatterConfigReader
import main.FormatterResult
import main.Token
import main.TokenType
import java.io.Writer

class SpaceBeforeColonFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: Writer,
    ): FormatterResult {
        if (tokens.getType() == TokenType.TYPE_ASSIGNATION) {
            if (config.spaceBeforeColon) {
                fileOutputWriter.write(" ")
            }
            fileOutputWriter.write(":")
            return FormatterResult("success", false, true)
        }
        return FormatterResult("fail", true, true)
    }
}

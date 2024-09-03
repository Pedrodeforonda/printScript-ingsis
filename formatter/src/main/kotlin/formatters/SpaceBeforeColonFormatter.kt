package formatters

import Formatter
import FormatterConfigReader
import FormatterResult
import Token
import java.io.BufferedWriter

class SpaceBeforeColonFormatter : Formatter {
    override fun formatCode(
        tokens: Token,
        config: FormatterConfigReader,
        fileOutputWriter: BufferedWriter,
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

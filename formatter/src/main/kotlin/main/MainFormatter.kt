package main

import formatters.EnforceSpacingFormatter
import formatters.IsLiteral
import formatters.NewlineAfterSemicolonFormatter
import formatters.NewlineBeforePrintlnFormatter
import formatters.SpaceAfterAssignmentFormatter
import formatters.SpaceAfterColonFormatter
import formatters.SpaceAroundOperatorsFormatter
import formatters.SpaceBeforeAssignmentFormatter
import formatters.SpaceBeforeColonFormatter
import java.io.BufferedWriter

class MainFormatter {

    private val formatters by lazy {
        listOf(
            EnforceSpacingFormatter(),
            IsLiteral(),
            NewlineAfterSemicolonFormatter(),
            SpaceAroundOperatorsFormatter(),
            SpaceBeforeAssignmentFormatter(),
            SpaceAfterAssignmentFormatter(),
            SpaceBeforeColonFormatter(),
            SpaceAfterColonFormatter(),
            NewlineBeforePrintlnFormatter(),
        )
    }

    private val formattingOpts by lazy {
        listOf(
            TokenType.STRING_LITERAL,
            TokenType.NUMBER_LITERAL,
            TokenType.TYPE_ASSIGNATION,
            TokenType.ASSIGNATION,
            TokenType.SEMICOLON,
            TokenType.CALL_FUNC,
            TokenType.PLUS,
            TokenType.MINUS,
            TokenType.ASTERISK,
            TokenType.SLASH,
        )
    }

    fun formatCode(
        tokens: Sequence<Token>,
        config: FormatterConfigReader,
        fileOutputWriter: BufferedWriter,
    ): FormatterResult {
        var lastTokenWasNewline = true
        var lastTokenWasOperator = false
        var isEnforceSpacing = false
        if (config.enforceSpacingBetweenTokens == true) {
            tokens.forEach {
                for (formatter in formatters) {
                    val result = formatter.formatCode(it, config, fileOutputWriter)
                    break
                }
            }
            return FormatterResult("Formatted successfully", false)
        }

        tokens.forEach { token ->
            val isIgnore = token.getType() == TokenType.LEFT_PAREN ||
                token.getType() == TokenType.RIGHT_PAREN ||
                token.getType() == TokenType.STRING_TYPE ||
                token.getType() == TokenType.NUMBER_TYPE

            if (token.getType() in formattingOpts) {
                for (formatter in formatters) {
                    val result = formatter.formatCode(token, config, fileOutputWriter)
                    if (result.hasError() || result.hasNext()) {
                        continue
                    }
                    break
                }
            } else {
                if (isIgnore) {
                    fileOutputWriter.write(token.getText())
                } else {
                    if (!lastTokenWasNewline && !lastTokenWasOperator) {
                        fileOutputWriter.write(" ")
                    }

                    fileOutputWriter.write(token.getText())
                }
            }
            lastTokenWasNewline = token.getType() == TokenType.SEMICOLON
            lastTokenWasOperator = token.getType() == TokenType.PLUS ||
                token.getType() == TokenType.MINUS ||
                token.getType() == TokenType.ASTERISK ||
                token.getType() == TokenType.SLASH
        }
        return FormatterResult("Formatted successfully", false)
    }
}

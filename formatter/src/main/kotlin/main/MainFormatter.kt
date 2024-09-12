package main

import formatters.BraceLineFormatter
import formatters.EnforceSpacingAroundEquals
import formatters.EnforceSpacingFormatter
import formatters.IsLiteral
import formatters.LetFormatter
import formatters.NewlineAfterSemicolonFormatter
import formatters.NewlineBeforePrintlnFormatter
import formatters.SpaceAfterColonFormatter
import formatters.SpaceAroundOperatorsFormatter
import formatters.SpaceBeforeColonFormatter
import java.io.Writer

class MainFormatter {

    private val formatters by lazy {
        listOf(
            EnforceSpacingFormatter(),
            LetFormatter(),
            BraceLineFormatter(),
            IsLiteral(),
            NewlineAfterSemicolonFormatter(),
            SpaceAroundOperatorsFormatter(),
            EnforceSpacingAroundEquals(),
            SpaceBeforeColonFormatter(),
            SpaceAfterColonFormatter(),
            NewlineBeforePrintlnFormatter(0),
        )
    }

    private val formattingOpts by lazy {
        listOf(
            TokenType.LET_KEYWORD,
            TokenType.LEFT_BRACE,
            TokenType.RIGHT_BRACE,
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
        fileOutputWriter: Writer,
    ): FormatterResult {
        var lastTokenWasNewline = true
        var lastTokenWasOperator = false
        var openBraceQty = 0
        if (config.enforceSpacingBetweenTokens) {
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
                token.getType() == TokenType.NUMBER_TYPE ||
                token.getType() == TokenType.IDENTIFIER
            if (token.getType() != TokenType.RIGHT_BRACE && lastTokenWasNewline) {
                fileOutputWriter.write(" ".repeat(openBraceQty * config.indentInsideBraces))
            } else if (token.getType() == TokenType.RIGHT_BRACE) {
                fileOutputWriter.write(" ".repeat((openBraceQty - 1) * config.indentInsideBraces))
            }
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
            openBraceQty += if (token.getType() == TokenType.LEFT_BRACE) 1 else 0
            openBraceQty -= if (token.getType() == TokenType.RIGHT_BRACE) 1 else 0
            lastTokenWasNewline = token.getType() == TokenType.SEMICOLON || token.getType() == TokenType.LEFT_BRACE ||
                token.getType() == TokenType.RIGHT_BRACE
            lastTokenWasOperator = token.getType() == TokenType.PLUS ||
                token.getType() == TokenType.MINUS ||
                token.getType() == TokenType.ASTERISK ||
                token.getType() == TokenType.SLASH
            for (formatter in formatters) {
                when (formatter) {
                    is NewlineBeforePrintlnFormatter -> {
                        formatter.setIndent(openBraceQty * config.indentInsideBraces)
                    }
                    is BraceLineFormatter -> {
                        formatter.setIndent(openBraceQty * config.indentInsideBraces)
                    }
                }
            }
        }
        return FormatterResult("Formatted successfully", false)
    }
}

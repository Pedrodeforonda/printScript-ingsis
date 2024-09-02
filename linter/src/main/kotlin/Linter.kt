package main.kotlin

import main.kotlin.identifierFormats.IdentifierFormats
import main.kotlin.rules.IdentifierFormatRule
import main.kotlin.rules.PrintlnRestrictionRule
import org.example.ClassicTokenStrategies
import org.example.Lexer

class Linter {

    fun lint(input: String, config: LinterConfig): List<String> {
        val lexer = Lexer(input, ClassicTokenStrategies())
        val tokens = lexer.tokenizeAll(lexer)
        val errors = mutableListOf<String>()
        for (identifierFormat in IdentifierFormats().formats) {
            if (identifierFormat.getFormatName() == config.identifierFormat) {
                errors.addAll(IdentifierFormatRule(identifierFormat).lintCode(tokens))
            }
        }
        if (config.restrictPrintln) {
            errors.addAll(PrintlnRestrictionRule().lintCode(tokens))
        }
        return errors
    }
}

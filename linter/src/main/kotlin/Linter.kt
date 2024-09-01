package main.kotlin

import main.kotlin.rules.IdentifierFormatRule
import main.kotlin.rules.PrintlnRestrictionRule
import org.example.ClassicTokenStrategies
import org.example.Lexer

class Linter {

    fun lint(input: String, config: LinterConfig): List<String> {
        val lexer = Lexer(input, ClassicTokenStrategies())
        val errors = mutableListOf<String>()
        val identifierFormat = config.identifierFormat
        if (identifierFormat == "camelCase" || identifierFormat == "snake_case") {
            errors.addAll(IdentifierFormatRule(identifierFormat).lintCode(input, lexer))
        }
        if (config.restrictPrintln) {
            errors.addAll(PrintlnRestrictionRule().lintCode(input, lexer))
        }
        return errors
    }
}

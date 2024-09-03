package main.kotlin

import main.kotlin.identifierFormats.IdentifierFormats
import main.kotlin.rules.IdentifierFormatRule
import main.kotlin.rules.PrintlnRestrictionRule
import org.example.Lexer
import java.io.BufferedReader

class Linter {

    fun lint(iterator: BufferedReader, config: LinterConfig): List<String> {
        val lexer = Lexer(iterator)
        val tokens = lexer.tokenizeAll(lexer)
        val errors = mutableListOf<String>()
        for (identifierFormat in IdentifierFormats().formats) {
            if (identifierFormat.getFormatName() == config.identifierFormat) {
                errors.addAll(IdentifierFormatRule(identifierFormat).lintCode(tokens.toList()))
            }
        }
        if (config.restrictPrintln) {
            errors.addAll(PrintlnRestrictionRule().lintCode(tokens.toList()))
        }
        return errors
    }
}

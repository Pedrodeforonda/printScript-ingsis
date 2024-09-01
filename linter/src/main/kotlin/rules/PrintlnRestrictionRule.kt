package main.kotlin.rules

import Token
import org.example.ClassicTokenStrategies
import org.example.Lexer
import java.util.regex.Pattern

class PrintlnRestrictionRule : LinterRule {

    private val printlnPattern = Pattern.compile("""println\(([^)]+)\)""")

    override fun lintCode(input: String, lexer: Lexer): List<String> {
        val lines = input.split("\n")
        val errors = mutableListOf<String>()
        for (line in lines) {
            val matcher = printlnPattern.matcher(line)
            if (matcher.find()) { // If println is found in the line
                val argument = matcher.group(1) // Extract the argument of println
                val lexer = Lexer(argument, ClassicTokenStrategies())
                val tokens = lexer.tokenizeAll(lexer) // Tokenize the argument
                if (printlnArgumentIsInvalid(tokens)) {
                    errors.add(
                        "Invalid argument in println: ${tokens[0].getCharArray()}" +
                            " at line ${tokens[0].getPosition().getLine()}" +
                                " column ${tokens[0].getPosition().getColumn()}",
                    )
                }
            }
        }
        return errors
    }

    private fun printlnArgumentIsInvalid(tokens: List<Token>): Boolean {
        val type = tokens[0].getType()
        return tokens.size != 1 || (
            type != TokenType.IDENTIFIER &&
                type != TokenType.NUMBER_LITERAL && type != TokenType.STRING_LITERAL
            )
    }
}

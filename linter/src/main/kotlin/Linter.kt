package main.kotlin

import main.kotlin.strategies.ClassicLinterStrategies
import org.example.ClassicTokenStrategies
import org.example.Lexer
import java.io.File
import java.util.regex.Pattern

class Linter(private val config: LinterConfig, private val strategies: ClassicLinterStrategies) {

    fun checkIdentifierStrategies(identifier: String): Boolean {
        for (strategy in strategies.identifierStrategies) {
            if (strategy.checkIdentifier(identifier)) {
                return true
            }
        }
        return false
    }

    fun checkPrintlnUsage(line: String): Boolean {
        if (!config.restrictPrintln) return true
        val printlnPattern = Pattern.compile("""println\(([^)]+)\)""")
        val matcher = printlnPattern.matcher(line)
        if (matcher.find()) {
            val argument = matcher.group(1).trim()
            return argument.matches(Regex("""[a-zA-Z_][a-zA-Z0-9_]*"""))
        }
        return false
    }

    fun lintFile(file: File): List<String> {
        val lexer = Lexer(file.readText(), ClassicTokenStrategies())
        val tokens = lexer.tokenizeAll(lexer)
        val errors = mutableListOf<String>()
        for (token in tokens) {
            if (token.getType() == TokenType.IDENTIFIER) {
                if (!checkIdentifierStrategies(token.getCharArray())) {
                    errors.add(
                        "Invalid identifier: ${token.getCharArray()}" +
                            " at line ${token.getPosition().getLine()} column ${token.getPosition().getColumn()}",
                    )
                }
            }
        }
        return errors
    }
}

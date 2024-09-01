package main.kotlin.rules

import org.example.Lexer

interface LinterRule {
    fun lintCode(input: String, lexer: Lexer): List<String>
}

package main.kotlin.rules

import Token

interface LinterRule {
    fun lintCode(tokens: List<Token>): List<String>
}

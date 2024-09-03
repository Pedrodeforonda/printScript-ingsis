package main.kotlin.rules

import Token

interface LinterRule {
    fun lintCode(token: Token): List<String>
}

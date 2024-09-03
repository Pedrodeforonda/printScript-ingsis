package main.kotlin.rules

import Token
import main.kotlin.LinterConfig

interface LinterRule {
    fun lintCode(token: Token, config: LinterConfig): List<String>
}

package lexer

import org.example.lexer.TokenStrategy

interface StrategyList {
    fun getStrategies(): List<TokenStrategy>
}

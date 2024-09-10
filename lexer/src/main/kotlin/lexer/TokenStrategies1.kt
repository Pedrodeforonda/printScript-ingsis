package lexer

import org.example.lexer.TokenStrategy
import org.example.tokenManagers.StringStrategy1
import tokenManagers.AssignationStrategy
import tokenManagers.CommaStrategy
import tokenManagers.LeftBraceStrategy
import tokenManagers.LeftParenStrategy
import tokenManagers.NumberStrategy
import tokenManagers.OperatorStrategy
import tokenManagers.RightBraceStrategy
import tokenManagers.RightParenStrategy
import tokenManagers.SemicolonStrategy
import tokenManagers.StringLiteralStrategy
import tokenManagers.TypeAssignationStrategy

data class TokenStrategies1(
    val list: List<TokenStrategy> = listOf(
        AssignationStrategy(),
        NumberStrategy(),
        OperatorStrategy(),
        SemicolonStrategy(),
        StringStrategy1(),
        StringLiteralStrategy(),
        TypeAssignationStrategy(),
        LeftParenStrategy(),
        RightParenStrategy(),
        CommaStrategy(),
        LeftBraceStrategy(),
        RightBraceStrategy(),
    ),
) : StrategyList {
    override fun getStrategies(): List<TokenStrategy> {
        return list
    }
}

package lexer

import org.example.lexer.TokenStrategy
import org.example.tokenManagers.AssignationStrategy
import org.example.tokenManagers.CommaStrategy
import org.example.tokenManagers.LeftParenStrategy
import org.example.tokenManagers.NumberStrategy
import org.example.tokenManagers.OperatorStrategy
import org.example.tokenManagers.RightParenStrategy
import org.example.tokenManagers.SemicolonStrategy
import org.example.tokenManagers.StringLiteralStrategy
import org.example.tokenManagers.StringStrategy1
import org.example.tokenManagers.TypeAssignationStrategy
import tokenManagers.LeftBraceStrategy
import tokenManagers.RightBraceStrategy

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

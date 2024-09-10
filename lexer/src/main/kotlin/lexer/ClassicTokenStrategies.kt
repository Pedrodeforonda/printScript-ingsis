package org.example.lexer

import lexer.StrategyList
import tokenManagers.AssignationStrategy
import tokenManagers.CommaStrategy
import tokenManagers.LeftParenStrategy
import tokenManagers.NumberStrategy
import tokenManagers.OperatorStrategy
import tokenManagers.RightParenStrategy
import tokenManagers.SemicolonStrategy
import tokenManagers.StringLiteralStrategy
import tokenManagers.StringStrategy
import tokenManagers.TypeAssignationStrategy

data class ClassicTokenStrategies(
    val listOfStrategies: List<TokenStrategy> = listOf(
        AssignationStrategy(),
        NumberStrategy(),
        OperatorStrategy(),
        SemicolonStrategy(),
        StringStrategy(),
        StringLiteralStrategy(),
        TypeAssignationStrategy(),
        LeftParenStrategy(),
        RightParenStrategy(),
        CommaStrategy(),
    ),
) : StrategyList {
    override fun getStrategies(): List<TokenStrategy> {
        return listOfStrategies
    }
}

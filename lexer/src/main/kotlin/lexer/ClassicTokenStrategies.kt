package org.example.lexer

import lexer.StrategyList
import org.example.tokenManagers.AssignationStrategy
import org.example.tokenManagers.CommaStrategy
import org.example.tokenManagers.LeftParenStrategy
import org.example.tokenManagers.NumberStrategy
import org.example.tokenManagers.OperatorStrategy
import org.example.tokenManagers.RightParenStrategy
import org.example.tokenManagers.SemicolonStrategy
import org.example.tokenManagers.StringLiteralStrategy
import org.example.tokenManagers.StringStrategy
import org.example.tokenManagers.TypeAssignationStrategy


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

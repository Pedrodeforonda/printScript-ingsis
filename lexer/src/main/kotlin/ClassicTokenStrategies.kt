package org.example

import org.example.tokenManagers.AssignationStrategy
import org.example.tokenManagers.LeftParenStrategy
import org.example.tokenManagers.NumberStrategy
import org.example.tokenManagers.OperatorStrategy
import org.example.tokenManagers.RightParenStrategy
import org.example.tokenManagers.SemicolonStrategy
import org.example.tokenManagers.StringLiteralStrategy
import org.example.tokenManagers.StringStrategy
import org.example.tokenManagers.TypeAssignationStrategy

class ClassicTokenStrategies {
    fun getManagers(): List<TokenStrategy> {
        return listOfManagers
    }

    private var listOfManagers = listOf(
        AssignationStrategy(),
        NumberStrategy(),
        OperatorStrategy(),
        SemicolonStrategy(),
        StringStrategy(),
        StringLiteralStrategy(),
        TypeAssignationStrategy(),
        LeftParenStrategy(),
        RightParenStrategy(),
    )
}

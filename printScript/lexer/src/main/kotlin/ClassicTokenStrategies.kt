package org.example

import org.example.tokenManagers.*

class ClassicTokenStrategies {
    fun getManagers(): List<`TokenStrategy`> {
        return listOfManagers
    }

    private var listOfManagers = listOf(
        AssignationStrategy(),
        NumberStrategy(),
        OperatorStrategy(),
        SemicolonStrategy(),
        StringStrategy(),
        StringLiteralStrategy(),
        TypeAssignationStrategy()
    )
}
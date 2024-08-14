package org.example.tokenManagers

import TokenManager

class ClassicTokenManagers {
    fun getManagers(): List<TokenManager> {
        return listOfManagers
    }

    val listOfManagers = listOf(
        AssignationManager(),
        NumberManager(),
        OperatorManager(),
        SemicolonManager(),
        StringManager(),
        StringLiteralManager(),
        TypeAssignationManager()
    )
}
package org.example

import TokenManager
import org.example.tokenManagers.*

class ClassicTokenManagers {
    fun getManagers(): List<TokenManager> {
        return listOfManagers
    }

    private var listOfManagers = listOf(
        AssignationManager(),
        NumberManager(),
        OperatorManager(),
        SemicolonManager(),
        StringManager(),
        StringLiteralManager(),
        TypeAssignationManager()
    )
}
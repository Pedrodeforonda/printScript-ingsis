package org.example

import org.example.tokenManagers.AssignationManager
import org.example.tokenManagers.NumberManager
import org.example.tokenManagers.OperatorManager
import org.example.tokenManagers.SemicolonManager
import org.example.tokenManagers.StringLiteralManager
import org.example.tokenManagers.StringManager
import org.example.tokenManagers.TypeAssignationManager

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
        TypeAssignationManager(),
    )
}

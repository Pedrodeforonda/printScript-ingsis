package main.kotlin.rules

import Token
import TokenType

class PrintlnRestrictionRule : LinterRule {

    override fun lintCode(token: Token): List<String> {
        val errors = mutableListOf<String>()
        /*val listsOfPrintlnArgumentTokens = getListsOfPrintlnArgumentTokens(listOf(token).asSequence())
        for (printlnArgumentTokens in listsOfPrintlnArgumentTokens) {
            if (printlnArgumentIsInvalid(printlnArgumentTokens)) {
                errors.add(
                    "Invalid argument in println" +
                        " at line ${printlnArgumentTokens[0].getPosition().getLine()}" +
                        " column ${printlnArgumentTokens[0].getPosition().getColumn()}",
                )
            }
        }*/
        return errors
    }

    /*private fun getListsOfPrintlnArgumentTokens(tokens: Sequence<Token>): List<List<Token>> {
        val listsOfPrintlnArgumentTokens = mutableListOf<List<Token>>()
        for ((index, token) in tokens.withIndex()) {
            if (token.getType() == TokenType.CALL_FUNC && token.getCharArray() == "println") {
                if (tokens[index + 1].getType() == TokenType.LEFT_PAREN) {
                    val printlnArgumentTokens = mutableListOf<Token>()
                    var i = index + 2
                    while (tokens[i].getType() != TokenType.RIGHT_PAREN) {
                        printlnArgumentTokens.add(tokens[i])
                        i++
                    }
                    listsOfPrintlnArgumentTokens.add(printlnArgumentTokens)
                }
            }
        }
        return listsOfPrintlnArgumentTokens
    }

     */

    private fun printlnArgumentIsInvalid(argumentTokens: List<Token>): Boolean {
        val type = argumentTokens[0].getType()
        return argumentTokens.size != 1 || (
            type != TokenType.IDENTIFIER &&
                type != TokenType.NUMBER_LITERAL && type != TokenType.STRING_LITERAL
            )
    }
}

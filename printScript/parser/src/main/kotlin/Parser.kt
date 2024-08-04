import org.example.ASTNode

class Parser {

    val priorityMap: Map<TokenType, Int> = mapOf(
        TokenType.STRING_TYPE to 0,
        TokenType.NUMBER_TYPE to 0,
        TokenType.STRING_LITERAL to 0,
        TokenType.TYPE_ASSIGNATION to 1,
        TokenType.IDENTIFIER to 5,
        TokenType.NUMBER_LITERAL to 0,
        TokenType.ASSIGNATION to 10,
        TokenType.OPERATOR to 5,
        TokenType.COMPARE_OPERATOR to 5
    )
    

    private fun tokToAST(tokens: List<Token>) : ASTNode {

    }

    private fun verifyAST(ast: ASTNode) : Boolean {

    }

    fun parse(tokens: List<Token>) : ASTNode {
        val ast = tokToAST(tokens)
        if (verifyAST(ast)) {
            return ast
        } else {
            throw Exception("Invalid AST")
        }
    }
}
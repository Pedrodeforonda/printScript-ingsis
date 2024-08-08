import org.example.*
import org.example.nodes.*
import org.example.PrinterVisitor

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

    val types = listOf(TokenType.STRING_TYPE, TokenType.NUMBER_TYPE)
    

    private fun tokToAST(tokens: List<Token>) : List<Node> {
        var res = mutableListOf<Node>()
        var cursor: Int = 0
        var ast: Node? = null;
        while (cursor < tokens.size) {
            val token: Token = tokens[cursor]
            // TODO: refactor this to use the strategy pattern
            when (token.getType()) {
                TokenType.LET_KEYWORD -> {
                    ast = buildDeclaration(tokens.subList(cursor, cursor + 4))
                    cursor += 4
                }

                TokenType.ASSIGNATION -> {
                    ast = buildAssignation(tokens.subList(cursor, cursor + 2), ast)
                    cursor += 2
                }
                TokenType.CALL_FUNC -> {
                    ast = buildCallFunc(tokens.subList(cursor, tokens.size))
                    cursor += 2
                }

                TokenType.SEMICOLON -> {
                    res.add(ast!!)
                    ast = null
                    cursor += 1
                }
                else -> {
                    throw Exception("Invalid token")
                }
            }
        }
        return res
    }

    private fun buildAssignation(subList: List<Token>, currentTree: Node?): Node {
        if (currentTree == null) {
            throw Exception("Invalid assignation")
        }
        //check if the current tree is a declaration
        val printerVisitor = PrinterVisitor();
        val astType: Any = currentTree.accept(printerVisitor);
        //check if ast is a string
        if(astType !is String) {
            throw Exception("Unexpected error")
        }
        if(astType != "Declaration") {
            throw Exception("Invalid assignation")
        }
        val declaration: Declaration = currentTree as Declaration
        val node: Literal = Literal(subList[1].getCharArray().concatToString())
        return Assignment(declaration, node)


    }

    private fun buildExpression(subList: List<Token>): Node {
        TODO()
    }

    private fun buildDeclaration(subList: List<Token>) : Node {
        val declarationKeyWord: DeclarationKeyWord = if(subList[0].getType() == TokenType.LET_KEYWORD) {
            DeclarationKeyWord.LET_KEYWORD
        } else {
            throw Exception("Invalid declaration keyword")
        }
        val name: String = subList[1].getCharArray().concatToString()
        if (!name.matches(Regex("[a-zA-Z]+"))) {
            throw Exception("Invalid variable name")
        }
        val assignationChar = subList[2].getType()
        if (assignationChar != TokenType.TYPE_ASSIGNATION) {
            throw Exception("Invalid assignation")
        }
        val type: TokenType = subList[3].getType()
        if (!types.contains(subList[3].getType())) {
            throw Exception("Invalid type")
        }

        return Declaration(name, type.toString(), declarationKeyWord)
    }

    private fun buildCallFunc(subList: List<Token>) : Node {
        val name = subList[0].getCharArray().concatToString()
        if (!name.matches(Regex("[a-zA-Z]+"))) {
            throw Exception("Invalid function name")
        }
        val arguments = subList[1].getCharArray().concatToString()
        val type = subList[1].getType()
        if (type == TokenType.STRING_LITERAL) {
            return CallNode(name, listOf(Literal(arguments)))
        }
        if(type == TokenType.NUMBER_LITERAL) {
            return CallNode(name, listOf(Literal(arguments)))
        }
        if (type == TokenType.IDENTIFIER) {
            return CallNode(name, listOf(Identifier(arguments)))
        }
        throw Exception("Invalid argument")
    }

    fun parse(tokens: List<Token>) : List<Node> {
        val ast = tokToAST(tokens)
        return ast
    }


}
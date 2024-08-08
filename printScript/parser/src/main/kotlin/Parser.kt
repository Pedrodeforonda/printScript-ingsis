import nodes.Nud
import org.example.*
import org.example.nodes.*

class Parser(val tokens: List<Token>) {

    val lookups = Lookups()

    init {
        lookups.createTokenLookups()
    }


    private var cursor: Int = 0


    private fun parseExpr(bp: Int): List<Node> {
        val res: MutableList<Node> = mutableListOf()
        val token = tokens[cursor]
        var left = typeToNud(lookups.getNud(token.getType())).parse()

        while (bp < (lookups.getBP(tokens[cursor].getType())?.let { BindingPowers().getBP(it) } ?: 0)) {
            val led = lookups.getLed(tokens[cursor].getType())
            left = led?.eval(this, tokens[cursor], left!!)
            cursor++
        }

        if (tokens[cursor].getType() == TokenType.SEMICOLON) {
            res.add(left!!)
        }

        return res
    }

    private fun parse(): List<Node> {
        return parseExpr(  0)
    }


    fun advance(index: Int): Pair<Token, Int> {
        val nextIndex = index + 1
        return (tokens[nextIndex] to nextIndex)
    }

    fun currentToken(index: Int): Token {
        return tokens[index]
    }

    fun previousToken(index: Int): Pair<Token, Int> {
        return (tokens[index - 1] to index - 1)
    }

    fun currentTokenKind(index: Int): TokenType {
        return tokens[index].getType()
    }

    fun hasToken(index: Int): Boolean {
        return index < tokens.size
    }

    fun consume(expectedType: TokenType): Token {
        val token = tokens[cursor]
        if (token.getType() != expectedType) {
            throw Exception("Expected token of type $expectedType but found ${token.getType()}")
        }
        cursor++
        return token
    }

    fun typeToNud(token: NodeTypes): Nud {
        return when (token) {
            NodeTypes.LITERAL -> Literal(null, TokenType.STRING_TYPE)
            else -> throw Exception("Invalid token type")
        }
    }

    fun typeToLed(token: NodeTypes): Led {
        return when (token) {
            NodeTypes.ADDITION -> Addition(null, null, TokenType.PLUS)
            NodeTypes.LOGICAL -> Logical(null, null, TokenType.AND)
            NodeTypes.MULTIPLICATION -> Multiplication(null, null, TokenType.MULTIPLICATION)
            else -> throw Exception("Invalid token type")
        }
    }
}
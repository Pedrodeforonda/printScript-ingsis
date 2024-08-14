import nodes.*
import parsers.*

class Parser(private val tokens: List<Token>) {
    private val prefixParsers = mutableMapOf<TokenType, Prefix>()
    private val infixParsers = mutableMapOf<TokenType, Infix>()


    init {
        registerPrefix(TokenType.LET_KEYWORD, DeclarationParser())
        registerPrefix(TokenType.IDENTIFIER, IdentifierParser())
        registerPrefix(TokenType.MINUS, PrefixOperatorParser())
        registerPrefix(TokenType.STRING_LITERAL, LiteralParser())
        registerPrefix(TokenType.NUMBER_LITERAL, LiteralParser())
        registerInfix(TokenType.ASSIGNATION, AssignationParser())
        registerInfix(TokenType.PLUS, BinaryOperationParser(Precedence.SUM))
        registerInfix(TokenType.MINUS, BinaryOperationParser(Precedence.SUM))
        registerInfix(TokenType.SLASH, BinaryOperationParser(Precedence.PRODUCT))
        registerInfix(TokenType.ASTERISK, BinaryOperationParser(Precedence.PRODUCT))
        registerPrefix(TokenType.CALL_FUNC, FunCallParser())
    }


    private var currentToken: Token = tokens[0]
    private var currentIndex = 0

    private fun registerPrefix(tokenType: TokenType, parser: Prefix) {
        prefixParsers[tokenType] = parser
    }

    private fun registerInfix(tokenType: TokenType, parser: Infix) {
        infixParsers[tokenType] = parser
    }

    fun parseExpression(precedence: Int = 0): Node {
        val token = currentToken
        val prefixParser = prefixParsers[token.getType()] ?: throw ParseException(
            "Unexpected token: ${
                token.getCharArray().concatToString()
            }"
        )

        val left = prefixParser.parse(this, token)

        while (precedence < getPrecedence()) {
            val infixParser = infixParsers[currentToken.getType()] ?: break
            consume()
            return infixParser.parse(this, left, currentToken)
        }

        return left
    }

    fun parseExpressions(): List<Node> {
        val expressions = mutableListOf<Node>()

        while (hasNextToken()) {
            expressions.add(parseExpression())
            if (currentToken.getType() == TokenType.SEMICOLON && !hasNextToken()) {
               return expressions
            }
            if (currentToken.getType() == TokenType.SEMICOLON) {
                consume()
            }
        }

        return expressions
    }

    private fun hasNextToken(): Boolean = currentIndex < tokens.size - 1

    private fun getPrecedence(): Int {
        val infixParser = infixParsers[currentToken.getType()]
        return infixParser?.getPrecedence() ?: 0
    }

    public fun consume(): Token {
        val token = currentToken
        currentToken = tokens.getOrNull(++ currentIndex ) ?: throw ParseException("Unexpected end of input")
        return token
    }

    fun lookAhead(offset: Int): Token {
        return tokens.getOrNull(currentIndex + offset + 1) ?: throw ParseException("Unexpected end of input")
    }

    fun getCurrentToken(): Token {
        return currentToken
    }

}

class ParseException(message: String) : RuntimeException(message)

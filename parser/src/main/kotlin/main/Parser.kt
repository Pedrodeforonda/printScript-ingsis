package main

import nodes.Node
import parsers.AssignationParser
import parsers.BinaryOperationParser
import parsers.DeclarationParser
import parsers.FunCallParser
import parsers.IdentifierParser
import parsers.Infix
import parsers.LiteralParser
import parsers.Prefix
import parsers.PrefixOperatorParser
import utils.ParsingResult

class Parser(private val tokens: Iterator<Token>) {
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

    private var currentToken: Token = tokens.next()

    private fun registerPrefix(tokenType: TokenType, parser: Prefix) {
        prefixParsers[tokenType] = parser
    }

    private fun registerInfix(tokenType: TokenType, parser: Infix) {
        infixParsers[tokenType] = parser
    }

    fun parseExpression(precedence: Int = 0): Node {
        val token = currentToken
        val prefixParser = prefixParsers[token.getType()] ?: throw ParseException(
            "Syntax Error At: ${token.getPosition().getLine()}, ${token.getPosition().getColumn()}" +
                " Unknown token ${token.getText()}",
        )

        var left = prefixParser.parse(this, token)

        while (precedence < getPrecedence()) {
            val infixParser = infixParsers[currentToken.getType()] ?: break
            if (infixParser is BinaryOperationParser) {
                infixParser.updateToken(currentToken)
            }
            consume()
            left = infixParser.parse(this, left, currentToken)
        }

        return left
    }

    fun parseExpressions(): Sequence<ParsingResult> = sequence {
        while (hasNextToken()) {
            try {
                val result: Node = parseExpression()
                if (checkExpressions(result).isEmpty()) {
                    yield(ParsingResult(result, null))
                } else {
                    yield(ParsingResult(null, ParseException(checkExpressions(result)[0].message!!)))
                    while (currentToken.getType() != TokenType.SEMICOLON && hasNextToken()) consume()
                }
            } catch (e: ParseException) {
                yield(ParsingResult(null, e))
                while (currentToken.getType() != TokenType.SEMICOLON && hasNextToken()) consume()
            }
            if (currentToken.getType() == TokenType.SEMICOLON && hasNextToken()) consume()
        }
    }

    private fun hasNextToken(): Boolean = tokens.hasNext()

    private fun getPrecedence(): Int {
        val infixParser = infixParsers[currentToken.getType()]
        return infixParser?.getPrecedence() ?: 0
    }

    fun consume(): Token {
        try {
            currentToken = tokens.next()
        } catch (e: NoSuchElementException) {
            throw ParseException("Unexpected end of input")
        }
        return currentToken
    }

    fun getCurrentToken(): Token {
        return currentToken
    }

    private fun checkExpressions(expression: Node): MutableList<Error> {
        val variables = HashMap<String, String>()
        val visitor = ExpressionCheckerVisitor(variables)
        val result: MutableList<Error> = ArrayList()
        val checkResult = expression.accept(visitor)
        if (checkResult is CheckAstResult) {
            if (!checkResult.isPass()) {
                result.add(Error(checkResult.getMessage()))
            }
        }
        return result
    }
}

class ParseException(message: String) : RuntimeException(message)

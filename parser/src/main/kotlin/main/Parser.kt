package main

import nodes.Node
import parsers.v1.BinaryOperationParser
import utils.CheckAstResult
import utils.ParsingResult

class Parser(private val tokens: Iterator<Token>) {
    private val prefixParsers = mutableMapOf<TokenType, Prefix>()
    private val infixParsers = mutableMapOf<TokenType, Infix>()

    private var currentToken: Token = tokens.next()
    private var prevToken: Token = currentToken

    internal fun registerPrefix(tokenType: TokenType, parser: Prefix) {
        prefixParsers[tokenType] = parser
    }

    internal fun registerInfix(tokenType: TokenType, parser: Infix) {
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
                    while (sentenceNotConsumed()) consume()
                }
            } catch (e: ParseException) {
                yield(ParsingResult(null, e))
                while (sentenceNotConsumed()) consume()
            }
            if (hasNotClosingChar() && hasNextToken()) {
                yield(
                    ParsingResult(
                        null,
                        ParseException(
                            "Syntax Error At: " +
                                "${currentToken.getPosition().getLine()}, ${currentToken.getPosition().getColumn()}",
                        ),
                    ),
                )
            }
            if (hasClosingChar() && hasNextToken()) {
                consume()
            }
        }
    }

    private fun hasClosingChar() = (
        currentToken.getType() == TokenType.SEMICOLON ||
            currentToken.getType() == TokenType.RIGHT_BRACE
        )

    private fun hasNotClosingChar() = (
        currentToken.getType() != TokenType.SEMICOLON &&
            currentToken.getType() != TokenType.RIGHT_BRACE
        ) && prevToken.getType() != TokenType.RIGHT_BRACE

    private fun sentenceNotConsumed() = currentToken.getType() != TokenType.SEMICOLON &&
        currentToken.getType() != TokenType.RIGHT_BRACE &&
        hasNextToken()

    fun hasNextToken(): Boolean = tokens.hasNext()

    private fun getPrecedence(): Int {
        val infixParser = infixParsers[currentToken.getType()]
        return infixParser?.getPrecedence() ?: 0
    }

    fun consume(): Token {
        try {
            prevToken = currentToken
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

package parsers.v2

import main.ParseException
import main.Parser
import main.Token
import main.TokenType
import nodes.IfNode
import nodes.Literal
import nodes.Node
import parsers.v1.Prefix

class IfParser : Prefix {

    override fun parse(parser: Parser, token: Token): Node {
        if (parser.consume().getType() != TokenType.LEFT_PAREN) {
            throw ParseException(
                "Expected LEFT_PARENTHESIS, got ${token.getType()} at line ${
                    token.getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
        }
        parser.consume()
        val condition = parser.parseExpression()
        when (condition) {
            is Literal -> {
                if (condition.getType() != TokenType.BOOLEAN_LITERAL) {
                    throw ParseException(
                        "Expected BOOLEAN_LITERAL, got ${condition.getType()} at line ${
                            token.getPosition().getLine()
                        }, column ${token.getPosition().getColumn()}",
                    )
                }
            }

            else -> {
                throw ParseException(
                    "Expected BOOLEAN_LITERAL, got ${condition.javaClass} at line ${
                        token.getPosition().getLine()
                    }, column ${token.getPosition().getColumn()}",
                )
            }
        }
        if (parser.getCurrentToken().getType() != TokenType.RIGHT_PAREN) {
            throw ParseException(
                "Expected RIGHT_PARENTHESIS, got ${token.getType()} at line ${
                    token.getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
        }
        val ifBody: ArrayList<Node> = getBody(parser, token)

        if (!parser.hasNextToken()) {
            return IfNode(condition, ifBody, ArrayList(), token.getPosition())
        }

        if (parser.consume().getType() != TokenType.ELSE_KEYWORD) {
            return IfNode(condition, ifBody, ArrayList(), token.getPosition())
        }

        val elseBody: ArrayList<Node> = getBody(parser, token)

        return IfNode(condition, ifBody, elseBody, token.getPosition())
    }

    private fun getBody(parser: Parser, token: Token): ArrayList<Node> {
        if (parser.consume().getType() != TokenType.LEFT_BRACE) {
            throw ParseException(
                "Expected LEFT_BRACE, got ${token.getType()} at line ${
                    token.getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
        }

        parser.consume()
        val ifBody: ArrayList<Node> = ArrayList()
        while (parser.getCurrentToken().getType() != TokenType.RIGHT_BRACE) {
            ifBody.add(parser.parseExpression())
            if (parser.getCurrentToken().getType() != TokenType.SEMICOLON) {
                throw ParseException(
                    "Expected SEMICOLON, got ${token.getType()} at line ${
                        token.getPosition().getLine()
                    }, column ${token.getPosition().getColumn()}",
                )
            }
            val consumed = parser.consume()
            if (consumed.getType() == TokenType.RIGHT_BRACE) {
                break
            }
        }

        if (parser.getCurrentToken().getType() != TokenType.RIGHT_BRACE) {
            throw ParseException(
                "Expected RIGHT_BRACE, got ${token.getType()} at line ${
                    token.getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
        }
        return ifBody
    }
}

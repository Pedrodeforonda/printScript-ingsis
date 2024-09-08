package parsers

import main.ParseException
import main.Parser
import main.Token
import main.TokenType
import nodes.IfNode
import nodes.Literal
import nodes.Node

class IfParser : Prefix {

    override fun parse(parser: Parser, token: Token): Node {
        if (token.getType() != TokenType.IF_KEYWORD) {
            throw ParseException(
                "Expected IF_KEYWORD, got ${token.getType()} at line ${
                    token.getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
        }
        if (parser.consume().getType() != TokenType.LEFT_PAREN) {
            throw ParseException(
                "Expected LEFT_PARENTHESIS, got ${token.getType()} at line ${
                    token.getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
        }
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
        if (parser.consume().getType() != TokenType.RIGHT_PAREN) {
            throw ParseException(
                "Expected RIGHT_PARENTHESIS, got ${token.getType()} at line ${
                    token.getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
        }
        val ifBody: ArrayList<Node> = getBody(parser, token)

        if (parser.consume().getType() != TokenType.ELSE_KEYWORD) {
            throw ParseException(
                "Expected ELSE_KEYWORD, got ${token.getType()} at line ${
                    token.getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
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

        val ifBody: ArrayList<Node> = ArrayList()
        while (parser.getCurrentToken().getType() != TokenType.RIGHT_BRACE) {
            ifBody.add(parser.parseExpression())
            val consumed = parser.consume()
            if (consumed.getType() == TokenType.RIGHT_BRACE) {
                break
            }
            if (consumed.getType() != TokenType.SEMICOLON) {
                throw ParseException(
                    "Expected SEMICOLON, got ${token.getType()} at line ${
                        token.getPosition().getLine()
                    }, column ${token.getPosition().getColumn()}",
                )
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

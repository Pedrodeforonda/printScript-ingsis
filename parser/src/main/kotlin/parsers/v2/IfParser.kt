package parsers.v2

import main.ParseException
import main.Parser
import main.Prefix
import main.Token
import main.TokenType
import nodes.Identifier
import nodes.IfNode
import nodes.Literal
import nodes.Node

class IfParser : Prefix {

    override fun parse(parser: Parser, token: Token): Node {
        if (parser.consume().getType() != TokenType.LEFT_PAREN) {
            throw ParseException(
                "Expected LEFT_PARENTHESIS, got ${parser.getCurrentToken().getType()} at line ${
                    parser.getCurrentToken().getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
        }
        parser.consume()
        val condition = parser.parseExpression()
        when (condition) {
            is Literal -> {
                if (!condition.isBoolean()) {
                    throw ParseException(
                        "Expected BOOLEAN_LITERAL, got ${condition.getType()} at line ${
                            parser.getCurrentToken().getPosition().getLine()
                        }, column ${parser.getCurrentToken().getPosition().getColumn()}",
                    )
                }
            }
            is Identifier -> {}

            else -> {
                throw ParseException(
                    "Expected BOOLEAN_LITERAL, got ${condition.javaClass} at line ${
                        token.getPosition().getLine()
                    }, column ${parser.getCurrentToken().getPosition().getColumn()}",
                )
            }
        }
        if (parser.getCurrentToken().getType() != TokenType.RIGHT_PAREN) {
            throw ParseException(
                "Expected RIGHT_PARENTHESIS,got ${parser.getCurrentToken().getType()} at line ${
                    parser.getCurrentToken().getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
        }
        val ifBody: ArrayList<Node> = getBody(parser, token)

        if (!parser.hasNextToken()) {
            return IfNode(condition, ifBody, ArrayList(), parser.adaptPos(token.getPosition()))
        }

        if (parser.consume().getType() != TokenType.ELSE_KEYWORD) {
            return IfNode(condition, ifBody, ArrayList(), parser.adaptPos(token.getPosition()))
        }

        val elseBody: ArrayList<Node> = getBody(parser, token)

        return IfNode(condition, ifBody, elseBody, parser.adaptPos(token.getPosition()))
    }

    private fun getBody(parser: Parser, token: Token): ArrayList<Node> {
        if (parser.consume().getType() != TokenType.LEFT_BRACE) {
            throw ParseException(
                "Expected LEFT_BRACE,got ${parser.getCurrentToken().getType()} at line ${
                    parser.getCurrentToken().getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
        }

        parser.consume()
        val ifBody: ArrayList<Node> = ArrayList()
        while (parser.getCurrentToken().getType() != TokenType.RIGHT_BRACE) {
            ifBody.add(parser.parseExpression())
            if (parser.getCurrentToken().getType() != TokenType.SEMICOLON) {
                throw ParseException(
                    "Expected SEMICOLON, got ${parser.getCurrentToken().getType()} at line ${
                        parser.getCurrentToken().getPosition().getLine()
                    }, column ${parser.getCurrentToken().getPosition().getColumn()}",
                )
            }
            val consumed = parser.consume()
            if (consumed.getType() == TokenType.RIGHT_BRACE) {
                break
            }
        }

        if (parser.getCurrentToken().getType() != TokenType.RIGHT_BRACE) {
            throw ParseException(
                "Expected RIGHT_BRACE,got ${parser.getCurrentToken().getType()} at line ${
                    parser.getCurrentToken().getPosition().getLine()
                }, column ${token.getPosition().getColumn()}",
            )
        }
        return ifBody
    }
}

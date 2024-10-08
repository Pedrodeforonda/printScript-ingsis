package parsers.v2

import main.ParseException
import main.Parser
import main.Prefix
import main.Token
import main.TokenType
import nodes.BinaryNode
import nodes.Identifier
import nodes.Literal
import nodes.Node
import nodes.ReadInput

class ReadInputParser : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        if (parser.consume().getType() != TokenType.LEFT_PAREN) {
            throw ParseException(
                "Syntax Error at ${parser.getCurrentToken().getPosition().getLine()}," +
                    " ${parser.getCurrentToken().getPosition().getColumn()}" +
                    " Expected left parenthesis",
            )
        }

        parser.consume()
        val message = parser.parseExpression()
        val type: String?

        when (message) {
            is Literal -> {
                type = "Literal"
                if (!message.isString()) {
                    throw ParseException(
                        "Syntax Error at ${parser.getCurrentToken().getPosition().getLine()}," +
                            " ${parser.getCurrentToken().getPosition().getColumn()}" +
                            " Expected STRING_LITERAL",
                    )
                }
            }
            // identifier continues
            // does not have a message: ignore
            is Identifier -> {
                type = "Identifier"
            }
            is BinaryNode -> {
                type = "BinaryNode"
            }
            else -> {
                throw ParseException(
                    "Syntax Error at ${parser.getCurrentToken().getPosition().getLine()}," +
                        " ${parser.getCurrentToken().getPosition().getColumn()}" +
                        " Expected STRING_LITERAL or IDENTIFIER or BIN_OP",
                )
            }
        }

        if (parser.getCurrentToken().getType() != TokenType.RIGHT_PAREN) {
            throw ParseException(
                "Syntax Error at ${parser.getCurrentToken().getPosition().getLine()}, " +
                    "${parser.getCurrentToken().getPosition().getColumn()}" +
                    " Expected right parenthesis",
            )
        }
        parser.consume()

        return when (type) {
            "Literal" -> ReadInput(message as Literal, parser.adaptPos(token.getPosition()))
            "Identifier" -> ReadInput(message as Identifier, parser.adaptPos(token.getPosition()))
            "BinaryNode" -> ReadInput(message as BinaryNode, parser.adaptPos(token.getPosition()))
            else -> throw ParseException(
                "Syntax Error at ${parser.getCurrentToken().getPosition().getLine()}, " +
                    "${parser.getCurrentToken().getPosition().getColumn()}" +
                    " Expected STRING_LITERAL or IDENTIFIER or BIN_OP",
            )
        }
    }
}

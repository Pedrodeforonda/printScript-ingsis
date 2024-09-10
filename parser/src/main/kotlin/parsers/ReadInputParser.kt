package parsers

import main.ParseException
import main.Parser
import main.Token
import main.TokenType
import nodes.Node
import nodes.ReadInput

class ReadInputParser : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        if (parser.consume().getType() != TokenType.LEFT_PAREN) {
            throw ParseException(
                "Syntax Error at ${token.getPosition().getLine()}, ${token.getPosition().getColumn()}" +
                    " Expected left parenthesis",
            )
        }

        parser.consume()
        val message = parser.parseExpression()
        var type: String? = null

        when (message) {
            is nodes.Literal -> {
                type = "Literal"
                if (message.getType() != TokenType.STRING_LITERAL) {
                    throw ParseException(
                        "Syntax Error at ${token.getPosition().getLine()}, ${token.getPosition().getColumn()}" +
                            " Expected STRING_LITERAL",
                    )
                }
            }
            // identifier continues
            // does not have a message: ignore
            is nodes.Identifier -> {
                type = "Identifier"
            }

            else -> {
                throw ParseException(
                    "Syntax Error at ${token.getPosition().getLine()}, ${token.getPosition().getColumn()}" +
                        " Expected STRING_LITERAL",
                )
            }
        }

        if (parser.getCurrentToken().getType() != TokenType.RIGHT_PAREN) {
            throw ParseException(
                "Syntax Error at ${token.getPosition().getLine()}, ${token.getPosition().getColumn()}" +
                    " Expected right parenthesis",
            )
        }

        if (type == "Literal") {
            return ReadInput(message as nodes.Literal, token.getPosition())
        }

        return ReadInput(message as nodes.Identifier, token.getPosition())
    }
}

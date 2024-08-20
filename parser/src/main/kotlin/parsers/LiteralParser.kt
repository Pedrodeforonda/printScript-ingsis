package parsers

import ParseException
import Parser
import Token
import nodes.Literal
import nodes.Node

class LiteralParser : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        parser.consume()
        return Literal(
            when (token.getType()) {
                TokenType.NUMBER_LITERAL -> token.getCharArray().toInt()
                TokenType.STRING_LITERAL -> token.getCharArray()
                else -> throw ParseException("Invalid literal type")
            },
        )
    }
}

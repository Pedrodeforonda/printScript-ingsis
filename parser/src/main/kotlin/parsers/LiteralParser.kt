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
                TokenType.NUMBER_LITERAL -> token.getCharArray().concatToString().toInt()
                TokenType.STRING_TYPE -> token.getCharArray().concatToString()
                else -> throw ParseException("Invalid literal type")
            },
        )
    }
}

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
                TokenType.NUMBER_LITERAL -> token.getText().toInt()
                TokenType.STRING_LITERAL -> token.getText()
                else -> throw ParseException("Invalid literal type")
            },
            token.getPosition(),
        )
    }
}

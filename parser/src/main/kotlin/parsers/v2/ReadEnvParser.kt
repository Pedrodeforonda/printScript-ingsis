package parsers.v2

import main.ParseException
import main.Parser
import main.Prefix
import main.Token
import main.TokenType
import nodes.Node

class ReadEnvParser : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        if (parser.consume().getType() != TokenType.LEFT_PAREN) {
            throw ParseException(
                "Syntax Error at ${token.getPosition().getLine()}, ${token.getPosition().getColumn()}" +
                    " Expected left parenthesis",
            )
        }

        if (parser.consume().getType() != TokenType.STRING_LITERAL) {
            throw ParseException(
                "Syntax Error at ${token.getPosition().getLine()}, ${token.getPosition().getColumn()}" +
                    " Expected STRING_LITERAL",
            )
        }

        val name = parser.getCurrentToken().getText()

        if (parser.consume().getType() != TokenType.RIGHT_PAREN) {
            throw ParseException(
                "Syntax Error at ${token.getPosition().getLine()}, ${token.getPosition().getColumn()}" +
                    " Expected right parenthesis",
            )
        }

        return nodes.ReadEnv(name, token.getPosition())
    }
}

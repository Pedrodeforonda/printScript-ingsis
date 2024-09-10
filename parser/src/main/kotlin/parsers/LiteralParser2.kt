package parsers

import main.ParseException
import main.Parser
import main.Token
import main.TokenType
import nodes.Literal
import nodes.Node

class LiteralParser2 : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        parser.consume()

        var stringValue: String? = null
        var numberValue: Number? = null
        var booleanValue: Boolean? = null
        when (token.getType()) {
            TokenType.NUMBER_LITERAL -> {
                numberValue = if (token.getText().contains(".")) {
                    token.getText().toDouble()
                } else {
                    token.getText().toInt()
                }
            }
            TokenType.STRING_LITERAL -> {
                stringValue = token.getText()
            }
            TokenType.BOOLEAN_LITERAL -> {
                booleanValue = when (token.getText()) {
                    "true" -> true
                    "false" -> false
                    else -> throw ParseException("Invalid boolean literal")
                }
            }
            else -> throw ParseException("Invalid literal type")
        }

        stringValue?.let {
            return Literal(it, token.getPosition(), token.getType())
        }
        numberValue?.let {
            return Literal(it, token.getPosition(), token.getType())
        }
        booleanValue?.let {
            return Literal(it, token.getPosition(), token.getType())
        }
        throw ParseException("Invalid literal type")
    }
}

package parsers.v2

import main.ParseException
import main.Parser
import main.Prefix
import main.Token
import main.TokenType
import nodes.Literal
import nodes.Node
import types.LiteralType

class LiteralParser : Prefix {
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
            return Literal(it, parser.adaptPos(token.getPosition()), tokenTypeToLiteralType(token.getType()))
        }
        numberValue?.let {
            return Literal(it, parser.adaptPos(token.getPosition()), tokenTypeToLiteralType(token.getType()))
        }
        booleanValue?.let {
            return Literal(it, parser.adaptPos(token.getPosition()), tokenTypeToLiteralType(token.getType()))
        }
        throw ParseException("Invalid literal type")
    }

    private fun tokenTypeToLiteralType(tokenType: TokenType): LiteralType {
        return when (tokenType) {
            TokenType.NUMBER_LITERAL -> LiteralType.NUMBER
            TokenType.STRING_LITERAL -> LiteralType.STRING
            TokenType.BOOLEAN_LITERAL -> LiteralType.BOOLEAN
            else -> throw ParseException("Invalid literal type")
        }
    }
}

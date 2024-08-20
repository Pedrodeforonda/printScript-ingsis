package parsers

import DeclarationKeyWord
import ParseException
import Parser
import Token
import TokenType
import nodes.Declaration
import nodes.Node

class DeclarationParser : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        val letToken: Token = token
        if (letToken.getType() != TokenType.LET_KEYWORD) {
            throw ParseException("Expected let token")
        }
        val identifierToken: Token = parser.lookAhead(0)
        parser.consume()
        if (identifierToken.getType() != TokenType.IDENTIFIER) {
            throw ParseException("Expected identifier token")
        }
        val typeAssignation = parser.lookAhead(0)
        parser.consume()
        if (typeAssignation.getType() != TokenType.TYPE_ASSIGNATION) {
            throw ParseException("Expected type assignation token")
        }
        val type = parser.lookAhead(0)
        parser.consume()
        if (type.getType() != TokenType.STRING_TYPE && type.getType() != TokenType.NUMBER_TYPE) {
            throw ParseException("Expected type token")
        }
        parser.consume()
        val letKeword: DeclarationKeyWord = DeclarationKeyWord.LET_KEYWORD
        return Declaration(
            identifierToken.getCharArray().concatToString(),
            type.getCharArray().concatToString(),
            letKeword,
        )
    }
}

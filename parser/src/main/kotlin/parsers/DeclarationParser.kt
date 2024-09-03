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
            throw ParseException(
                "Semantic Error at ${letToken.getPosition().getLine()}, ${letToken.getPosition().getColumn()}" +
                    " Expected let keyword",
            )
        }
        val identifierToken: Token = parser.consume()
        if (identifierToken.getType() != TokenType.IDENTIFIER) {
            throw ParseException(
                "Semantic Error at ${identifierToken.getPosition().getLine()}, " +
                    "${identifierToken.getPosition().getColumn()} Expected identifier token",
            )
        }
        val typeAssignation = parser.consume()
        if (typeAssignation.getType() != TokenType.TYPE_ASSIGNATION) {
            throw ParseException(
                "Semantic Error at ${typeAssignation.getPosition().getLine()}, " +
                    "${typeAssignation.getPosition().getColumn()} Expected type assignation",
            )
        }
        val type = parser.consume()
        if (type.getType() != TokenType.STRING_TYPE && type.getType() != TokenType.NUMBER_TYPE) {
            throw ParseException(
                "Semantic Error at ${type.getPosition().getLine()}, ${type.getPosition().getColumn()} Expected type",
            )
        }
        parser.consume()
        val letKeword: DeclarationKeyWord = DeclarationKeyWord.LET_KEYWORD
        return Declaration(
            identifierToken.getText(),
            type.getText(),
            letKeword,
            letToken.getPosition(),
        )
    }
}

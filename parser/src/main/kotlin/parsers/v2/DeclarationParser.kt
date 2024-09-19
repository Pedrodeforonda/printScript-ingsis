package parsers.v2

import main.ParseException
import main.Parser
import main.Prefix
import main.Token
import main.TokenType
import nodes.Declaration
import nodes.Node
import types.DeclarationType

class DeclarationParser : Prefix {
    override fun parse(parser: Parser, token: Token): Node {
        val declarationToken: Token = token

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
        if (type.getType() != TokenType.STRING_TYPE && type.getType() != TokenType.NUMBER_TYPE &&
            type.getType() != TokenType.BOOLEAN_TYPE
        ) {
            throw ParseException(
                "Semantic Error at ${type.getPosition().getLine()}, ${type.getPosition().getColumn()} Expected type",
            )
        }
        parser.consume()
        val declarationKeyword = if (declarationToken.getType() == TokenType.LET_KEYWORD) {
            DeclarationType.LET_KEYWORD
        } else {
            DeclarationType.CONST_KEYWORD
        }
        return Declaration(
            identifierToken.getText(),
            type.getText(),
            declarationKeyword,
            parser.adaptPos(declarationToken.getPosition()),
        )
    }
}

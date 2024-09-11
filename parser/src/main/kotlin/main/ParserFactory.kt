package main

import parsers.v1.AssignationParser
import parsers.v1.BinaryOperationParser
import parsers.v1.DeclarationParser
import parsers.v1.FunCallParser
import parsers.v1.IdentifierParser
import parsers.v1.LiteralParser
import parsers.v2.IfParser
import parsers.v2.ReadEnvParser
import parsers.v2.ReadInputParser

class ParserFactory {
    fun createParser(version: String, tokens: Iterator<Token>): Parser {
        val parser = Parser(tokens)
        when (version) {
            "1.0" -> {
                createFirstVersionParser(parser)
                parser.registerPrefix(TokenType.STRING_LITERAL, LiteralParser())
                parser.registerPrefix(TokenType.NUMBER_LITERAL, LiteralParser())
                parser.registerPrefix(TokenType.LET_KEYWORD, DeclarationParser())
            }
            "1.1" -> {
                createFirstVersionParser(parser)
                parser.registerPrefix(TokenType.LET_KEYWORD, parsers.v2.DeclarationParser())
                parser.registerPrefix(TokenType.IF_KEYWORD, IfParser())
                parser.registerPrefix(TokenType.BOOLEAN_LITERAL, parsers.v2.LiteralParser())
                parser.registerPrefix(TokenType.READ_ENV, ReadEnvParser())
                parser.registerPrefix(TokenType.READ_INPUT, ReadInputParser())
                parser.registerPrefix(TokenType.STRING_LITERAL, parsers.v2.LiteralParser())
                parser.registerPrefix(TokenType.NUMBER_LITERAL, parsers.v2.LiteralParser())
            }
            else -> throw IllegalArgumentException("Unsupported version: $version")
        }
        return parser
    }

    private fun createFirstVersionParser(parser: Parser) {
        parser.registerPrefix(TokenType.IDENTIFIER, IdentifierParser())
        parser.registerInfix(TokenType.ASSIGNATION, AssignationParser())
        parser.registerInfix(TokenType.PLUS, BinaryOperationParser(Precedence.SUM))
        parser.registerInfix(TokenType.MINUS, BinaryOperationParser(Precedence.SUM))
        parser.registerInfix(TokenType.SLASH, BinaryOperationParser(Precedence.PRODUCT))
        parser.registerInfix(TokenType.ASTERISK, BinaryOperationParser(Precedence.PRODUCT))
        parser.registerPrefix(TokenType.CALL_FUNC, FunCallParser())
    }
}

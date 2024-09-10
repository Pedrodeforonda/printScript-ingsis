package main

import parsers.AssignationParser
import parsers.BinaryOperationParser
import parsers.DeclarationParser
import parsers.DeclarationParser2
import parsers.FunCallParser
import parsers.IdentifierParser
import parsers.IfParser
import parsers.LiteralParser
import parsers.LiteralParser2
import parsers.ReadEnvParser
import parsers.ReadInputParser

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
                parser.registerPrefix(TokenType.LET_KEYWORD, DeclarationParser2())
                parser.registerPrefix(TokenType.IF_KEYWORD, IfParser())
                parser.registerPrefix(TokenType.BOOLEAN_LITERAL, LiteralParser2())
                parser.registerPrefix(TokenType.READ_ENV, ReadEnvParser())
                parser.registerPrefix(TokenType.READ_INPUT, ReadInputParser())
                parser.registerPrefix(TokenType.STRING_LITERAL, LiteralParser2())
                parser.registerPrefix(TokenType.NUMBER_LITERAL, LiteralParser2())
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

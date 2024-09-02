import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

public class ParserTester {
    @Test
    fun testDeclaration() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token(";", TokenType.SEMICOLON, Position(1, 14)),
        )

        val parser = Parser(tokens.listIterator())
        val result = parser.parseExpressions().toList()
        val expected = sequenceOf(
            Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
        ).toList()
        assertEquals(expected, result)
    }

    @Test
    fun testAssignationAndDeclaration() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token(";", TokenType.SEMICOLON, Position(1, 14)),
            Token("a", TokenType.IDENTIFIER, Position(2, 1)),
            Token("=", TokenType.ASSIGNATION, Position(2, 3)),
            Token("1", TokenType.NUMBER_LITERAL, Position(2, 5)),
            Token(";", TokenType.SEMICOLON, Position(2, 6)),
        )

        val parser = Parser(tokens.listIterator())
        val result = parser.parseExpressions().toList()
        val expected = listOf(
            Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
            Assignation(Identifier("a", Position(1, 5)), Literal(1, Position(2, 1)), Position(2, 3)),
        )
        assertEquals(expected, result)
    }

    @Test
    fun declarationInLine() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("1", TokenType.NUMBER_LITERAL, Position(1, 16)),
            Token(";", TokenType.SEMICOLON, Position(1, 17)),
        )

        val parser = Parser(tokens.listIterator())
        val result = parser.parseExpressions().toList()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                Literal(1, Position(1, 16)),
                Position(1, 14),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun declarationInLineAndPrintln() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("1", TokenType.NUMBER_LITERAL, Position(1, 16)),
            Token(";", TokenType.SEMICOLON, Position(1, 17)),
            Token("println", TokenType.CALL_FUNC, Position(2, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(2, 8)),
            Token("a", TokenType.IDENTIFIER, Position(2, 9)),
            Token(")", TokenType.RIGHT_PAREN, Position(2, 10)),
            Token(";", TokenType.SEMICOLON, Position(2, 11)),
        )

        val parser = Parser(tokens.listIterator())
        val result = parser.parseExpressions().toList()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                Literal(1, Position(1, 16)),
                Position(1, 14),
            ),
            CallNode("println", listOf(Identifier("a", Position(2, 9))), Position(2, 1)),
        )
        assertEquals(expected, result)
    }

    @Test
    fun sumAssignation() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("1", TokenType.NUMBER_LITERAL, Position(1, 16)),
            Token("+", TokenType.PLUS, Position(1, 17)),
            Token("2", TokenType.NUMBER_LITERAL, Position(1, 19)),
            Token(";", TokenType.SEMICOLON, Position(1, 20)),
        )

        val parser = Parser(tokens.listIterator())
        val result = parser.parseExpressions().toList()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                BinaryNode(
                    Literal(1, Position(1, 16)),
                    Token("+", TokenType.PLUS, Position(1, 17)),
                    Literal(2, Position(1, 19)),
                    Position(1, 17),
                ),
                Position(1, 14),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testMultiplication() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("1", TokenType.NUMBER_LITERAL, Position(1, 16)),
            Token("*", TokenType.ASTERISK, Position(1, 17)),
            Token("2", TokenType.NUMBER_LITERAL, Position(1, 19)),
            Token(";", TokenType.SEMICOLON, Position(1, 20)),
        )

        val parser = Parser(tokens.listIterator())
        val result = parser.parseExpressions().toList()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                BinaryNode(
                    Literal(1, Position(1, 16)),
                    Token("*", TokenType.ASTERISK, Position(1, 17)),
                    Literal(2, Position(1, 19)),
                    Position(1, 17),
                ),
                Position(1, 14),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testMultiplicationAndSum() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("1", TokenType.NUMBER_LITERAL, Position(1, 16)),
            Token("*", TokenType.ASTERISK, Position(1, 17)),
            Token("2", TokenType.NUMBER_LITERAL, Position(1, 19)),
            Token("+", TokenType.PLUS, Position(1, 20)),
            Token("3", TokenType.NUMBER_LITERAL, Position(1, 22)),
            Token(";", TokenType.SEMICOLON, Position(1, 23)),
        )

        val parser = Parser(tokens.listIterator())
        val result = parser.parseExpressions().toList()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                BinaryNode(
                    BinaryNode(
                        Literal(1, Position(1, 16)),
                        Token("*", TokenType.ASTERISK, Position(1, 17)),
                        Literal(2, Position(1, 19)),
                        Position(1, 17),
                    ),
                    Token("+", TokenType.PLUS, Position(1, 20)),
                    Literal(3, Position(1, 22)),
                    Position(1, 20),
                ),
                Position(1, 14),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testSumAndMultiplication() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("1", TokenType.NUMBER_LITERAL, Position(1, 16)),
            Token("+", TokenType.PLUS, Position(1, 17)),
            Token("2", TokenType.NUMBER_LITERAL, Position(1, 19)),
            Token("*", TokenType.ASTERISK, Position(1, 20)),
            Token("3", TokenType.NUMBER_LITERAL, Position(1, 22)),
            Token(";", TokenType.SEMICOLON, Position(1, 23)),
        )

        val parser = Parser(tokens.listIterator())
        val result = parser.parseExpressions().toList()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                BinaryNode(
                    Literal(1, Position(1, 16)),
                    Token("+", TokenType.PLUS, Position(1, 17)),
                    BinaryNode(
                        Literal(2, Position(1, 19)),
                        Token("*", TokenType.ASTERISK, Position(1, 20)),
                        Literal(3, Position(1, 22)),
                        Position(1, 20),
                    ),
                    Position(1, 17),
                ),
                Position(1, 14),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testTypeMismatch() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("string", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("1", TokenType.NUMBER_LITERAL, Position(1, 16)),
            Token("*", TokenType.ASTERISK, Position(1, 17)),
            Token("2", TokenType.NUMBER_LITERAL, Position(1, 19)),
            Token("+", TokenType.PLUS, Position(1, 20)),
            Token("3", TokenType.NUMBER_LITERAL, Position(1, 22)),
            Token(";", TokenType.SEMICOLON, Position(1, 23)),
        )

        val parser = Parser(tokens.listIterator())
        val error = assertFailsWith<ParseException> {
            parser.parseExpressions().toList()
        }

        println(error.message!!)
    }

    @Test
    fun testFunctionCall() {
        val tokens = listOf(
            Token("println", TokenType.CALL_FUNC, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 8)),
            Token("a", TokenType.IDENTIFIER, Position(1, 9)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 10)),
            Token(";", TokenType.SEMICOLON, Position(1, 11)),
        )

        val parser = Parser(tokens.listIterator())
        val result = parser.parseExpressions().toList()
        val expected = listOf(
            CallNode("println", listOf(Identifier("a", Position(1, 9))), Position(1, 1)),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testFunctionCallFail() {
        val tokens = listOf(
            Token("println", TokenType.CALL_FUNC, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 8)),
            Token("a", TokenType.IDENTIFIER, Position(1, 9)),
            Token(";", TokenType.SEMICOLON, Position(1, 11)),
        )

        val parser = Parser(tokens.listIterator())
        val error = assertFailsWith<ParseException> {
            parser.parseExpressions().toList()
        }
        assertEquals(
            error.message,
            "Syntax Error at 1, 11" +
                " Expected right parenthesis",
        )
    }
}

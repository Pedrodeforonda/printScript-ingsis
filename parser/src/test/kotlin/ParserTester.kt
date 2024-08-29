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

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testAssignation() {
        val tokens = listOf(
            Token("a", TokenType.IDENTIFIER, Position(1, 1)),
            Token("=", TokenType.ASSIGNATION, Position(1, 3)),
            Token("1", TokenType.NUMBER_LITERAL, Position(1, 5)),
            Token(";", TokenType.SEMICOLON, Position(1, 6)),
        )

        val parser = Parser(tokens)

        val error = assertFailsWith<ParseException> {
            val result = parser.parseExpressions()
        }

        println("Error Message: ${error.message}")
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

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD),
            Assignation(Identifier("a"), Literal(1)),
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

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD), Literal(1)),
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

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD), Literal(1)),
            CallNode("println", listOf(Identifier("a"))),
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

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD),
                BinaryNode(Literal(1), Token("+", TokenType.PLUS, Position(1, 17)), Literal(2)),
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

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD),
                BinaryNode(Literal(1), Token("*", TokenType.ASTERISK, Position(1, 17)), Literal(2)),
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

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD),
                BinaryNode(
                    BinaryNode(Literal(1), Token("*", TokenType.ASTERISK, Position(1, 17)), Literal(2)),
                    Token("+", TokenType.PLUS, Position(1, 20)),
                    Literal(3),
                ),
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

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD),
                BinaryNode(
                    Literal(1),
                    Token("+", TokenType.PLUS, Position(1, 17)),
                    BinaryNode(Literal(2), Token("*", TokenType.ASTERISK, Position(1, 20)), Literal(3)),
                ),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testErrorDeclaration() {
        val tokens = listOf(
            // Tokens para la primera línea: a let: number = 5;
            Token("a", TokenType.IDENTIFIER, Position(1, 1)),
            Token("let", TokenType.LET_KEYWORD, Position(1, 3)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("5", TokenType.NUMBER_LITERAL, Position(1, 16)),
            Token(";", TokenType.SEMICOLON, Position(1, 17)),

            // Tokens para la segunda línea: let b number = 4;
            Token("let", TokenType.LET_KEYWORD, Position(2, 1)),
            Token("b", TokenType.IDENTIFIER, Position(2, 5)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 7)), // Error: Falta ":"
            Token("=", TokenType.ASSIGNATION, Position(2, 14)),
            Token("4", TokenType.NUMBER_LITERAL, Position(2, 16)),
            Token(";", TokenType.SEMICOLON, Position(2, 17)),

            // Tokens para la tercera línea: let c: numer = 3;
            Token("let", TokenType.LET_KEYWORD, Position(3, 1)),
            Token("c", TokenType.IDENTIFIER, Position(3, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(3, 6)),
            Token("numer", TokenType.IDENTIFIER, Position(3, 8)), // Error: Typo en "number"
            Token("=", TokenType.ASSIGNATION, Position(3, 14)),
            Token("3", TokenType.NUMBER_LITERAL, Position(3, 16)),
            Token(";", TokenType.SEMICOLON, Position(3, 17)),

            // Tokens para la cuarta línea: a = 3 + 84 + "hey";
            Token("a", TokenType.IDENTIFIER, Position(4, 1)),
            Token("=", TokenType.ASSIGNATION, Position(4, 3)),
            Token("3", TokenType.NUMBER_LITERAL, Position(4, 5)),
            Token("+", TokenType.PLUS, Position(4, 6)),
            Token("84", TokenType.NUMBER_LITERAL, Position(4, 8)),
            Token("+", TokenType.PLUS, Position(4, 10)),
            Token("hey", TokenType.STRING_LITERAL, Position(4, 12)), // Error: Asignación de número a string
            Token(";", TokenType.SEMICOLON, Position(4, 17)),

            // Tokens para la quinta línea: println("Result: " + a);
            Token("println", TokenType.CALL_FUNC, Position(5, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(5, 8)),
            Token("Result:", TokenType.STRING_LITERAL, Position(5, 9)),
            Token("+", TokenType.PLUS, Position(5, 18)),
            Token("a", TokenType.IDENTIFIER, Position(5, 20)),
            Token(")", TokenType.RIGHT_PAREN, Position(5, 21)),
            Token(";", TokenType.SEMICOLON, Position(5, 22)),
        )

        val parser = Parser(tokens)
        val exception = assertFailsWith<ParseException> {
            parser.parseExpressions()
        }

        println("Error message: ${exception.message}")
    }

    @Test
    fun testErrorType() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("5", TokenType.NUMBER_LITERAL, Position(1, 16)),
            Token(";", TokenType.SEMICOLON, Position(1, 17)),

            Token("let", TokenType.LET_KEYWORD, Position(2, 1)),
            Token("b", TokenType.IDENTIFIER, Position(2, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 8)),
            Token("=", TokenType.ASSIGNATION, Position(2, 14)),
            Token("4", TokenType.NUMBER_LITERAL, Position(2, 16)),
            Token(";", TokenType.SEMICOLON, Position(2, 17)),

            Token("a", TokenType.IDENTIFIER, Position(3, 1)),
            Token("=", TokenType.ASSIGNATION, Position(3, 3)),
            Token("3", TokenType.NUMBER_LITERAL, Position(3, 5)),
            Token("+", TokenType.PLUS, Position(3, 6)),
            Token("84", TokenType.NUMBER_LITERAL, Position(3, 8)),
            Token("+", TokenType.PLUS, Position(3, 10)),
            Token("hey", TokenType.STRING_LITERAL, Position(3, 12)),
            Token(";", TokenType.SEMICOLON, Position(3, 17)),

            Token("println", TokenType.CALL_FUNC, Position(4, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(4, 8)),
            Token("Result:", TokenType.STRING_LITERAL, Position(4, 9)),
            Token("+", TokenType.PLUS, Position(4, 18)),
            Token("a", TokenType.IDENTIFIER, Position(4, 20)),
            Token(")", TokenType.RIGHT_PAREN, Position(4, 21)),
            Token(";", TokenType.SEMICOLON, Position(4, 22)),
        )

        val parser = Parser(tokens)
        val exception = assertFailsWith<ParseException> {
            parser.parseExpressions()
        }

        println("Error message: ${exception.message}")
    }

    @Test
    fun testErrorMultiply() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("string", TokenType.STRING_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("hola", TokenType.STRING_LITERAL, Position(1, 16)),
            Token(";", TokenType.SEMICOLON, Position(1, 17)),

            Token("let", TokenType.LET_KEYWORD, Position(2, 1)),
            Token("b", TokenType.IDENTIFIER, Position(2, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 8)),
            Token("=", TokenType.ASSIGNATION, Position(2, 14)),
            Token("4", TokenType.NUMBER_LITERAL, Position(2, 16)),
            Token(";", TokenType.SEMICOLON, Position(2, 17)),

            Token("a", TokenType.IDENTIFIER, Position(3, 1)),
            Token("=", TokenType.ASSIGNATION, Position(3, 3)),
            Token("hey", TokenType.STRING_LITERAL, Position(3, 5)),
            Token("*", TokenType.ASTERISK, Position(3, 9)),
            Token("3", TokenType.NUMBER_LITERAL, Position(3, 11)),
            Token(";", TokenType.SEMICOLON, Position(3, 12)),

            Token("println", TokenType.CALL_FUNC, Position(4, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(4, 8)),
            Token("Result:", TokenType.STRING_LITERAL, Position(4, 9)),
            Token("+", TokenType.PLUS, Position(4, 18)),
            Token("a", TokenType.IDENTIFIER, Position(4, 20)),
            Token(")", TokenType.RIGHT_PAREN, Position(4, 21)),
            Token(";", TokenType.SEMICOLON, Position(4, 22)),
        )

        val parser = Parser(tokens)
        val exception = assertFailsWith<ParseException> {
            parser.parseExpressions()
        }
        println("Error message: ${exception.message}")
    }

    @Test
    fun testPrintlnWithMultipleArguments() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("5", TokenType.NUMBER_LITERAL, Position(1, 16)),
            Token(";", TokenType.SEMICOLON, Position(1, 17)),

            Token("let", TokenType.LET_KEYWORD, Position(2, 1)),
            Token("b", TokenType.IDENTIFIER, Position(2, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 8)),
            Token("=", TokenType.ASSIGNATION, Position(2, 14)),
            Token("4", TokenType.NUMBER_LITERAL, Position(2, 16)),
            Token(";", TokenType.SEMICOLON, Position(2, 17)),

            Token("println", TokenType.CALL_FUNC, Position(3, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(3, 8)),
            Token("Result:", TokenType.STRING_LITERAL, Position(3, 9)),
            Token("+", TokenType.PLUS, Position(3, 18)),
            Token("a", TokenType.IDENTIFIER, Position(3, 20)),
            Token(",", TokenType.COMMA, Position(3, 21)),
            Token("b", TokenType.IDENTIFIER, Position(3, 23)),
            Token(")", TokenType.RIGHT_PAREN, Position(3, 24)),
            Token(";", TokenType.SEMICOLON, Position(3, 25)),
        )

        val parser = Parser(tokens)
        val exception = assertFailsWith<ParseException> {
            parser.parseExpressions()
        }
        println("Error message: ${exception.message}")
    }
}

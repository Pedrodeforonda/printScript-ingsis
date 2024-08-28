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
            Token("let", TokenType.LET_KEYWORD),
            Token("a", TokenType.IDENTIFIER),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("number", TokenType.NUMBER_TYPE),
            Token(";", TokenType.SEMICOLON),
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
            Token("a", TokenType.IDENTIFIER),
            Token("=", TokenType.ASSIGNATION),
            Token("1", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),
        )

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(Identifier("a"), Literal(1)),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testAssignationAndDeclaration() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD),
            Token("a", TokenType.IDENTIFIER),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("number", TokenType.NUMBER_TYPE),
            Token(";", TokenType.SEMICOLON),
            Token("a", TokenType.IDENTIFIER),
            Token("=", TokenType.ASSIGNATION),
            Token("1", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),
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
            Token("let", TokenType.LET_KEYWORD),
            Token("a", TokenType.IDENTIFIER),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("number", TokenType.NUMBER_TYPE),
            Token("=", TokenType.ASSIGNATION),
            Token("1", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),
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
            Token("let", TokenType.LET_KEYWORD),
            Token("a", TokenType.IDENTIFIER),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("number", TokenType.NUMBER_TYPE),
            Token("=", TokenType.ASSIGNATION),
            Token("1", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),
            Token("println", TokenType.CALL_FUNC),
            Token("(", TokenType.LEFT_PAREN),
            Token("a", TokenType.IDENTIFIER),
            Token(")", TokenType.RIGHT_PAREN),
            Token(";", TokenType.SEMICOLON),
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
            Token("let", TokenType.LET_KEYWORD),
            Token("a", TokenType.IDENTIFIER),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("number", TokenType.NUMBER_TYPE),
            Token("=", TokenType.ASSIGNATION),
            Token("1", TokenType.NUMBER_LITERAL),
            Token("+", TokenType.PLUS),
            Token("2", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),
        )

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD),
                BinaryNode(Literal(1), Token("+", TokenType.PLUS), Literal(2)),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testMultiplication() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD),
            Token("a", TokenType.IDENTIFIER),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("number", TokenType.NUMBER_TYPE),
            Token("=", TokenType.ASSIGNATION),
            Token("1", TokenType.NUMBER_LITERAL),
            Token("*", TokenType.ASTERISK),
            Token("2", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),
        )

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD),
                BinaryNode(Literal(1), Token("*", TokenType.ASTERISK), Literal(2)),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testMultiplicationAndSum() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD),
            Token("a", TokenType.IDENTIFIER),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("number", TokenType.NUMBER_TYPE),
            Token("=", TokenType.ASSIGNATION),
            Token("1", TokenType.NUMBER_LITERAL),
            Token("*", TokenType.ASTERISK),
            Token("2", TokenType.NUMBER_LITERAL),
            Token("+", TokenType.PLUS),
            Token("3", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),
        )

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD),
                BinaryNode(
                    BinaryNode(Literal(1), Token("*", TokenType.ASTERISK), Literal(2)),
                    Token("+", TokenType.PLUS),
                    Literal(3),
                ),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testSumAndMultiplication() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD),
            Token("a", TokenType.IDENTIFIER),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("number", TokenType.NUMBER_TYPE),
            Token("=", TokenType.ASSIGNATION),
            Token("1", TokenType.NUMBER_LITERAL),
            Token("+", TokenType.PLUS),
            Token("2", TokenType.NUMBER_LITERAL),
            Token("*", TokenType.ASTERISK),
            Token("3", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),
        )

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD),
                BinaryNode(
                    Literal(1),
                    Token("+", TokenType.PLUS),
                    BinaryNode(Literal(2), Token("*", TokenType.ASTERISK), Literal(3)),
                ),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testErrorDeclaration() {
        val tokens = listOf(
            // Tokens para la primera línea: a let: number = 5;
            Token("a", TokenType.IDENTIFIER),
            Token("let", TokenType.LET_KEYWORD),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("number", TokenType.NUMBER_TYPE),
            Token("=", TokenType.ASSIGNATION),
            Token("5", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),

            // Tokens para la segunda línea: let b number = 4;
            Token("let", TokenType.LET_KEYWORD),
            Token("b", TokenType.IDENTIFIER),
            Token("number", TokenType.NUMBER_TYPE), // Error: Falta ":"
            Token("=", TokenType.ASSIGNATION),
            Token("4", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),

            // Tokens para la tercera línea: let c: numer = 3;
            Token("let", TokenType.LET_KEYWORD),
            Token("c", TokenType.IDENTIFIER),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("numer", TokenType.IDENTIFIER), // Error: Typo en "number"
            Token("=", TokenType.ASSIGNATION),
            Token("3", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),

            // Tokens para la cuarta línea: a = 3 + 84 + "hey";
            Token("a", TokenType.IDENTIFIER),
            Token("=", TokenType.ASSIGNATION),
            Token("3", TokenType.NUMBER_LITERAL),
            Token("+", TokenType.PLUS),
            Token("84", TokenType.NUMBER_LITERAL),
            Token("+", TokenType.PLUS),
            Token("hey", TokenType.STRING_LITERAL), // Error: Asignación de número a string
            Token(";", TokenType.SEMICOLON),

            // Tokens para la quinta línea: println("Result: " + a);
            Token("println", TokenType.CALL_FUNC),
            Token("(", TokenType.LEFT_PAREN),
            Token("Result:", TokenType.STRING_LITERAL),
            Token("+", TokenType.PLUS),
            Token("a", TokenType.IDENTIFIER),
            Token(")", TokenType.RIGHT_PAREN),
            Token(";", TokenType.SEMICOLON),
        )

        val parser = Parser(tokens)
        assertFailsWith<ParseException> {
            parser.parseExpressions()
        }
    }
}

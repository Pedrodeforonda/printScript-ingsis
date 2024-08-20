import nodes.Assignation
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

public class ParserTester {
    @Test
    fun testDeclaration() {
        val tokens = listOf(
            Token("let".toCharArray(), TokenType.LET_KEYWORD),
            Token("a".toCharArray(), TokenType.IDENTIFIER),
            Token(":".toCharArray(), TokenType.TYPE_ASSIGNATION),
            Token("number".toCharArray(), TokenType.NUMBER_TYPE),
            Token(";".toCharArray(), TokenType.SEMICOLON),
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
            Token("a".toCharArray(), TokenType.IDENTIFIER),
            Token("=".toCharArray(), TokenType.ASSIGNATION),
            Token("1".toCharArray(), TokenType.NUMBER_LITERAL),
            Token(";".toCharArray(), TokenType.SEMICOLON),
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
            Token("let".toCharArray(), TokenType.LET_KEYWORD),
            Token("a".toCharArray(), TokenType.IDENTIFIER),
            Token(":".toCharArray(), TokenType.TYPE_ASSIGNATION),
            Token("number".toCharArray(), TokenType.NUMBER_TYPE),
            Token(";".toCharArray(), TokenType.SEMICOLON),
            Token("a".toCharArray(), TokenType.IDENTIFIER),
            Token("=".toCharArray(), TokenType.ASSIGNATION),
            Token("1".toCharArray(), TokenType.NUMBER_LITERAL),
            Token(";".toCharArray(), TokenType.SEMICOLON),
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
            Token("let".toCharArray(), TokenType.LET_KEYWORD),
            Token("a".toCharArray(), TokenType.IDENTIFIER),
            Token(":".toCharArray(), TokenType.TYPE_ASSIGNATION),
            Token("number".toCharArray(), TokenType.NUMBER_TYPE),
            Token("=".toCharArray(), TokenType.ASSIGNATION),
            Token("1".toCharArray(), TokenType.NUMBER_LITERAL),
            Token(";".toCharArray(), TokenType.SEMICOLON),
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
            Token("let".toCharArray(), TokenType.LET_KEYWORD),
            Token("a".toCharArray(), TokenType.IDENTIFIER),
            Token(":".toCharArray(), TokenType.TYPE_ASSIGNATION),
            Token("number".toCharArray(), TokenType.NUMBER_TYPE),
            Token("=".toCharArray(), TokenType.ASSIGNATION),
            Token("1".toCharArray(), TokenType.NUMBER_LITERAL),
            Token(";".toCharArray(), TokenType.SEMICOLON),
            Token("println".toCharArray(), TokenType.CALL_FUNC),
            Token("(".toCharArray(), TokenType.LEFT_PAREN),
            Token("a".toCharArray(), TokenType.IDENTIFIER),
            Token(")".toCharArray(), TokenType.RIGHT_PAREN),
            Token(";".toCharArray(), TokenType.SEMICOLON),
        )

        val parser = Parser(tokens)
        val result = parser.parseExpressions()
        val expected = listOf(
            Assignation(Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD), Literal(1)),
            CallNode("println", listOf(Identifier("a"))),
        )
        assertEquals(expected, result)
    }
}

import main.ParseException
import main.Parser
import main.ParserFactory
import main.Position
import main.Token
import main.TokenType
import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.IfNode
import nodes.Literal
import nodes.Node
import nodes.ReadEnv
import nodes.ReadInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.DeclarationKeyWord
import kotlin.test.assertTrue

public class ParserTester {

    fun createParser1(tokens: List<Token>): Parser {
        return ParserFactory().createParser("1.0", tokens.listIterator())
    }

    fun createParser2(tokens: List<Token>): Parser {
        return ParserFactory().createParser("1.1", tokens.listIterator())
    }

    @Test
    fun testDeclaration() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token(";", TokenType.SEMICOLON, Position(1, 14)),
        )

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
        }
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

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
        }
        val expected = listOf(
            Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
            Assignation(
                Identifier("a", Position(1, 5)),
                Literal(1, Position(2, 1), TokenType.NUMBER_LITERAL),
                Position(2, 3),
            ),
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

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
        }
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                Literal(1, Position(1, 16), TokenType.NUMBER_LITERAL),
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

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
        }
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                Literal(1, Position(1, 16), TokenType.NUMBER_LITERAL),
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

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
        }
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                BinaryNode(
                    Literal(1, Position(1, 16), TokenType.NUMBER_LITERAL),
                    Token("+", TokenType.PLUS, Position(1, 17)),
                    Literal(2, Position(1, 19), TokenType.NUMBER_LITERAL),
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

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
        }
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                BinaryNode(
                    Literal(1, Position(1, 16), TokenType.NUMBER_LITERAL),
                    Token("*", TokenType.ASTERISK, Position(1, 17)),
                    Literal(2, Position(1, 19), TokenType.NUMBER_LITERAL),
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

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
        }
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                BinaryNode(
                    BinaryNode(
                        Literal(1, Position(1, 16), TokenType.NUMBER_LITERAL),
                        Token("*", TokenType.ASTERISK, Position(1, 17)),
                        Literal(2, Position(1, 19), TokenType.NUMBER_LITERAL),
                        Position(1, 17),
                    ),
                    Token("+", TokenType.PLUS, Position(1, 20)),
                    Literal(3, Position(1, 22), TokenType.NUMBER_LITERAL),
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

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
        }
        val expected = listOf(
            Assignation(
                Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                BinaryNode(
                    Literal(1, Position(1, 16), TokenType.NUMBER_LITERAL),
                    Token("+", TokenType.PLUS, Position(1, 17)),
                    BinaryNode(
                        Literal(2, Position(1, 19), TokenType.NUMBER_LITERAL),
                        Token("*", TokenType.ASTERISK, Position(1, 20)),
                        Literal(3, Position(1, 22), TokenType.NUMBER_LITERAL),
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

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) {
                result.add(results.getAst())
            } else errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
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

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
        }
        val expected = listOf(
            CallNode("println", listOf(Identifier("a", Position(1, 9))), Position(1, 1)),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testNoTrailingSemiColon() {
        val tokens = listOf(
            Token("println", TokenType.CALL_FUNC, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 8)),
            Token("a", TokenType.IDENTIFIER, Position(1, 9)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 10)),
            Token("println", TokenType.CALL_FUNC, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 8)),
            Token("a", TokenType.IDENTIFIER, Position(1, 9)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 10)),
            Token(";", TokenType.SEMICOLON, Position(1, 11)),

        )

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
    }

    @Test
    fun testFunctionCallFail() {
        val tokens = listOf(
            Token("println", TokenType.CALL_FUNC, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 8)),
            Token("a", TokenType.IDENTIFIER, Position(1, 9)),
            Token(";", TokenType.SEMICOLON, Position(1, 11)),
        )

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) {
                result.add(results.getAst())
            } else errors.add(results.getError())
        }
        val error = errors[0] as ParseException
        assertEquals(
            error.message,
            "Syntax Error at 1, 11" +
                " Expected right parenthesis",
        )
    }

    @Test
    fun testBooleanLiteral() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("boolean", TokenType.BOOLEAN_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 16)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 18)),
            Token(";", TokenType.SEMICOLON, Position(1, 22)),
        )

        val parser = createParser2(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) println(results.getError())
        }
        val expected = listOf(
            Assignation(
                Declaration("a", "boolean", DeclarationKeyWord.LET_KEYWORD, Position(1, 1)),
                Literal(true, Position(1, 18), TokenType.BOOLEAN_LITERAL),
                Position(1, 18),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun testIf() {
        val tokens = listOf(
            Token("if", TokenType.IF_KEYWORD, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 3)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 4)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 8)),
            Token("{", TokenType.LEFT_BRACE, Position(1, 10)),
            Token("let", TokenType.LET_KEYWORD, Position(2, 2)),
            Token("a", TokenType.IDENTIFIER, Position(2, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 9)),
            Token("=", TokenType.ASSIGNATION, Position(2, 15)),
            Token("1", TokenType.NUMBER_LITERAL, Position(2, 17)),
            Token(";", TokenType.SEMICOLON, Position(2, 18)),
            Token("}", TokenType.RIGHT_BRACE, Position(3, 1)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) println(results.getError())
        }

        val expected = listOf(
            IfNode(
                Literal(true, Position(1, 4), TokenType.BOOLEAN_LITERAL),
                listOf(
                    Assignation(
                        Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(2, 2)),
                        Literal(1, Position(2, 17), TokenType.NUMBER_LITERAL),
                        Position(2, 17),
                    ),
                ),
                listOf(),
                Position(1, 1),
            ),
        )

        assertEquals(expected, result)
    }

    @Test
    fun testIfFalse() {
        val tokens = listOf(
            Token("if", TokenType.IF_KEYWORD, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 3)),
            Token("false", TokenType.BOOLEAN_LITERAL, Position(1, 4)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 8)),
            Token("{", TokenType.LEFT_BRACE, Position(1, 10)),
            Token("let", TokenType.LET_KEYWORD, Position(2, 2)),
            Token("a", TokenType.IDENTIFIER, Position(2, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 9)),
            Token("=", TokenType.ASSIGNATION, Position(2, 15)),
            Token("1", TokenType.NUMBER_LITERAL, Position(2, 17)),
            Token(";", TokenType.SEMICOLON, Position(2, 18)),
            Token("}", TokenType.RIGHT_BRACE, Position(3, 1)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) println(results.getError())
        }

        val expected = listOf(
            IfNode(
                Literal(false, Position(1, 4), TokenType.BOOLEAN_LITERAL),
                listOf(
                    Assignation(
                        Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(2, 2)),
                        Literal(1, Position(2, 17), TokenType.NUMBER_LITERAL),
                        Position(2, 17),
                    ),
                ),
                listOf(),
                Position(1, 1),
            ),
        )

        assertEquals(expected, result)
    }

    @Test
    fun testIfElse() {
        val tokens = listOf(
            Token("if", TokenType.IF_KEYWORD, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 3)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 4)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 8)),
            Token("{", TokenType.LEFT_BRACE, Position(1, 10)),
            Token("let", TokenType.LET_KEYWORD, Position(2, 2)),
            Token("a", TokenType.IDENTIFIER, Position(2, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 9)),
            Token("=", TokenType.ASSIGNATION, Position(2, 15)),
            Token("1", TokenType.NUMBER_LITERAL, Position(2, 17)),
            Token(";", TokenType.SEMICOLON, Position(2, 18)),
            Token("}", TokenType.RIGHT_BRACE, Position(3, 1)),
            Token("else", TokenType.ELSE_KEYWORD, Position(3, 3)),
            Token("{", TokenType.LEFT_BRACE, Position(3, 8)),
            Token("let", TokenType.LET_KEYWORD, Position(4, 2)),
            Token("a", TokenType.IDENTIFIER, Position(4, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(4, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(4, 9)),
            Token("=", TokenType.ASSIGNATION, Position(4, 15)),
            Token("2", TokenType.NUMBER_LITERAL, Position(4, 17)),
            Token(";", TokenType.SEMICOLON, Position(4, 18)),
            Token("println", TokenType.CALL_FUNC, Position(5, 2)),
            Token("(", TokenType.LEFT_PAREN, Position(5, 9)),
            Token("a", TokenType.IDENTIFIER, Position(5, 10)),
            Token(")", TokenType.RIGHT_PAREN, Position(5, 11)),
            Token(";", TokenType.SEMICOLON, Position(5, 12)),
            Token("}", TokenType.RIGHT_BRACE, Position(5, 1)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) println(results.getError())
        }

        val expected = listOf(
            IfNode(
                Literal(true, Position(1, 4), TokenType.BOOLEAN_LITERAL),
                listOf(
                    Assignation(
                        Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(2, 2)),
                        Literal(1, Position(2, 17), TokenType.NUMBER_LITERAL),
                        Position(2, 17),
                    ),
                ),
                listOf(
                    Assignation(
                        Declaration("a", "number", DeclarationKeyWord.LET_KEYWORD, Position(4, 2)),
                        Literal(2, Position(4, 17), TokenType.NUMBER_LITERAL),
                        Position(4, 17),
                    ),
                    CallNode("println", listOf(Identifier("a", Position(5, 10))), Position(5, 2)),
                ),
                Position(1, 1),
            ),
        )

        assertEquals(expected, result)
    }

    @Test
    fun testIfFailing() {
        val tokens = listOf(
            Token("if", TokenType.IF_KEYWORD, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 3)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 4)),
            Token("{", TokenType.LEFT_BRACE, Position(1, 10)),
            Token("let", TokenType.LET_KEYWORD, Position(2, 2)),
            Token("a", TokenType.IDENTIFIER, Position(2, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 9)),
            Token("=", TokenType.ASSIGNATION, Position(2, 15)),
            Token("1", TokenType.NUMBER_LITERAL, Position(2, 17)),
            Token(";", TokenType.SEMICOLON, Position(2, 18)),
            Token("}", TokenType.RIGHT_BRACE, Position(3, 1)),
            Token("else", TokenType.ELSE_KEYWORD, Position(3, 3)),
            Token("{", TokenType.LEFT_BRACE, Position(3, 8)),
            Token("let", TokenType.LET_KEYWORD, Position(4, 2)),
            Token("a", TokenType.IDENTIFIER, Position(4, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(4, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(4, 9)),
            Token("=", TokenType.ASSIGNATION, Position(4, 15)),
            Token("2", TokenType.NUMBER_LITERAL, Position(4, 17)),
            Token(";", TokenType.SEMICOLON, Position(4, 18)),
            Token("println", TokenType.CALL_FUNC, Position(5, 2)),
            Token("(", TokenType.LEFT_PAREN, Position(5, 9)),
            Token("a", TokenType.IDENTIFIER, Position(5, 10)),
            Token(")", TokenType.RIGHT_PAREN, Position(5, 11)),
            Token(";", TokenType.SEMICOLON, Position(5, 12)),
            Token("}", TokenType.RIGHT_BRACE, Position(5, 1)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
    }

    @Test
    fun testIfNoLeftBrace() {
        val tokens = listOf(
            Token("if", TokenType.IF_KEYWORD, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 3)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 4)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 8)),
            Token("let", TokenType.LET_KEYWORD, Position(2, 2)),
            Token("a", TokenType.IDENTIFIER, Position(2, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 9)),
            Token("=", TokenType.ASSIGNATION, Position(2, 15)),
            Token("1", TokenType.NUMBER_LITERAL, Position(2, 17)),
            Token(";", TokenType.SEMICOLON, Position(2, 18)),
            Token("}", TokenType.RIGHT_BRACE, Position(3, 1)),
            Token("else", TokenType.ELSE_KEYWORD, Position(3, 3)),
            Token("{", TokenType.LEFT_BRACE, Position(3, 8)),
            Token("let", TokenType.LET_KEYWORD, Position(4, 2)),
            Token("a", TokenType.IDENTIFIER, Position(4, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(4, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(4, 9)),
            Token("=", TokenType.ASSIGNATION, Position(4, 15)),
            Token("2", TokenType.NUMBER_LITERAL, Position(4, 17)),
            Token(";", TokenType.SEMICOLON, Position(4, 18)),
            Token("println", TokenType.CALL_FUNC, Position(5, 2)),
            Token("(", TokenType.LEFT_PAREN, Position(5, 9)),
            Token("a", TokenType.IDENTIFIER, Position(5, 10)),
            Token(")", TokenType.RIGHT_PAREN, Position(5, 11)),
            Token(";", TokenType.SEMICOLON, Position(5, 12)),
            Token("}", TokenType.RIGHT_BRACE, Position(5, 1)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
    }

    @Test
    fun testIfNoLeftBrace2() {
        val tokens = listOf(
            Token("if", TokenType.IF_KEYWORD, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 3)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 4)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 8)),
            Token("{", TokenType.LEFT_BRACE, Position(1, 10)),
            Token("let", TokenType.LET_KEYWORD, Position(2, 2)),
            Token("a", TokenType.IDENTIFIER, Position(2, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 9)),
            Token("=", TokenType.ASSIGNATION, Position(2, 15)),
            Token("1", TokenType.NUMBER_LITERAL, Position(2, 17)),
            Token(";", TokenType.SEMICOLON, Position(2, 18)),
            Token("}", TokenType.RIGHT_BRACE, Position(3, 1)),
            Token("else", TokenType.ELSE_KEYWORD, Position(3, 3)),
            Token("let", TokenType.LET_KEYWORD, Position(4, 2)),
            Token("a", TokenType.IDENTIFIER, Position(4, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(4, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(4, 9)),
            Token("=", TokenType.ASSIGNATION, Position(4, 15)),
            Token("2", TokenType.NUMBER_LITERAL, Position(4, 17)),
            Token(";", TokenType.SEMICOLON, Position(4, 18)),
            Token("println", TokenType.CALL_FUNC, Position(5, 2)),
            Token("(", TokenType.LEFT_PAREN, Position(5, 9)),
            Token("a", TokenType.IDENTIFIER, Position(5, 10)),
            Token(")", TokenType.RIGHT_PAREN, Position(5, 11)),
            Token(";", TokenType.SEMICOLON, Position(5, 12)),
            Token("}", TokenType.RIGHT_BRACE, Position(5, 1)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
    }

    @Test
    fun testIfNoLeftParen() {
        val tokens = listOf(
            Token("if", TokenType.IF_KEYWORD, Position(1, 1)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 4)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 8)),
            Token("{", TokenType.LEFT_BRACE, Position(1, 10)),
            Token("let", TokenType.LET_KEYWORD, Position(2, 2)),
            Token("a", TokenType.IDENTIFIER, Position(2, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 9)),
            Token("=", TokenType.ASSIGNATION, Position(2, 15)),
            Token("1", TokenType.NUMBER_LITERAL, Position(2, 17)),
            Token(";", TokenType.SEMICOLON, Position(2, 18)),
            Token("}", TokenType.RIGHT_BRACE, Position(3, 1)),
            Token("else", TokenType.ELSE_KEYWORD, Position(3, 3)),
            Token("{", TokenType.LEFT_BRACE, Position(3, 8)),
            Token("let", TokenType.LET_KEYWORD, Position(4, 2)),
            Token("a", TokenType.IDENTIFIER, Position(4, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(4, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(4, 9)),
            Token("=", TokenType.ASSIGNATION, Position(4, 15)),
            Token("2", TokenType.NUMBER_LITERAL, Position(4, 17)),
            Token(";", TokenType.SEMICOLON, Position(4, 18)),
            Token("println", TokenType.CALL_FUNC, Position(5, 2)),
            Token("(", TokenType.LEFT_PAREN, Position(5, 9)),
            Token("a", TokenType.IDENTIFIER, Position(5, 10)),
            Token(")", TokenType.RIGHT_PAREN, Position(5, 11)),
            Token(";", TokenType.SEMICOLON, Position(5, 12)),
            Token("}", TokenType.RIGHT_BRACE, Position(5, 1)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
    }

    @Test
    fun testIfNoBool() {
        val tokens = listOf(
            Token("if", TokenType.IF_KEYWORD, Position(1, 1)),
            Token("2", TokenType.NUMBER_LITERAL, Position(1, 4)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 8)),
            Token("{", TokenType.LEFT_BRACE, Position(1, 10)),
            Token("let", TokenType.LET_KEYWORD, Position(2, 2)),
            Token("a", TokenType.IDENTIFIER, Position(2, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 9)),
            Token("=", TokenType.ASSIGNATION, Position(2, 15)),
            Token("1", TokenType.NUMBER_LITERAL, Position(2, 17)),
            Token(";", TokenType.SEMICOLON, Position(2, 18)),
            Token("}", TokenType.RIGHT_BRACE, Position(3, 1)),
            Token("else", TokenType.ELSE_KEYWORD, Position(3, 3)),
            Token("{", TokenType.LEFT_BRACE, Position(3, 8)),
            Token("let", TokenType.LET_KEYWORD, Position(4, 2)),
            Token("a", TokenType.IDENTIFIER, Position(4, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(4, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(4, 9)),
            Token("=", TokenType.ASSIGNATION, Position(4, 15)),
            Token("2", TokenType.NUMBER_LITERAL, Position(4, 17)),
            Token(";", TokenType.SEMICOLON, Position(4, 18)),
            Token("println", TokenType.CALL_FUNC, Position(5, 2)),
            Token("(", TokenType.LEFT_PAREN, Position(5, 9)),
            Token("a", TokenType.IDENTIFIER, Position(5, 10)),
            Token(")", TokenType.RIGHT_PAREN, Position(5, 11)),
            Token(";", TokenType.SEMICOLON, Position(5, 12)),
            Token("}", TokenType.RIGHT_BRACE, Position(5, 1)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
    }

    @Test
    fun testIfNoSemiColon() {
        val tokens = listOf(
            Token("if", TokenType.IF_KEYWORD, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 3)),
            Token("false", TokenType.BOOLEAN_LITERAL, Position(1, 4)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 8)),
            Token("{", TokenType.LEFT_BRACE, Position(1, 10)),
            Token("let", TokenType.LET_KEYWORD, Position(2, 2)),
            Token("a", TokenType.IDENTIFIER, Position(2, 6)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 7)),
            Token("number", TokenType.NUMBER_TYPE, Position(2, 9)),
            Token("=", TokenType.ASSIGNATION, Position(2, 15)),
            Token("1", TokenType.NUMBER_LITERAL, Position(2, 17)),
            Token("}", TokenType.RIGHT_BRACE, Position(3, 1)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
    }

    @Test
    fun readEnvTest() {
        val tokens = listOf(
            Token("read_env", TokenType.READ_ENV, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 9)),
            Token("DB_USER", TokenType.STRING_LITERAL, Position(1, 10)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 18)),
            Token(";", TokenType.SEMICOLON, Position(1, 19)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        val expected = listOf(
            ReadEnv("DB_USER", Position(1, 1)),
        )

        assertEquals(expected, result)
    }

    @Test
    fun readInputTest() {
        val tokens = listOf(
            Token("read_input", TokenType.READ_INPUT, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 11)),
            Token("insert Your Name", TokenType.STRING_LITERAL, Position(1, 12)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 30)),
            Token(";", TokenType.SEMICOLON, Position(1, 31)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        val expected = listOf(
            ReadInput(Literal("insert Your Name", Position(1, 12), TokenType.STRING_LITERAL), Position(1, 1)),
        )

        assertEquals(expected, result)
    }

    @Test
    fun readInputTestBoolFail() {
        val tokens = listOf(
            Token("read_input", TokenType.READ_INPUT, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 11)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 12)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 30)),
            Token(";", TokenType.SEMICOLON, Position(1, 31)),
        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        listOf(
            ReadInput(Literal("insert Your Name", Position(1, 12), TokenType.STRING_LITERAL), Position(1, 1)),
        )

        assertTrue { errors.size > 0 }
    }

    @Test
    fun readEnvSintaxErrors() {
        val tokens = listOf(
            Token("read_env", TokenType.READ_ENV, Position(1, 1)),
            Token("DB_USER", TokenType.STRING_LITERAL, Position(1, 10)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 18)),
            Token(";", TokenType.SEMICOLON, Position(1, 19)),
            Token("read_env", TokenType.READ_ENV, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 9)),
            Token("a", TokenType.IDENTIFIER, Position(1, 10)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 11)),
            Token(";", TokenType.SEMICOLON, Position(1, 12)),
            Token("read_env", TokenType.READ_ENV, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 9)),
            Token("DB_USER", TokenType.STRING_LITERAL, Position(1, 10)),
            Token(";", TokenType.SEMICOLON, Position(1, 18)),

        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
    }

    @Test
    fun readInputSintaxErrors() {
        val tokens = listOf(
            Token("read_input", TokenType.READ_INPUT, Position(1, 1)),
            Token("insert Your Name", TokenType.STRING_LITERAL, Position(1, 12)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 30)),
            Token(";", TokenType.SEMICOLON, Position(1, 31)),
            Token("read_input", TokenType.READ_INPUT, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 11)),
            Token("a", TokenType.IDENTIFIER, Position(1, 12)),
            Token(";", TokenType.SEMICOLON, Position(1, 13)),
            Token("read_input", TokenType.READ_INPUT, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 11)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 12)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 30)),
            Token(";", TokenType.SEMICOLON, Position(1, 31)),
            Token("read_input", TokenType.READ_INPUT, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 11)),
            Token("*", TokenType.ASTERISK, Position(1, 12)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 30)),
            Token(";", TokenType.SEMICOLON, Position(1, 31)),
            Token("read_input", TokenType.READ_INPUT, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 11)),
            Token("a", TokenType.IDENTIFIER, Position(1, 12)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 13)),
            Token(";", TokenType.SEMICOLON, Position(1, 14)),

        )

        val parser = createParser2(tokens)

        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
    }

    @Test
    fun testBoolOnPrevVersion() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("boolean", TokenType.BOOLEAN_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 16)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 18)),
            Token(";", TokenType.SEMICOLON, Position(1, 22)),
            Token("let", TokenType.LET_KEYWORD, Position(1, 24)),
            Token("b", TokenType.IDENTIFIER, Position(1, 28)),
            Token("boolean", TokenType.BOOLEAN_TYPE, Position(1, 31)),
            Token("=", TokenType.ASSIGNATION, Position(1, 39)),
            Token("false", TokenType.BOOLEAN_LITERAL, Position(1, 41)),
            Token(";", TokenType.SEMICOLON, Position(1, 46)),
            Token("let", TokenType.LET_KEYWORD, Position(1, 48)),
            Token("boolean", TokenType.BOOLEAN_TYPE, Position(1, 55)),
            Token("=", TokenType.ASSIGNATION, Position(1, 63)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 65)),
            Token(";", TokenType.SEMICOLON, Position(1, 69)),
        )

        val parser = createParser1(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
    }

    @Test
    fun testBoolOnCurrentVersion() {
        val tokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("boolean", TokenType.BOOLEAN_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 16)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 18)),
            Token(";", TokenType.SEMICOLON, Position(1, 22)),
            Token("let", TokenType.LET_KEYWORD, Position(1, 24)),
            Token("b", TokenType.IDENTIFIER, Position(1, 28)),
            Token("boolean", TokenType.BOOLEAN_TYPE, Position(1, 31)),
            Token("=", TokenType.ASSIGNATION, Position(1, 39)),
            Token("false", TokenType.BOOLEAN_LITERAL, Position(1, 41)),
            Token(";", TokenType.SEMICOLON, Position(1, 46)),
            Token("let", TokenType.LET_KEYWORD, Position(1, 48)),
            Token("boolean", TokenType.BOOLEAN_TYPE, Position(1, 55)),
            Token("=", TokenType.ASSIGNATION, Position(1, 63)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 65)),
            Token(";", TokenType.SEMICOLON, Position(1, 69)),
        )

        val parser = createParser2(tokens)
        val parsingResults = parser.parseExpressions().toList()
        val result = ArrayList<Node>()
        val errors = ArrayList<Exception>()
        for (results in parsingResults) {
            if (!results.hasError()) result.add(results.getAst())
            if (results.hasError()) errors.add(results.getError())
        }

        assertTrue { errors.size > 0 }
    }
}

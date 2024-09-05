import lexer.LexerFactory
import main.Position
import main.Token
import main.TokenType
import org.example.lexer.Lexer
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class LexerTest {

    @Test
    fun testTokenizeCodeLines() {
        val byteArr: ByteArray = Files.readAllBytes(
            Paths.get("src/test/resources/assigneNumber.txt"),
        )
        val text: String = byteArr.toString(Charsets.UTF_8)
        val bufferedReader: BufferedReader = text.reader().buffered()
        val lexer = LexerFactory().createLexer(bufferedReader, 1)
        val actualTokens = lexer.tokenizeAll(lexer).toList()

        val expectedTokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 15)),
            Token("12", TokenType.NUMBER_LITERAL, Position(1, 17)),
            Token(";", TokenType.SEMICOLON, Position(1, 19)),
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testTokenizeCodeLine2() {
        val byteArr: ByteArray = Files.readAllBytes(
            Paths.get("src/test/resources/assigneString.txt"),
        )
        val text: String = byteArr.toString(Charsets.UTF_8)
        val bufferedReader: BufferedReader = text.reader().buffered()
        val lexer = LexerFactory().createLexer(bufferedReader, 1)
        val actualTokens = lexer.tokenizeAll(lexer).toList()

        val expectedTokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("string", TokenType.STRING_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 15)),
            Token("monos", TokenType.STRING_LITERAL, Position(1, 17)),
            Token(";", TokenType.SEMICOLON, Position(1, 24)),
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testTokenizeCodeLine3() {
        val byteArr: ByteArray = Files.readAllBytes(
            Paths.get("src/test/resources/assigneOperation.txt"),
        )
        val text: String = byteArr.toString(Charsets.UTF_8)
        val bufferedReader: BufferedReader = text.reader().buffered()
        val lexer = LexerFactory().createLexer(bufferedReader, 1)
        val actualTokens = lexer.tokenizeAll(lexer).toList()

        val expectedTokens = listOf(
            Token("a", TokenType.IDENTIFIER, Position(1, 1)),
            Token("=", TokenType.ASSIGNATION, Position(1, 3)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token("/", TokenType.SLASH, Position(1, 7)),
            Token("b", TokenType.IDENTIFIER, Position(1, 9)),
            Token(";", TokenType.SEMICOLON, Position(1, 10)),
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testTokenizeCodeLine4() {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get("src/test/resources/println.txt"))
        val text: String = byteArr.toString(Charsets.UTF_8)
        val bufferedReader: BufferedReader = text.reader().buffered()
        val lexer = LexerFactory().createLexer(bufferedReader, 1)
        val actualTokens = lexer.tokenizeAll(lexer).toList()

        val expectedTokens = listOf(
            Token("println", TokenType.CALL_FUNC, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 8)),
            Token("a", TokenType.IDENTIFIER, Position(1, 9)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 10)),
            Token(";", TokenType.SEMICOLON, Position(1, 11)),
        )

        assertEquals(expectedTokens, actualTokens)
    }


    @Test
    fun testLexerMultipleLines() {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get("src/test/resources/multipleLines"))
        val text: String = byteArr.toString(Charsets.UTF_8)
        val bufferedReader: BufferedReader = text.reader().buffered()
        val lexer = LexerFactory().createLexer(bufferedReader, 1)
        val actualTokens = lexer.tokenizeAll(lexer).toList()

        val expectedTokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 15)),
            Token("12", TokenType.NUMBER_LITERAL, Position(1, 17)),
            Token(";", TokenType.SEMICOLON, Position(1, 19)),
            Token("let", TokenType.LET_KEYWORD, Position(2, 1)),
            Token("b", TokenType.IDENTIFIER, Position(2, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 6)),
            Token("string", TokenType.STRING_TYPE, Position(2, 8)),
            Token("=", TokenType.ASSIGNATION, Position(2, 15)),
            Token("monos", TokenType.STRING_LITERAL, Position(2, 17)),
            Token(";", TokenType.SEMICOLON, Position(2, 24)),
            Token("a", TokenType.IDENTIFIER, Position(3, 1)),
            Token("=", TokenType.ASSIGNATION, Position(3, 3)),
            Token("a", TokenType.IDENTIFIER, Position(3, 5)),
            Token("/", TokenType.SLASH, Position(3, 7)),
            Token("b", TokenType.IDENTIFIER, Position(3, 9)),
            Token(";", TokenType.SEMICOLON, Position(3, 10)),
            Token("println", TokenType.CALL_FUNC, Position(4, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(4, 8)),
            Token("a", TokenType.IDENTIFIER, Position(4, 9)),
            Token(")", TokenType.RIGHT_PAREN, Position(4, 10)),
            Token(";", TokenType.SEMICOLON, Position(4, 11)),
        )
        assertEquals(expectedTokens, actualTokens)
    }


    @Test
    fun testTokenizeBraces() {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get("src/test/resources/braces.txt"))
        val text: String = byteArr.toString(Charsets.UTF_8)
        val bufferedReader: BufferedReader = text.reader().buffered()
        val lexer = LexerFactory().createLexer(bufferedReader, 1.1)
        val actualTokens = lexer.tokenizeAll(lexer).toList()

        val expectedTokens = listOf(
            Token("{", TokenType.LEFT_BRACE, Position(1, 1)),
            Token("}", TokenType.RIGHT_BRACE, Position(1, 3))
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testTokenizeIfElse() {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get("src/test/resources/ifElse.txt"))
        val text: String = byteArr.toString(Charsets.UTF_8)
        val bufferedReader: BufferedReader = text.reader().buffered()
        val lexer = LexerFactory().createLexer(bufferedReader, 1.1)
        val actualTokens = lexer.tokenizeAll(lexer).toList()

        val expectedTokens = listOf(
            Token("if", TokenType.IF_KEYWORD, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 4)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 6)),
            Token("{", TokenType.LEFT_BRACE, Position(1, 8)),
            Token("}", TokenType.RIGHT_BRACE, Position(1, 10)),
            Token("else", TokenType.ELSE_KEYWORD, Position(1, 12)),
            Token("{", TokenType.LEFT_BRACE, Position(1, 17)),
            Token("}", TokenType.RIGHT_BRACE, Position(1, 19))
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testTokenizeBoolean() {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get("src/test/resources/boolean.txt"))
        val text: String = byteArr.toString(Charsets.UTF_8)
        val bufferedReader: BufferedReader = text.reader().buffered()
        val lexer = LexerFactory().createLexer(bufferedReader, 1.1)
        val actualTokens = lexer.tokenizeAll(lexer).toList()

        val expectedTokens = listOf(
            Token("const", TokenType.CONST_KEYWORD, Position(1, 1)),
            Token("isTrue", TokenType.IDENTIFIER, Position(1, 7)),
            Token("=", TokenType.ASSIGNATION, Position(1, 14)),
            Token("true", TokenType.BOOLEAN_TYPE, Position(1, 16)),
            Token(";", TokenType.SEMICOLON, Position(1, 20)),
            Token("const", TokenType.CONST_KEYWORD, Position(1, 22)),
            Token("isFalse", TokenType.IDENTIFIER, Position(1, 28)),
            Token("=", TokenType.ASSIGNATION, Position(1, 36)),
            Token("false", TokenType.BOOLEAN_TYPE, Position(1, 38)),
            Token(";", TokenType.SEMICOLON, Position(1, 43))
        )

        assertEquals(expectedTokens, actualTokens)
}}

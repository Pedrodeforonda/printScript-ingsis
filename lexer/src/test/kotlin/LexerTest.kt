import org.example.ClassicTokenStrategies
import org.example.Lexer
import org.junit.jupiter.api.Test
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
        val lexer = Lexer(text, ClassicTokenStrategies())
        val actualTokens = lexer.tokenizeAll(lexer)

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
        val lexer = Lexer(text, ClassicTokenStrategies())
        val actualTokens = lexer.tokenizeAll(lexer)

        val expectedTokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("string", TokenType.STRING_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 15)),
            Token("monos", TokenType.STRING_LITERAL, Position(1, 17)),
            Token(";", TokenType.SEMICOLON, Position(1, 22)),
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testTokenizeCodeLine3() {
        val byteArr: ByteArray = Files.readAllBytes(
            Paths.get("src/test/resources/assigneOperation.txt"),
        )
        val text: String = byteArr.toString(Charsets.UTF_8)
        val lexer = Lexer(text, ClassicTokenStrategies())
        val actualTokens = lexer.tokenizeAll(lexer)

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
        val lexer = Lexer(text, ClassicTokenStrategies())
        val actualTokens = lexer.tokenizeAll(lexer)

        val expectedTokens = listOf(
            Token("println", TokenType.CALL_FUNC, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 8)),
            Token("a", TokenType.IDENTIFIER, Position(1, 9)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 10)),
            Token(";", TokenType.SEMICOLON, Position(1, 11)),
        )

        assertEquals(expectedTokens, actualTokens)
    }
}
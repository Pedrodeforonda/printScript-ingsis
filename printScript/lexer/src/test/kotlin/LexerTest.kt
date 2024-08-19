import org.example.ClassicTokenStrategies
import org.example.Lexer
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class LexerTest {

    @Test
    fun testTokenizeCodeLines() {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get("src/test/resources/assigneNumber.txt"))
        val text: String = byteArr.toString(Charsets.UTF_8)
        val lexer = Lexer(text, ClassicTokenStrategies())
        val actualTokens = lexer.tokenizeAll()

        val expectedTokens = listOf(
            Token("let", TokenType.LET_KEYWORD),
            Token("a", TokenType.IDENTIFIER),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("number", TokenType.NUMBER_TYPE),
            Token("=", TokenType.ASSIGNATION),
            Token("12", TokenType.NUMBER_LITERAL),
            Token(";", TokenType.SEMICOLON),
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testTokenizeCodeLine2() {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get("src/test/resources/assigneString.txt"))
        val text: String = byteArr.toString(Charsets.UTF_8)
        val lexer = Lexer(text, ClassicTokenStrategies())
        val actualTokens = lexer.tokenizeAll()

        val expectedTokens = listOf(
            Token("let", TokenType.LET_KEYWORD),
            Token("a", TokenType.IDENTIFIER),
            Token(":", TokenType.TYPE_ASSIGNATION),
            Token("string", TokenType.STRING_TYPE),
            Token("=", TokenType.ASSIGNATION),
            Token("monos", TokenType.STRING_LITERAL),
            Token(";", TokenType.SEMICOLON),
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testTokenizeCodeLine3() {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get("src/test/resources/assigneOperation.txt"))
        val text: String = byteArr.toString(Charsets.UTF_8)
        val lexer = Lexer(text, ClassicTokenStrategies())
        val actualTokens = lexer.tokenizeAll()

        val expectedTokens = listOf(
            Token("a", TokenType.IDENTIFIER),
            Token("=", TokenType.ASSIGNATION),
            Token("a", TokenType.IDENTIFIER),
            Token("/", TokenType.SLASH),
            Token("b", TokenType.IDENTIFIER),
            Token(";", TokenType.SEMICOLON),
        )

        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testTokenizeCodeLine4() {
        val byteArr: ByteArray = Files.readAllBytes(Paths.get("src/test/resources/println.txt"))
        val text: String = byteArr.toString(Charsets.UTF_8)
        val lexer = Lexer(text, ClassicTokenStrategies())
        val actualTokens = lexer.tokenizeAll()

        val expectedTokens = listOf(
            Token("println", TokenType.CALL_FUNC),
            Token("a", TokenType.IDENTIFIER),
            Token(";", TokenType.SEMICOLON),
        )

        assertEquals(expectedTokens, actualTokens)
    }
}
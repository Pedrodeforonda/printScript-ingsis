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
            Token(charArrayOf('l', 'e', 't'), TokenType.LET_KEYWORD),
            Token(charArrayOf('a'), TokenType.IDENTIFIER),
            Token(charArrayOf(':'), TokenType.TYPE_ASSIGNATION),
            Token(charArrayOf('n', 'u', 'm', 'b', 'e', 'r'), TokenType.NUMBER_TYPE),
            Token(charArrayOf('='), TokenType.ASSIGNATION),
            Token(charArrayOf('1', '2'), TokenType.NUMBER_LITERAL),
            Token(charArrayOf(';'), TokenType.SEMICOLON),
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
            Token(charArrayOf('l', 'e', 't'), TokenType.LET_KEYWORD),
            Token(charArrayOf('a'), TokenType.IDENTIFIER),
            Token(charArrayOf(':'), TokenType.TYPE_ASSIGNATION),
            Token(charArrayOf('s', 't', 'r', 'i', 'n', 'g'), TokenType.STRING_TYPE),
            Token(charArrayOf('='), TokenType.ASSIGNATION),
            Token(charArrayOf('m', 'o', 'n', 'o', 's'), TokenType.STRING_LITERAL),
            Token(charArrayOf(';'), TokenType.SEMICOLON),
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
            Token(charArrayOf('a'), TokenType.IDENTIFIER),
            Token(charArrayOf('='), TokenType.ASSIGNATION),
            Token(charArrayOf('a'), TokenType.IDENTIFIER),
            Token(charArrayOf('/'), TokenType.SLASH),
            Token(charArrayOf('b'), TokenType.IDENTIFIER),
            Token(charArrayOf(';'), TokenType.SEMICOLON),
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
            Token(charArrayOf('p', 'r', 'i', 'n', 't', 'l', 'n'), TokenType.CALL_FUNC),
            Token(charArrayOf('a'), TokenType.IDENTIFIER),
            Token(charArrayOf(';'), TokenType.SEMICOLON),
        )

        assertEquals(expectedTokens, actualTokens)
    }
}
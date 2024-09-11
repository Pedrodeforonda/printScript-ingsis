import lexer.Lexer
import lexer.TokenStrategies1
import main.Position
import main.Token
import main.TokenType
import org.example.lexer.ClassicTokenStrategies
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.PercentageCollector
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.test.assertEquals

class LexerTest {

    private val percentageCollector = PercentageCollector()

    @BeforeEach
    fun resetPercentageCollector() {
        percentageCollector.reset()
    }

    @Test
    fun testTokenizeCodeLines() {
        val path: Path = Paths.get("src/test/resources/assigneNumber.txt")
        val inputStream = Files.newInputStream(path)
        val bufferedReader: BufferedReader = inputStream.bufferedReader()
        val lexer =
            Lexer(bufferedReader, inputStream.available(), percentageCollector, strategies = ClassicTokenStrategies())
        val actualTokens = lexer.tokenize().toList()

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
        assertEquals(100.0, percentageCollector.getPercentage())
    }

    @Test
    fun testTokenizeCodeLine2() {
        val path: Path = Paths.get("src/test/resources/assigneString.txt")
        val inputStream = Files.newInputStream(path)
        val bufferedReader: BufferedReader = inputStream.bufferedReader()
        val lexer = Lexer(bufferedReader, inputStream.available(), percentageCollector, ClassicTokenStrategies())
        val actualTokens = lexer.tokenize().toList()

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
        // assertEquals(100.0, percentageCollector.getPercentage())
    }

    @Test
    fun testTokenizeCodeLine3() {
        val path: Path = Paths.get("src/test/resources/assigneOperation.txt")
        val inputStream = Files.newInputStream(path)
        val bufferedReader: BufferedReader = inputStream.bufferedReader()
        val lexer = Lexer(bufferedReader, inputStream.available(), percentageCollector, ClassicTokenStrategies())
        val actualTokens = lexer.tokenize().toList()

        val expectedTokens = listOf(
            Token("a", TokenType.IDENTIFIER, Position(1, 1)),
            Token("=", TokenType.ASSIGNATION, Position(1, 3)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token("/", TokenType.SLASH, Position(1, 7)),
            Token("b", TokenType.IDENTIFIER, Position(1, 9)),
            Token(";", TokenType.SEMICOLON, Position(1, 10)),
        )

        assertEquals(expectedTokens, actualTokens)
        assertEquals(100.0, percentageCollector.getPercentage())
    }

    @Test
    fun testTokenizeCodeLine4() {
        val path: Path = Paths.get("src/test/resources/println.txt")
        val inputStream = Files.newInputStream(path)
        val bufferedReader: BufferedReader = inputStream.bufferedReader()
        val lexer = Lexer(bufferedReader, inputStream.available(), percentageCollector, TokenStrategies1())
        val actualTokens = lexer.tokenize().toList()

        val expectedTokens = listOf(
            Token("println", TokenType.CALL_FUNC, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 8)),
            Token("a", TokenType.IDENTIFIER, Position(1, 9)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 10)),
            Token(";", TokenType.SEMICOLON, Position(1, 11)),
        )

        assertEquals(expectedTokens, actualTokens)
        assertEquals(100.0, percentageCollector.getPercentage())
    }

    @Test
    fun testLexerMultipleLines() {
        val path: Path = Paths.get("src/test/resources/multipleLines")
        val inputStream = Files.newInputStream(path)
        val bufferedReader: BufferedReader = inputStream.bufferedReader()
        val lexer = Lexer(bufferedReader, inputStream.available(), percentageCollector, TokenStrategies1())
        val actualTokens = lexer.tokenize().toList()

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
        assertEquals(100.0, percentageCollector.getPercentage())
    }

    @Test
    fun testTokenizeKeywords() {
        val path: Path = Paths.get("src/test/resources/keywords.txt")
        val inputStream = Files.newInputStream(path)
        val bufferedReader: BufferedReader = inputStream.bufferedReader()
        val lexer = Lexer(bufferedReader, inputStream.available(), percentageCollector, TokenStrategies1())
        val actualTokens = lexer.tokenize().toList()

        val expectedTokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("string", TokenType.STRING_TYPE, Position(1, 5)),
            Token("number", TokenType.NUMBER_TYPE, Position(1, 12)),
            Token("println", TokenType.CALL_FUNC, Position(1, 19)),
            Token("if", TokenType.IF_KEYWORD, Position(1, 27)),
            Token("else", TokenType.ELSE_KEYWORD, Position(1, 30)),
            Token("boolean", TokenType.BOOLEAN_TYPE, Position(1, 35)),
            Token("const", TokenType.CONST_KEYWORD, Position(1, 43)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 49)),
            Token("false", TokenType.BOOLEAN_LITERAL, Position(1, 54)),
            Token(";", TokenType.SEMICOLON, Position(1, 59)),
        )
        assertEquals(expectedTokens, actualTokens)
        assertEquals(100.0, percentageCollector.getPercentage())
    }

    @Test
    fun testTokenizeIdentifiers() {
        val path: Path = Paths.get("src/test/resources/identifiers.txt")
        val inputStream = Files.newInputStream(path)
        val bufferedReader: BufferedReader = inputStream.bufferedReader()
        val lexer = Lexer(bufferedReader, inputStream.available(), percentageCollector, TokenStrategies1())
        val actualTokens = lexer.tokenize().toList()

        val expectedTokens = listOf(
            Token("myVar", TokenType.IDENTIFIER, Position(1, 1)),
            Token("anotherVar", TokenType.IDENTIFIER, Position(1, 7)),
            Token("private123", TokenType.IDENTIFIER, Position(1, 18)),
            Token(";", TokenType.SEMICOLON, Position(1, 28)),
        )
        assertEquals(expectedTokens, actualTokens)
        assertEquals(100.0, percentageCollector.getPercentage())
    }

    @Test
    fun testTokenizeReadEnvInput() {
        val path: Path = Paths.get("src/test/resources/readEnvInput.txt")
        val inputStream = Files.newInputStream(path)
        val bufferedReader: BufferedReader = inputStream.bufferedReader()
        val lexer = Lexer(bufferedReader, inputStream.available(), percentageCollector, TokenStrategies1())
        val actualTokens = lexer.tokenize().toList()

        val expectedTokens = listOf(
            Token("let", TokenType.LET_KEYWORD, Position(1, 1)),
            Token("a", TokenType.IDENTIFIER, Position(1, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(1, 6)),
            Token("string", TokenType.STRING_TYPE, Position(1, 8)),
            Token("=", TokenType.ASSIGNATION, Position(1, 15)),
            Token("readInput", TokenType.READ_INPUT, Position(1, 17)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 26)),
            Token("write your name", TokenType.STRING_LITERAL, Position(1, 27)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 44)),
            Token(";", TokenType.SEMICOLON, Position(1, 45)),

            Token("let", TokenType.LET_KEYWORD, Position(2, 1)),
            Token("b", TokenType.IDENTIFIER, Position(2, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 6)),
            Token("string", TokenType.STRING_TYPE, Position(2, 8)),
            Token("=", TokenType.ASSIGNATION, Position(2, 15)),
            Token("readEnv", TokenType.READ_ENV, Position(2, 17)),
            Token("(", TokenType.LEFT_PAREN, Position(2, 24)),
            Token("HOME", TokenType.STRING_LITERAL, Position(2, 25)),
            Token(")", TokenType.RIGHT_PAREN, Position(2, 31)),
            Token(";", TokenType.SEMICOLON, Position(2, 32)),

            Token("let", TokenType.LET_KEYWORD, Position(3, 1)),
            Token("c", TokenType.IDENTIFIER, Position(3, 5)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(3, 6)),
            Token("string", TokenType.STRING_TYPE, Position(3, 8)),
            Token("=", TokenType.ASSIGNATION, Position(3, 15)),
            Token("readInput", TokenType.READ_INPUT, Position(3, 17)),
            Token("(", TokenType.LEFT_PAREN, Position(3, 26)),
            Token("a", TokenType.IDENTIFIER, Position(3, 27)),
            Token(")", TokenType.RIGHT_PAREN, Position(3, 28)),
            Token(";", TokenType.SEMICOLON, Position(3, 29)),
        )

        assertEquals(expectedTokens, actualTokens)
        assertEquals(100.0, percentageCollector.getPercentage())
    }

    @Test
    fun testTokenizeIfElseWithBody() {
        val path: Path = Paths.get("src/test/resources/ifElseWithBody.txt")
        val inputStream = Files.newInputStream(path)
        val bufferedReader: BufferedReader = inputStream.bufferedReader()
        val lexer = Lexer(bufferedReader, inputStream.available(), percentageCollector, TokenStrategies1())
        val actualTokens = lexer.tokenize().toList()

        val expectedTokens = listOf(
            Token("if", TokenType.IF_KEYWORD, Position(1, 1)),
            Token("(", TokenType.LEFT_PAREN, Position(1, 4)),
            Token("true", TokenType.BOOLEAN_LITERAL, Position(1, 6)),
            Token(")", TokenType.RIGHT_PAREN, Position(1, 11)),
            Token("{", TokenType.LEFT_BRACE, Position(1, 13)),
            Token("let", TokenType.LET_KEYWORD, Position(2, 5)),
            Token("a", TokenType.IDENTIFIER, Position(2, 9)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(2, 11)),
            Token("string", TokenType.STRING_TYPE, Position(2, 13)),
            Token("=", TokenType.ASSIGNATION, Position(2, 20)),
            Token("hello", TokenType.STRING_LITERAL, Position(2, 22)),
            Token(";", TokenType.SEMICOLON, Position(2, 29)),
            Token("println", TokenType.CALL_FUNC, Position(3, 5)),
            Token("(", TokenType.LEFT_PAREN, Position(3, 13)),
            Token("a", TokenType.IDENTIFIER, Position(3, 15)),
            Token(")", TokenType.RIGHT_PAREN, Position(3, 17)),
            Token(";", TokenType.SEMICOLON, Position(3, 18)),
            Token("let", TokenType.LET_KEYWORD, Position(4, 5)),
            Token("b", TokenType.IDENTIFIER, Position(4, 9)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(4, 11)),
            Token("number", TokenType.NUMBER_TYPE, Position(4, 13)),
            Token("=", TokenType.ASSIGNATION, Position(4, 20)),
            Token("12", TokenType.NUMBER_LITERAL, Position(4, 22)),
            Token(";", TokenType.SEMICOLON, Position(4, 24)),
            Token("let", TokenType.LET_KEYWORD, Position(5, 5)),
            Token("c", TokenType.IDENTIFIER, Position(5, 9)),
            Token(":", TokenType.TYPE_ASSIGNATION, Position(5, 11)),
            Token("number", TokenType.NUMBER_TYPE, Position(5, 13)),
            Token("=", TokenType.ASSIGNATION, Position(5, 20)),
            Token("13", TokenType.NUMBER_LITERAL, Position(5, 22)),
            Token(";", TokenType.SEMICOLON, Position(5, 24)),
            Token("println", TokenType.CALL_FUNC, Position(6, 5)),
            Token("(", TokenType.LEFT_PAREN, Position(6, 13)),
            Token("b", TokenType.IDENTIFIER, Position(6, 15)),
            Token("+", TokenType.PLUS, Position(6, 17)),
            Token("c", TokenType.IDENTIFIER, Position(6, 19)),
            Token(")", TokenType.RIGHT_PAREN, Position(6, 21)),
            Token(";", TokenType.SEMICOLON, Position(6, 22)),
            Token("println", TokenType.CALL_FUNC, Position(7, 5)),
            Token("(", TokenType.LEFT_PAREN, Position(7, 13)),
            Token("true", TokenType.STRING_LITERAL, Position(7, 15)),
            Token(")", TokenType.RIGHT_PAREN, Position(7, 22)),
            Token(";", TokenType.SEMICOLON, Position(7, 23)),
            Token("}", TokenType.RIGHT_BRACE, Position(8, 1)),
            Token("else", TokenType.ELSE_KEYWORD, Position(8, 3)),
            Token("{", TokenType.LEFT_BRACE, Position(8, 8)),
            Token("println", TokenType.CALL_FUNC, Position(9, 5)),
            Token("(", TokenType.LEFT_PAREN, Position(9, 13)),
            Token("false", TokenType.STRING_LITERAL, Position(9, 15)),
            Token(")", TokenType.RIGHT_PAREN, Position(9, 23)),
            Token(";", TokenType.SEMICOLON, Position(9, 24)),
            Token("}", TokenType.RIGHT_BRACE, Position(10, 1)),
        )

        assertEquals(expectedTokens, actualTokens)
        assertEquals(100.0, percentageCollector.getPercentage())
    }
}

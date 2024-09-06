package org.example

import main.Position
import main.Token
import main.TokenType
import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal
import nodes.Node
import org.example.interpreter.Interpreter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.DeclarationKeyWord
import utils.InterpreterException
import utils.InterpreterResult
import utils.ParsingResult
import utils.PercentageCollector
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InterpreterTest {

    private val collector = PercentageCollector()
    private val interpreter = Interpreter(collector)

    private fun nodeToParsingResult(node: Node): ParsingResult {
        return ParsingResult(node, null)
    }

    @BeforeEach
    fun resetPercentageCollector() {
        collector.reset()
    }

    @Test
    fun testAssignation() {
        val stringAssignation = Assignation(
            Declaration("name", "string", DeclarationKeyWord.CONST_KEYWORD, Position(0, 0)),
            Literal("Pedro", Position(0, 0)),
            Position(0, 0),
        )

        val result = nodeToParsingResult(stringAssignation)
        val interpreterResult = interpreter.interpret(sequenceOf(result))
        interpreterResult.toList()

        assertEquals("Pedro", interpreter.getVariableMap()[Pair("name", "string")])

        val numberAssignation = Assignation(
            Declaration("num", "number", DeclarationKeyWord.LET_KEYWORD, Position(0, 0)),
            Literal(10, Position(0, 0)),
            Position(0, 0),
        )

        val result2 = nodeToParsingResult(numberAssignation)
        val interpreterResult2 = interpreter.interpret(sequenceOf(result2))
        interpreterResult2.toList()

        assertEquals(10, interpreter.getVariableMap()[Pair("num", "number")])
    }

    @Test
    fun testPrintVariable() {
        val stringAssignation = Assignation(
            Declaration("name", "string", DeclarationKeyWord.CONST_KEYWORD, Position(0, 0)),
            Literal("Pedro", Position(0, 0)),
            Position(0, 0),
        )
        val callNode = CallNode("println", listOf(Identifier("name", Position(0, 0))), Position(0, 0))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(stringAssignation)
        val result2 = nodeToParsingResult(callNode)
        val interpreterResult = interpreter.interpret(sequenceOf(result, result2))
        interpreterResult.toList()

        assertEquals("Pedro", outContent.toString().replace(System.lineSeparator(), ""))

        val numberAssignation = Assignation(
            Declaration("num", "number", DeclarationKeyWord.LET_KEYWORD, Position(0, 0)),
            Literal(10, Position(0, 0)),
            Position(0, 0),
        )
        val callNode2 = CallNode("println", listOf(Identifier("num", Position(0, 0))), Position(0, 0))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result3 = nodeToParsingResult(numberAssignation)
        val result4 = nodeToParsingResult(callNode2)

        val interpreterResult2 = interpreter.interpret(sequenceOf(result3, result4))
        interpreterResult2.toList()

        assertEquals("10", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testPrintLiteral() {
        val stringLiteral = Literal("Pedro", Position(0, 0))
        val callNode = CallNode("println", listOf(stringLiteral), Position(0, 0))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(callNode)
        val interpreterResult = interpreter.interpret(sequenceOf(result))
        interpreterResult.toList()

        assertEquals("Pedro", outContent.toString().replace(System.lineSeparator(), ""))

        val numberLiteral = Literal(10, Position(0, 0))
        val callNode2 = CallNode("println", listOf(numberLiteral), Position(0, 0))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result2 = nodeToParsingResult(callNode2)
        val interpreterResult2 = interpreter.interpret(sequenceOf(result2))
        interpreterResult2.toList()

        assertEquals("10", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testSum() {
        val sum =
            BinaryNode(
                Literal(10, Position(0, 0)),
                Token("+", TokenType.PLUS, Position(0, 0)),
                Literal(20, Position(0, 0)),
                Position(0, 0),
            )
        val callNode = CallNode("println", listOf(sum), Position(0, 0))

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(callNode)
        val interpreterResult = interpreter.interpret(sequenceOf(result))
        interpreterResult.toList()

        assertEquals("30", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testConcatenation() {
        val concatenation = BinaryNode(
            Literal("Hello", Position(0, 0)),
            Token("+", TokenType.PLUS, Position(0, 0)),
            Literal(" World", Position(0, 0)),
            Position(0, 0),
        )
        val callNode = CallNode("println", listOf(concatenation), Position(0, 0))

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(callNode)
        val interpreterResult = interpreter.interpret(sequenceOf(result))
        interpreterResult.toList()

        assertEquals("Hello World", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testMixedConcatenation() {
        val concatenation = BinaryNode(
            Literal("Diego", Position(0, 0)),
            Token("+", TokenType.PLUS, Position(0, 0)),
            Literal(10, Position(0, 0)),
            Position(0, 0),
        )
        val callNode = CallNode("println", listOf(concatenation), Position(0, 0))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(callNode)
        val interpreterResult = interpreter.interpret(sequenceOf(result))
        interpreterResult.toList()

        assertEquals("Diego10", outContent.toString().replace(System.lineSeparator(), ""))

        val concatenation2 = BinaryNode(
            Literal(10, Position(0, 0)),
            Token("+", TokenType.PLUS, Position(0, 0)),
            Literal("Diego", Position(0, 0)),
            Position(0, 0),
        )
        val callNode2 = CallNode("println", listOf(concatenation2), Position(0, 0))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result2 = nodeToParsingResult(callNode2)
        val interpreterResult2 = interpreter.interpret(sequenceOf(result2))
        interpreterResult2.toList()

        assertEquals("10Diego", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testSubtraction() {
        val subtraction = BinaryNode(
            Literal(10, Position(0, 0)),
            Token("-", TokenType.MINUS, Position(0, 0)),
            Literal(5, Position(0, 0)),
            Position(0, 0),
        )
        val callNode = CallNode("println", listOf(subtraction), Position(0, 0))

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(callNode)
        val interpreterResult = interpreter.interpret(sequenceOf(result))
        interpreterResult.toList()

        assertEquals("5", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testMultiplication() {
        val multiplication = BinaryNode(
            Literal(10, Position(0, 0)),
            Token("*", TokenType.ASTERISK, Position(0, 0)),
            Literal(5, Position(0, 0)),
            Position(0, 0),
        )
        val callNode = CallNode("println", listOf(multiplication), Position(0, 0))

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(callNode)
        val interpreterResult = interpreter.interpret(sequenceOf(result))
        interpreterResult.toList()

        assertEquals("50", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testDivision() {
        val division = BinaryNode(
            Literal(10, Position(0, 0)),
            Token("/", TokenType.SLASH, Position(0, 0)),
            Literal(5, Position(0, 0)),
            Position(0, 0),
        )
        val callNode = CallNode("println", listOf(division), Position(0, 0))

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(callNode)
        val interpreterResult = interpreter.interpret(sequenceOf(result))
        interpreterResult.toList()

        assertEquals("2", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testPrintOperations() {
        val sum = BinaryNode(
            BinaryNode(
                Literal(10, Position(0, 0)),
                Token("+", TokenType.PLUS, Position(0, 0)),
                Literal(20, Position(0, 0)),
                Position(0, 0),
            ),
            Token("+", TokenType.PLUS, Position(0, 0)),
            Literal(" ", Position(0, 0)),
            Position(0, 0),
        )
        val sub = BinaryNode(
            BinaryNode(
                Literal(10, Position(0, 0)),
                Token("-", TokenType.MINUS, Position(0, 0)),
                Literal(5, Position(0, 0)),
                Position(0, 0),
            ),
            Token("+", TokenType.PLUS, Position(0, 0)),
            Literal(" ", Position(0, 0)),
            Position(0, 0),
        )
        val mul = BinaryNode(
            BinaryNode(
                Literal(10, Position(0, 0)),
                Token("*", TokenType.ASTERISK, Position(0, 0)),
                Literal(5, Position(0, 0)),
                Position(0, 0),
            ),
            Token("+", TokenType.PLUS, Position(0, 0)),
            Literal(" ", Position(0, 0)),
            Position(0, 0),
        )
        val div =
            BinaryNode(
                Literal(10, Position(0, 0)),
                Token("/", TokenType.SLASH, Position(0, 0)),
                Literal(5, Position(0, 0)),
                Position(0, 0),
            )

        val callNode = CallNode("println", listOf(sum), Position(0, 0))
        val callNode2 = CallNode("println", listOf(sub), Position(0, 0))
        val callNode3 = CallNode("println", listOf(mul), Position(0, 0))
        val callNode4 = CallNode("println", listOf(div), Position(0, 0))

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(callNode)
        val result2 = nodeToParsingResult(callNode2)
        val result3 = nodeToParsingResult(callNode3)
        val result4 = nodeToParsingResult(callNode4)

        val interpreterResult = interpreter.interpret(sequenceOf(result, result2, result3, result4))
        interpreterResult.toList()

        assertEquals("30 5 50 2", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testReAssignation() {
        val stringAssignation = Assignation(
            Declaration("name", "string", DeclarationKeyWord.CONST_KEYWORD, Position(0, 0)),
            Literal("Pedro", Position(0, 0)),
            Position(0, 0),
        )
        val stringReAssignation = Assignation(
            Identifier("name", Position(0, 0)),
            Literal("el nene", Position(0, 0)),
            Position(0, 0),
        )

        val result = nodeToParsingResult(stringAssignation)
        val result2 = nodeToParsingResult(stringReAssignation)
        val resultInterpreter: Sequence<InterpreterResult> = interpreter.interpret(sequenceOf(result, result2))
        resultInterpreter.toList()

        assertEquals("el nene", interpreter.getVariableMap()[Pair("name", "string")])
    }

    @Test
    fun testFailingReAssignation() {
        val stringAssignation = Assignation(
            Declaration("name", "string", DeclarationKeyWord.CONST_KEYWORD, Position(0, 0)),
            Literal("Pedro", Position(0, 0)),
            Position(0, 0),
        )
        val stringReAssignation = Assignation(
            Identifier("name", Position(0, 0)),
            Literal(7, Position(0, 0)),
            Position(0, 0),
        )

        val result = nodeToParsingResult(stringAssignation)
        val result2 = nodeToParsingResult(stringReAssignation)

        val resultInterpreter: Sequence<InterpreterResult> = interpreter.interpret(sequenceOf(result, result2))
        val errorList = ArrayList<Exception>()

        for (res in resultInterpreter) {
            if (res.hasException()) {
                errorList.add(res.getException())
            }
        }

        assertTrue { errorList.size > 0 }

        val error = errorList[0] as InterpreterException
        assertEquals(
            error.message,
            "Invalid type: expected string, but got number on variable name, at line 0 column 0",
        )
    }
}

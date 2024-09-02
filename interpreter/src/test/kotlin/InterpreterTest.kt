package org.example

import DeclarationKeyWord
import Position
import Token
import TokenType
import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class InterpreterTest {

    private val interpreter = Interpreter()

    @Test
    fun testAssignation() {
        val stringAssignation = Assignation(
            Declaration("name", "string", DeclarationKeyWord.CONST_KEYWORD, Position(0, 0)),
            Literal("Pedro", Position(0, 0)),
            Position(0, 0),
        )
        interpreter.interpret(sequenceOf(stringAssignation))

        assertEquals("Pedro", interpreter.getVariableMap()[Pair("name", "string")])

        val numberAssignation = Assignation(
            Declaration("num", "number", DeclarationKeyWord.LET_KEYWORD, Position(0, 0)),
            Literal(10, Position(0, 0)),
            Position(0, 0),
        )
        interpreter.interpret(sequenceOf(numberAssignation))

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

        interpreter.interpret(sequenceOf(stringAssignation, callNode))

        assertEquals("Pedro", outContent.toString().replace(System.lineSeparator(), ""))

        val numberAssignation = Assignation(
            Declaration("num", "number", DeclarationKeyWord.LET_KEYWORD, Position(0, 0)),
            Literal(10, Position(0, 0)),
            Position(0, 0),
        )
        val callNode2 = CallNode("println", listOf(Identifier("num", Position(0, 0))), Position(0, 0))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(sequenceOf(numberAssignation, callNode2))

        assertEquals("10", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testPrintLiteral() {
        val stringLiteral = Literal("Pedro", Position(0, 0))
        val callNode = CallNode("println", listOf(stringLiteral), Position(0, 0))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(sequenceOf(callNode))

        assertEquals("Pedro", outContent.toString().replace(System.lineSeparator(), ""))

        val numberLiteral = Literal(10, Position(0, 0))
        val callNode2 = CallNode("println", listOf(numberLiteral), Position(0, 0))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(sequenceOf(callNode2))

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

        interpreter.interpret(sequenceOf(callNode))

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

        interpreter.interpret(sequenceOf(callNode))

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

        interpreter.interpret(sequenceOf(callNode))

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

        interpreter.interpret(sequenceOf(callNode2))

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

        interpreter.interpret(sequenceOf(callNode))

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

        interpreter.interpret(sequenceOf(callNode))

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

        interpreter.interpret(sequenceOf(callNode))

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

        interpreter.interpret(sequenceOf(callNode, callNode2, callNode3, callNode4))

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
        interpreter.interpret(sequenceOf(stringAssignation, stringReAssignation))

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

        val error = assertThrows<InterpreterException> {
            interpreter.interpret(sequenceOf(stringAssignation, stringReAssignation))
        }

        assertEquals(error.message, "Invalid type: expected string, but got Int on variable name, at line 0 column 0")
    }
}

package org.example

import DeclarationKeyWord
import Token
import TokenType
import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class InterpreterTest {

    private val interpreter = Interpreter()

    @Test
    fun testAssignation() {
        val stringAssignation = Assignation(
            Declaration("name", "string", DeclarationKeyWord.CONST_KEYWORD),
            Literal("Pedro"),
        )
        interpreter.interpret(listOf(stringAssignation))

        assertEquals("Pedro", interpreter.getVariableMap()[Pair("name", "string")])

        val numberAssignation = Assignation(
            Declaration("num", "number", DeclarationKeyWord.LET_KEYWORD),
            Literal(10),
        )
        interpreter.interpret(listOf(numberAssignation))

        assertEquals(10, interpreter.getVariableMap()[Pair("num", "number")])
    }

    @Test
    fun testPrintVariable() {
        val stringAssignation = Assignation(
            Declaration("name", "string", DeclarationKeyWord.CONST_KEYWORD),
            Literal("Pedro"),
        )
        val callNode = CallNode("println", listOf(Identifier("name")))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(stringAssignation, callNode))

        assertEquals("Pedro", outContent.toString().replace(System.lineSeparator(), ""))

        val numberAssignation = Assignation(
            Declaration("num", "number", DeclarationKeyWord.LET_KEYWORD),
            Literal(10),
        )
        val callNode2 = CallNode("println", listOf(Identifier("num")))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(numberAssignation, callNode2))

        assertEquals("10", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testPrintLiteral() {
        val stringLiteral = Literal("Pedro")
        val callNode = CallNode("println", listOf(stringLiteral))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("Pedro", outContent.toString().replace(System.lineSeparator(), ""))

        val numberLiteral = Literal(10)
        val callNode2 = CallNode("println", listOf(numberLiteral))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode2))

        assertEquals("10", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testSum() {
        val sum = BinaryNode(Literal(10), Token("+", TokenType.PLUS), Literal(20))
        val callNode = CallNode("println", listOf(sum))

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("30", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testConcatenation() {
        val concatenation = BinaryNode(
            Literal("Hello"),
            Token("+", TokenType.PLUS),
            Literal(" World"),
        )
        val callNode = CallNode("println", listOf(concatenation))

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("Hello World", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testMixedConcatenation() {
        val concatenation = BinaryNode(
            Literal("Diego"),
            Token("+", TokenType.PLUS),
            Literal(10),
        )
        val callNode = CallNode("println", listOf(concatenation))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("Diego10", outContent.toString().replace(System.lineSeparator(), ""))

        val concatenation2 = BinaryNode(
            Literal(10),
            Token("+", TokenType.PLUS),
            Literal("Diego"),
        )
        val callNode2 = CallNode("println", listOf(concatenation2))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode2))

        assertEquals("10Diego", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testSubtraction() {
        val subtraction = BinaryNode(
            Literal(10),
            Token("-", TokenType.MINUS),
            Literal(5),
        )
        val callNode = CallNode("println", listOf(subtraction))

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("5", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testMultiplication() {
        val multiplication = BinaryNode(
            Literal(10),
            Token("*", TokenType.ASTERISK),
            Literal(5),
        )
        val callNode = CallNode("println", listOf(multiplication))

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("50", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testDivision() {
        val division = BinaryNode(
            Literal(10),
            Token("/", TokenType.SLASH),
            Literal(5),
        )
        val callNode = CallNode("println", listOf(division))

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("2", outContent.toString().replace(System.lineSeparator(), ""))
    }
}

package org.example

import DeclarationKeyWord
import Token
import TokenType
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import nodes.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
class InterpreterTest {

    @Test
    fun testAssignation() {
        val interpreter = Interpreter()
        val assignation = Assignation(
            Declaration("name", "STRING_TYPE", DeclarationKeyWord.CONST_KEYWORD),
            Literal("Pedro")
        )
        interpreter.interpret(listOf(assignation))

        assertEquals("Pedro", interpreter.getVariableMap()["name"])

        val assignation2 = Assignation(
            Declaration("name", "NUMBER_TYPE", DeclarationKeyWord.LET_KEYWORD),
            Literal(10)
        )
        interpreter.interpret(listOf(assignation2))

        assertEquals(10, interpreter.getVariableMap()["name"])
    }

    @Test
    fun testPrintVariable() {
        val interpreter = Interpreter()
        val assignation = Assignation(
            Declaration("name", "STRING_TYPE", DeclarationKeyWord.CONST_KEYWORD),
            Literal("Pedro")
        )
        val callNode = CallNode("println", listOf(Identifier("name")))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(assignation, callNode))

        assertEquals("Pedro\n", outContent.toString())

        val assignation2 = Assignation(
            Declaration("number", "NUMBER_TYPE", DeclarationKeyWord.LET_KEYWORD),
            Literal(10)
        )
        val callNode2 = CallNode("println", listOf(Identifier("number")))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(assignation2, callNode2))

        assertEquals("10\n", outContent.toString())
    }

    @Test
    fun testPrintLiteral() {
        val interpreter = Interpreter()
        val literal = Literal("Pedro")
        val callNode = CallNode("println", listOf(literal))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("Pedro\n", outContent.toString())

        val literal2 = Literal(10)
        val callNode2 = CallNode("println", listOf(literal2))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode2))

        assertEquals("10\n", outContent.toString())
    }

    @Test
    fun testSum() {
        val interpreter = Interpreter()
        val sum = BinaryNode(Literal(10), Token("+".toCharArray(), TokenType.PLUS), Literal(20))
        val callNode = CallNode("println", listOf(sum))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("30\n", outContent.toString())
    }

    @Test
    fun testConcatenation() {
        val interpreter = Interpreter()
        val concatenation = BinaryNode(
            Literal("Hello"),
            Token("+".toCharArray(), TokenType.PLUS),
            Literal(" World")
        )
        val callNode = CallNode("println", listOf(concatenation))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("Hello World\n", outContent.toString())
    }

    @Test
    fun testMixedConcatenation() {
        val interpreter = Interpreter()
        val concatenation = BinaryNode(
            Literal("Diego"),
            Token("+".toCharArray(), TokenType.PLUS),
            Literal(10)
        )
        val callNode = CallNode("println", listOf(concatenation))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("Diego10\n", outContent.toString())

        val concatenation2 = BinaryNode(
            Literal(10),
            Token("+".toCharArray(), TokenType.PLUS),
            Literal("Diego")
        )
        val callNode2 = CallNode("println", listOf(concatenation2))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode2))

        assertEquals("10Diego\n", outContent.toString())
    }

    @Test
    fun testSubtraction() {
        val interpreter = Interpreter()
        val subtraction = BinaryNode(
            Literal(10),
            Token("-".toCharArray(), TokenType.MINUS),
            Literal(5)
        )
        val callNode = CallNode("println", listOf(subtraction))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("5\n", outContent.toString())
    }

    @Test
    fun testMultiplication() {
        val interpreter = Interpreter()
        val multiplication = BinaryNode(
            Literal(10),
            Token("*".toCharArray(), TokenType.ASTERISK),
            Literal(5)
        )
        val callNode = CallNode("println", listOf(multiplication))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("50\n", outContent.toString())
    }

    @Test
    fun testDivision() {
        val interpreter = Interpreter()
        val division = BinaryNode(
            Literal(10),
            Token("/".toCharArray(), TokenType.SLASH),
            Literal(5)
        )
        val callNode = CallNode("println", listOf(division))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("2\n", outContent.toString())
    }
}

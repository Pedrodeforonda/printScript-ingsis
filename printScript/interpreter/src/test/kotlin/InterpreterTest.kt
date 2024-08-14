package org.example

import DeclarationKeyWord
import nodes.Assignation
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import org.example.nodes.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class InterpreterTest {

    @Test
    fun testAssignation() {
        val interpreter = Interpreter()
        val assignation = Assignation(Declaration("name", "STRING_TYPE", DeclarationKeyWord.CONST_KEYWORD), Literal("Pedro"))
        interpreter.interpret(listOf(assignation))

        assertEquals("Pedro", interpreter.getVariableMap()["name"])

        val assignation2 = Assignation(Declaration("name", "NUMBER_TYPE", DeclarationKeyWord.LET_KEYWORD), Literal(10))
        interpreter.interpret(listOf(assignation2))

        assertEquals(10, interpreter.getVariableMap()["name"])
    }

    @Test
    fun testPrintVariable() {
        val interpreter = Interpreter()
        val assignation = Assignation(Declaration("name", "STRING_TYPE", DeclarationKeyWord.CONST_KEYWORD), Literal("Pedro"))
        val callNode = CallNode("println", listOf(Identifier("name")))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(assignation, callNode))

        assertEquals("Pedro\r\n", outContent.toString())

        val assignation2 = Assignation(Declaration("number", "NUMBER_TYPE", DeclarationKeyWord.LET_KEYWORD), Literal(10))
        val callNode2 = CallNode("println", listOf(Identifier("number")))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(assignation2, callNode2))

        assertEquals("10\r\n", outContent.toString())
    }

    @Test
    fun testPrintLiteral() {
        val interpreter = Interpreter()
        val literal = Literal("Pedro")
        val callNode = CallNode("println", listOf(literal))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode))

        assertEquals("Pedro\r\n", outContent.toString())

        val literal2 = Literal(10)
        val callNode2 = CallNode("println", listOf(literal2))

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        interpreter.interpret(listOf(callNode2))

        assertEquals("10\r\n", outContent.toString())
    }
}
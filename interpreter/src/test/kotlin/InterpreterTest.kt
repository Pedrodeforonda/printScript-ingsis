package org.example

import dataObjects.InterpreterException
import dataObjects.ParsingResult
import interpreter.Interpreter
import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.IfNode
import nodes.Literal
import nodes.Node
import nodes.Position
import nodes.ReadEnv
import nodes.ReadInput
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import types.BinaryOperatorType
import types.DeclarationType
import types.LiteralType
import utils.InterpreterResult
import utils.MainStringInputProvider
import utils.PercentageCollector
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InterpreterTest {

    private val collector = PercentageCollector()
    private val interpreter = Interpreter(collector, MainStringInputProvider(iterator { }), mapOf())

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
            Declaration("name", "string", DeclarationType.LET_KEYWORD, Position(0, 0)),
            Literal("Pedro", Position(0, 0), LiteralType.STRING),
            Position(0, 0),
        )

        val result = nodeToParsingResult(stringAssignation)
        val interpreterResult = interpreter.interpret(sequenceOf(result))
        interpreterResult.toList()

        assertEquals(Triple("Pedro", "string", true), interpreter.getVariableMap()["name"])

        val numberAssignation = Assignation(
            Declaration("num", "number", DeclarationType.LET_KEYWORD, Position(0, 0)),
            Literal(10, Position(0, 0), LiteralType.NUMBER),
            Position(0, 0),
        )

        val result2 = nodeToParsingResult(numberAssignation)
        val interpreterResult2 = interpreter.interpret(sequenceOf(result2))
        interpreterResult2.toList()

        assertEquals(Triple(10, "number", true), interpreter.getVariableMap()["num"])
    }

    @Test
    fun testPrintVariable() {
        val stringAssignation = Assignation(
            Declaration("name", "string", DeclarationType.LET_KEYWORD, Position(0, 0)),
            Literal("Pedro", Position(0, 0), LiteralType.STRING),
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
            Declaration("num", "number", DeclarationType.LET_KEYWORD, Position(0, 0)),
            Literal(10, Position(0, 0), LiteralType.NUMBER),
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
        val stringLiteral = Literal("Pedro", Position(0, 0), LiteralType.STRING)
        val callNode = CallNode("println", listOf(stringLiteral), Position(0, 0))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(callNode)
        val interpreterResult = interpreter.interpret(sequenceOf(result))
        interpreterResult.toList()

        assertEquals("Pedro", outContent.toString().replace(System.lineSeparator(), ""))

        val numberLiteral = Literal(10, Position(0, 0), LiteralType.NUMBER)
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
                Literal(10, Position(0, 0), LiteralType.NUMBER),
                BinaryOperatorType.PLUS,
                Literal(20, Position(0, 0), LiteralType.NUMBER),
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
            Literal("Hello", Position(0, 0), LiteralType.STRING),
            BinaryOperatorType.PLUS,
            Literal(" World", Position(0, 0), LiteralType.STRING),
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
            Literal("Diego", Position(0, 0), LiteralType.STRING),
            BinaryOperatorType.PLUS,
            Literal(10, Position(0, 0), LiteralType.NUMBER),
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
            Literal(10, Position(0, 0), LiteralType.NUMBER),
            BinaryOperatorType.PLUS,
            Literal("Diego", Position(0, 0), LiteralType.STRING),
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
            Literal(10, Position(0, 0), LiteralType.NUMBER),
            BinaryOperatorType.MINUS,
            Literal(5, Position(0, 0), LiteralType.NUMBER),
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
            Literal(10, Position(0, 0), LiteralType.NUMBER),
            BinaryOperatorType.ASTERISK,
            Literal(5, Position(0, 0), LiteralType.NUMBER),
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
            Literal(10, Position(0, 0), LiteralType.NUMBER),
            BinaryOperatorType.SLASH,
            Literal(5, Position(0, 0), LiteralType.NUMBER),
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
                Literal(10, Position(0, 0), LiteralType.NUMBER),
                BinaryOperatorType.PLUS,
                Literal(20, Position(0, 0), LiteralType.NUMBER),
                Position(0, 0),
            ),
            BinaryOperatorType.PLUS,
            Literal(" ", Position(0, 0), LiteralType.STRING),
            Position(0, 0),
        )
        val sub = BinaryNode(
            BinaryNode(
                Literal(10, Position(0, 0), LiteralType.NUMBER),
                BinaryOperatorType.MINUS,
                Literal(5, Position(0, 0), LiteralType.NUMBER),
                Position(0, 0),
            ),
            BinaryOperatorType.PLUS,
            Literal(" ", Position(0, 0), LiteralType.STRING),
            Position(0, 0),
        )
        val mul = BinaryNode(
            BinaryNode(
                Literal(10, Position(0, 0), LiteralType.NUMBER),
                BinaryOperatorType.ASTERISK,
                Literal(5, Position(0, 0), LiteralType.NUMBER),
                Position(0, 0),
            ),
            BinaryOperatorType.PLUS,
            Literal(" ", Position(0, 0), LiteralType.STRING),
            Position(0, 0),
        )
        val div =
            BinaryNode(
                Literal(10, Position(0, 0), LiteralType.NUMBER),
                BinaryOperatorType.SLASH,
                Literal(5, Position(0, 0), LiteralType.NUMBER),
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
            Declaration("name", "string", DeclarationType.LET_KEYWORD, Position(0, 0)),
            Literal("Pedro", Position(0, 0), LiteralType.STRING),
            Position(0, 0),
        )
        val stringReAssignation = Assignation(
            Identifier("name", Position(0, 0)),
            Literal("el nene", Position(0, 0), LiteralType.STRING),
            Position(0, 0),
        )

        val result = nodeToParsingResult(stringAssignation)
        val result2 = nodeToParsingResult(stringReAssignation)
        val resultInterpreter: Sequence<InterpreterResult> = interpreter.interpret(sequenceOf(result, result2))
        resultInterpreter.toList()

        assertEquals(Triple("el nene", "string", true), interpreter.getVariableMap()["name"])
    }

    @Test
    fun testFailingReAssignation() {
        val stringAssignation = Assignation(
            Declaration("name", "string", DeclarationType.LET_KEYWORD, Position(0, 0)),
            Literal("Pedro", Position(0, 0), LiteralType.STRING),
            Position(0, 0),
        )
        val stringReAssignation = Assignation(
            Identifier("name", Position(0, 0)),
            Literal(7, Position(0, 0), LiteralType.NUMBER),
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

    @Test
    fun testFailingReAssignationImmutable() {
        val stringAssignation = Assignation(
            Declaration("name", "string", DeclarationType.CONST_KEYWORD, Position(0, 0)),
            Literal("Pedro", Position(0, 0), LiteralType.STRING),
            Position(0, 0),
        )
        val stringReAssignation = Assignation(
            Identifier("name", Position(0, 0)),
            Literal("el nene", Position(0, 0), LiteralType.STRING),
            Position(0, 0),
        )

        val result = nodeToParsingResult(stringAssignation)
        val result2 = nodeToParsingResult(stringReAssignation)

        val resultInterpreter: Sequence<InterpreterResult> = interpreter.interpret(sequenceOf(result, result2))
        val errorList = ArrayList<Exception>()

        for (res in resultInterpreter) {
            if (res.hasException()) {
                errorList.add(res.getException())
                println(res.getException().message)
            }
        }

        assertTrue { errorList.size == 1 }

        val error = errorList[0] as InterpreterException
        assertEquals("Variable name is not mutable", error.message)
    }

    @Test
    fun testIf() {
        val assignation = Assignation(
            Declaration("a", "boolean", DeclarationType.LET_KEYWORD, Position(0, 0)),
            Literal(true, Position(0, 0), LiteralType.BOOLEAN),
            Position(0, 0),
        )
        val firstLine = Assignation(
            Declaration("b", "number", DeclarationType.LET_KEYWORD, Position(0, 0)),
            Literal(10, Position(0, 0), LiteralType.NUMBER),
            Position(0, 0),
        )
        val secondLine = CallNode("println", listOf(Identifier("b", Position(0, 0))), Position(0, 0))
        val ifNode = IfNode(Identifier("a", Position(0, 0)), listOf(firstLine, secondLine), null, Position(0, 0))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(assignation)
        val result2 = nodeToParsingResult(ifNode)

        val interpreterResult = interpreter.interpret(sequenceOf(result, result2))
        interpreterResult.toList()

        assertEquals("10", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testElse() {
        val assignation = Assignation(
            Declaration("a", "boolean", DeclarationType.LET_KEYWORD, Position(0, 0)),
            Literal(false, Position(0, 0), LiteralType.BOOLEAN),
            Position(0, 0),
        )
        val firstLine =
            CallNode("println", listOf(Literal("1", Position(0, 0), LiteralType.STRING)), Position(0, 0))
        val secondLine = CallNode("println", listOf(Identifier("a", Position(0, 0))), Position(0, 0))
        val ifNode = IfNode(Identifier("a", Position(0, 0)), listOf(firstLine), listOf(secondLine), Position(0, 0))

        var outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        val result = nodeToParsingResult(assignation)
        val result2 = nodeToParsingResult(ifNode)

        val interpreterResult = interpreter.interpret(sequenceOf(result, result2))
        interpreterResult.toList()

        assertEquals("false", outContent.toString().replace(System.lineSeparator(), ""))
    }

    @Test
    fun testReadEnv() {
        val stringAssignation = Assignation(
            Declaration("env", "string", DeclarationType.LET_KEYWORD, Position(0, 0)),
            ReadEnv("a", Position(0, 0)),
            Position(0, 0),
        )
        val numberAssignation = Assignation(
            Declaration("env2", "number", DeclarationType.LET_KEYWORD, Position(0, 0)),
            ReadEnv("b", Position(0, 0)),
            Position(0, 0),
        )
        val booleanAssignation = Assignation(
            Declaration("env3", "boolean", DeclarationType.LET_KEYWORD, Position(0, 0)),
            ReadEnv("c", Position(0, 0)),
            Position(0, 0),
        )

        val result = nodeToParsingResult(stringAssignation)
        val result2 = nodeToParsingResult(numberAssignation)
        val result3 = nodeToParsingResult(booleanAssignation)

        val envVariables = mapOf("a" to "Diego", "b" to "10.0", "c" to "true")
        val interpreterForEnv = Interpreter(collector, MainStringInputProvider(iterator { }), envVariables)
        val interpreterResult = interpreterForEnv.interpret(sequenceOf(result, result2, result3))
        interpreterResult.toList()

        assertEquals(Triple("Diego", "string", true), interpreterForEnv.getVariableMap()["env"])
        assertEquals(Triple(10.0, "number", true), interpreterForEnv.getVariableMap()["env2"])
        assertEquals(Triple(true, "boolean", true), interpreterForEnv.getVariableMap()["env3"])
    }

    @Test
    fun testFallingReadEnv() {
        val numberAssignation = Assignation(
            Declaration("env", "number", DeclarationType.LET_KEYWORD, Position(0, 0)),
            ReadEnv("b", Position(0, 0)),
            Position(0, 0),
        )
        val booleanAssignation = Assignation(
            Declaration("env2", "boolean", DeclarationType.LET_KEYWORD, Position(0, 0)),
            ReadEnv("c", Position(0, 0)),
            Position(0, 0),
        )

        val result = nodeToParsingResult(numberAssignation)
        val result2 = nodeToParsingResult(booleanAssignation)

        val envVariables = mapOf("b" to "p1", "c" to "truee")
        val interpreterForEnv = Interpreter(collector, MainStringInputProvider(iterator { }), envVariables)
        val interpreterResult = interpreterForEnv.interpret(sequenceOf(result, result2))
        val errorList = ArrayList<Exception>()

        for (res in interpreterResult) {
            if (res.hasException()) {
                errorList.add(res.getException())
                println(res.getException().message)
            }
        }

        assertTrue { errorList.size == 2 }

        val error = errorList[0] as InterpreterException
        assertEquals("Invalid number format", error.message)
        val error2 = errorList[1] as InterpreterException
        assertEquals("Invalid boolean format", error2.message)
    }

    @Test
    fun testReadInput() {
        val message = Assignation(
            Declaration("message", "string", DeclarationType.LET_KEYWORD, Position(0, 0)),
            Literal("What's your name?", Position(0, 0), LiteralType.STRING),
            Position(0, 0),
        )
        val stringAssignation = Assignation(
            Declaration("a", "string", DeclarationType.LET_KEYWORD, Position(0, 0)),
            ReadInput(Identifier("message", Position(0, 0)), Position(0, 0)),
            Position(0, 0),
        )

        val result = nodeToParsingResult(message)
        val result2 = nodeToParsingResult(stringAssignation)

        val inputProvider = MainStringInputProvider(listOf("Diego").iterator())
        val interpreterForInp = Interpreter(collector, inputProvider, mapOf())
        val interpreterResult = interpreterForInp.interpret(sequenceOf(result, result2))
        interpreterResult.toList()

        assertEquals(Triple("Diego", "string", true), interpreterForInp.getVariableMap()["a"])
    }
}

import nodes.Assignation
import nodes.BinaryNode
import nodes.CallNode
import nodes.Declaration
import nodes.Identifier
import nodes.Literal

class ExpressionCheckerVisitor(private val variableTypeMap: MutableMap<String, String>) : ExpressionVisitor {
    override fun visitDeclaration(expression: Declaration): CheckAstResult {
        val variableName = expression.getName()
        val variableType = expression.getType()
        if (variableTypeMap.containsKey(variableName)) {
            return CheckAstResult(false, "Variable $variableName is already declared", "Declaration")
        }
        variableTypeMap[variableName] = variableType
        return CheckAstResult(true, "", "Declaration")
    }

    override fun visitLiteral(expression: Literal): CheckAstResult {
        val value = expression.getValue()
        if (value is String) {
            return CheckAstResult(true, "", "string")
        }
        if (value is Int) {
            return CheckAstResult(true, "", "number")
        }
        return CheckAstResult(false, "Invalid literal", "Literal")
    }

    override fun visitBinaryExp(expression: BinaryNode): CheckAstResult {
        val left = expression.getLeft()
        val right = expression.getRight()
        val leftResult = left.accept(this) as CheckAstResult
        val rightResult = right.accept(this) as CheckAstResult
        val operator = expression.getOperator()
        if (operator.getType() != TokenType.PLUS) {
            if (leftResult.getResultType() != rightResult.getResultType()) {
                return CheckAstResult(false, "Binary operation not supported", "invalid")
            }
        }
        if (leftResult.getResultType() == "string" || rightResult.getResultType() == "string") {
            return CheckAstResult(true, "", "string")
        }

        return CheckAstResult(true, "", "number")
    }

    override fun visitAssignment(expression: Assignation): Any {
        val (name, type) = if (expression.getDeclaration() is Identifier) {
            val identifierName = (expression.getDeclaration() as Identifier).getName()
            val type = variableTypeMap[identifierName] ?: return CheckAstResult(
                false,
                "variable $identifierName does not exist",
                "not defined",
            )
            Pair(identifierName, type)
        } else {
            val declaration = expression.getDeclaration() as Declaration
            val isError: CheckAstResult = declaration.accept(this) as CheckAstResult
            if (!isError.isPass()) {
                return CheckAstResult(false, isError.getMessage(), "Declaration")
            }
            Pair(declaration.getName(), declaration.getType())
        }
        val value = expression.getValue()
        val valueResult = value.accept(this) as CheckAstResult
        if (!valueResult.isPass()) {
            return CheckAstResult(
                false,
                valueResult.getMessage(),
                valueResult.getResultType(),
            )
        }
        if (valueResult.getResultType() != type) {
            return CheckAstResult(
                false,
                "Invalid type: expected $type got ${valueResult.getResultType()} on variable $name",
                "Assignation",
            )
        }
        return CheckAstResult(true, "", valueResult.getResultType())
    }

    override fun visitCallExp(expression: CallNode): Any {
        val functionName = expression.getFunc()
        val arguments = expression.getArguments()
        if (functionName != "println") {
            return CheckAstResult(false, "Function $functionName is not defined", "Print")
        }
        if (arguments.size > 1) {
            return CheckAstResult(false, "Invalid number of arguments", "Print")
        }
        return CheckAstResult(true, "", "Print")
    }

    override fun visitIdentifier(expression: Identifier): CheckAstResult {
        val variableName = expression.getName()
        if (!variableTypeMap.containsKey(variableName)) {
            return CheckAstResult(false, "Variable $variableName is not declared", "Identifier")
        }
        return CheckAstResult(true, "", "Identifier")
    }
}

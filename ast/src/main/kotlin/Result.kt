sealed interface Result

data class IdentifierResult(val nameAndType: Pair<String, String>, val value: Any) : Result

data class DeclarationResult(val name: String, val type: String) : Result

data class LiteralResult(val value: Any) : Result

data class SuccessResult(val message: String) : Result

class InterpreterException(override val message: String) : RuntimeException(message)


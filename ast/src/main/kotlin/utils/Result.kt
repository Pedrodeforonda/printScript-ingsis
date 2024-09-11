package utils

sealed interface Result

data class IdentifierResult(val variable: Triple<String, String, Boolean>, val value: Any) : Result

data class DeclarationResult(val variable: Triple<String, String, Boolean>) : Result

data class LiteralResult(val value: Any) : Result

data class ReadResult(val value: String) : Result

data class SuccessResult(val message: String) : Result

data class StringResult(val value: String) : Result

class CheckAstResult(private val isPassed: Boolean, private val message: String, private val resultType: String) :
    Result {

    fun isPass(): Boolean {
        return isPassed
    }

    fun getMessage(): String {
        return message
    }

    fun getResultType(): String {
        return resultType
    }
}

class LinterResult(
    private val message: String?,
    private val hasError: Boolean,
) : Result {

    fun hasError(): Boolean {
        return hasError
    }

    fun getMessage(): String {
        return message ?: ""
    }
}

class InterpreterException(override val message: String) : RuntimeException(message), Result

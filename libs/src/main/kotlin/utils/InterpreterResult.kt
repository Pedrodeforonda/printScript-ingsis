package utils
class InterpreterResult(
    private val println: String?,
    private val exception: Exception?,
    private val percentageProcessed: Double,
) {

    fun hasPrintln(): Boolean {
        return println != null
    }

    fun getPrintln(): String {
        return println!!
    }

    fun hasException(): Boolean {
        return exception != null
    }

    fun getException(): Exception {
        return exception!!
    }

    fun getPercentageProcessed(): Double {
        return percentageProcessed
    }
}

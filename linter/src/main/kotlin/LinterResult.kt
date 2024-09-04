package main.kotlin

class LinterResult(
    private val message: String?,
    private val hasError: Boolean,
) {

    fun hasError(): Boolean {
        return hasError
    }

    fun getMessage(): String {
        return message ?: ""
    }
}

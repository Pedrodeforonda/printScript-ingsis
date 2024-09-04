package main.kotlin.main

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

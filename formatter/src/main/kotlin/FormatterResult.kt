class FormatterResult(
    private val message: String,
    private val hasError: Boolean,
    private val hasNext: Boolean? = false,
) {
    override fun toString(): String {
        return "FormatterResult(message='$message', hasError='$hasError')"
    }

    fun hasError(): Boolean {
        return hasError
    }

    fun getMessage(): String {
        return message
    }

    fun hasNext(): Boolean {
        return hasNext ?: false
    }
}

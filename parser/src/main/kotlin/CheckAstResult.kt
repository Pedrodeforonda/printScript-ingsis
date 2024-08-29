class CheckAstResult(private val isPassed: Boolean, private val message: String, private val resultType: String) {

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

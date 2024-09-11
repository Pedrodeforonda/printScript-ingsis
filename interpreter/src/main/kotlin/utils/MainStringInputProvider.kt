package utils

class MainStringInputProvider(private val inputs: Iterator<String>) : StringInputProvider {
    override fun input(message: String): String {
        return if (inputs.hasNext()) {
            inputs.next()
        } else {
            throw InterpreterException("No more inputs")
        }
    }
}

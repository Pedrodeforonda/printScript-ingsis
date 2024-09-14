package utils

class InteractiveInputProvider : StringInputProvider {
    override fun input(message: String): String {
        val output = readln()
        return output
    }
}

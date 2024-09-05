package utils

class PrintlnCollector {
    private val prints = mutableListOf<String>()

    fun addPrint(value: String) {
        prints.add(value)
    }

    fun yieldPrints(): Sequence<String> = sequence {
        for (print in prints) {
            yield(print)
        }
    }

    fun hasPrints(): Boolean = prints.isNotEmpty()
}

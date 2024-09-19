package utils

class PercentageCollector {

    private var totalBytes: Int = 0
    private var readBytes: Int = 0

    fun updateTotalBytes(bytes: Int) {
        totalBytes = bytes
    }

    fun updateReadBytes(bytes: Int) {
        readBytes += bytes
    }

    fun getPercentage(): Double {
        return if (totalBytes == 0) 0.0 else (readBytes.toDouble() / totalBytes) * 100
    }

    fun reset() {
        totalBytes = 0
        readBytes = 0
    }
}

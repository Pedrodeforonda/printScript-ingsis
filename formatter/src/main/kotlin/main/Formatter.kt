package main

import java.io.BufferedWriter

interface Formatter {

    fun formatCode(tokens: Token, config: FormatterConfigReader, fileOutputWriter: BufferedWriter): FormatterResult
}

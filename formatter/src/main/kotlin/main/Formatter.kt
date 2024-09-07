package main

import java.io.Writer

interface Formatter {

    fun formatCode(tokens: Token, config: FormatterConfigReader, fileOutputWriter: Writer): FormatterResult
}

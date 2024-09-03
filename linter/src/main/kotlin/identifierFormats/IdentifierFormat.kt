package main.kotlin.identifierFormats

import java.util.regex.Pattern

interface IdentifierFormat {
    fun getPattern(): Pattern
    fun getFormat(): String
}

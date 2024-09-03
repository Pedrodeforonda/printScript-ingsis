package main.kotlin.identifierFormats

import java.util.regex.Pattern

class SnakeCase : IdentifierFormat {

    override fun getPattern(): Pattern {
        return Pattern.compile("^[a-z]+(_[a-z]+)*$")
    }

    override fun getFormat(): String {
        return "snake_case"
    }
}

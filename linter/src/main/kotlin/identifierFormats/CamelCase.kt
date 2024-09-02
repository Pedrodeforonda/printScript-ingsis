package main.kotlin.identifierFormats

import java.util.regex.Pattern

class CamelCase: IdentifierFormat {

    override fun getPattern(): Pattern {
        return Pattern.compile("^[a-z]+([A-Z][a-z]*)*$")
    }

    override fun getFormatName(): String {
        return "camelCase"
    }
}
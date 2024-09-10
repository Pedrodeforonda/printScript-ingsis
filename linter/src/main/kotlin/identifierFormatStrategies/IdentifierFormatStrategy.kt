package identifierFormatStrategies

import java.util.regex.Pattern

interface IdentifierFormatStrategy {
    fun getPattern(): Pattern
    fun getFormat(): String
}

package identifierFormatStrategies

import java.util.regex.Pattern

class CamelCaseStrategy : IdentifierFormatStrategy {

    override fun getPattern(): Pattern {
        return Pattern.compile("^[a-z]+([A-Z][a-z]*)*$")
    }

    override fun getFormat(): String {
        return "camel case"
    }
}

class Formatter {

    fun formatCode(input: String, config: FormatterConfigReader): String {
        var output = input

        // Espacio antes del ":"
        if (config.spaceBeforeColon) {
            output = output.replace(Regex("\\s*:"), " :")
        } else {
            output = output.replace(Regex("\\s*:"), ":")
        }

        // Espacio después del ":"
        if (config.spaceAfterColon) {
            output = output.replace(Regex(":\\s*"), ": ")
        } else {
            output = output.replace(Regex(":\\s*"), ":")
        }

        // Espacio antes y después del "="
        if (config.spaceBeforeAssignment) {
            output = output.replace(Regex("\\s*="), " =")
        } else {
            output = output.replace(Regex("\\s*="), "=")
        }

        if (config.spaceAfterAssignment) {
            output = output.replace(Regex("=\\s*"), "= ")
        } else {
            output = output.replace(Regex("=\\s*"), "=")
        }

        // Salto de línea antes de "println"
        output = output.replace(Regex("(?<!\\n)+println"), "println")

        // Un solo espacio entre tokens
        output = output.replace(Regex("\\s+"), " ")

        // Espacio antes y después de operadores
        output = output.replace(Regex("\\s*(\\+|-|\\*|/)\\s*"), " $1 ")

        // Salto de línea después de ";"
        output = output.replace(";", ";\n")

        return output.trim()
    }
}

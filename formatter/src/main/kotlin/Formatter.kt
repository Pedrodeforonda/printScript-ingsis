interface Formatter {

    fun formatCode(input: String, config: FormatterConfigReader): String
}

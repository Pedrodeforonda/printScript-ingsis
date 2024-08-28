package main.kotlin

import java.io.File
import java.util.regex.Pattern

class Linter(private val config: LinterConfig) {

    private val camelCasePattern = Pattern.compile("^[a-z]+([A-Z][a-z]*)*$")
    private val snakeCasePattern = Pattern.compile("^[a-z]+(_[a-z]+)*$")

    fun checkIdentifier(identifier: String): Boolean {
        return when (config.identifierFormat) {
            "camelCase" -> camelCasePattern.matcher(identifier).matches()
            "snake_case" -> snakeCasePattern.matcher(identifier).matches()
            else -> false
        }
    }

    fun checkPrintlnUsage(line: String): Boolean {
        if (!config.restrictPrintln) return true
        val printlnPattern = Pattern.compile("""println\(([^)]+)\)""")
        val matcher = printlnPattern.matcher(line)
        if (matcher.find()) {
            val argument = matcher.group(1).trim()
            return argument.matches(Regex("""[a-zA-Z_][a-zA-Z0-9_]*""")) ||
                argument.matches(Regex("""".*"""")) ||
                argument.matches(
                    Regex("""\d+"""),
                )
        }
        return true
    }

    fun lintFile(file: File): List<String> {
        val errors = mutableListOf<String>()
        file.readLines().forEachIndexed { index, line ->
            val words = line.split(Regex("\\W+"))
            words.forEach { word ->
                if (!checkIdentifier(word)) {
                    errors.add("Line ${index + 1}, Column ${line.indexOf(word) + 1}: Invalid identifier format: $word")
                }
            }
            if (!checkPrintlnUsage(line)) {
                errors.add("Line ${index + 1}: Invalid println usage")
            }
        }
        return errors
    }
}

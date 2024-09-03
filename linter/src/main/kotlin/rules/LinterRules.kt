package main.kotlin.rules

data class LinterRules(val rules: List<LinterRule> = listOf(IdentifierFormatRule()))

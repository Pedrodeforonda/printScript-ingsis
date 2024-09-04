package main.kotlin.main

import main.kotlin.identifierFormats.CamelCaseStrategy
import main.kotlin.identifierFormats.IdentifierFormatStrategy
import main.kotlin.identifierFormats.SnakeCaseStrategy

data class IdentifierFormats(
    val formats: List<IdentifierFormatStrategy> = listOf(CamelCaseStrategy(), SnakeCaseStrategy()),
)

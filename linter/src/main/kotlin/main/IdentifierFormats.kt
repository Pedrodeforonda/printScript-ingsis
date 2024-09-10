package main

import identifierFormatStrategies.CamelCaseStrategy
import identifierFormatStrategies.IdentifierFormatStrategy
import identifierFormatStrategies.SnakeCaseStrategy

data class IdentifierFormats(
    val formats: List<IdentifierFormatStrategy> = listOf(CamelCaseStrategy(), SnakeCaseStrategy()),
)

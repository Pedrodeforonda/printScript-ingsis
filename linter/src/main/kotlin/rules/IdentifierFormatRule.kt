package main.kotlin.rules

import main.kotlin.identifierFormats.IdentifierFormatStrategy
import main.kotlin.main.LinterResult
import nodes.Identifier
import nodes.Node

class IdentifierFormatRule(private val identifierFormat: IdentifierFormatStrategy) : LinterRule {

    override fun lintCode(node: Node): LinterResult {
        // Check if the identifier name matches the pattern format
        if (!identifierFormat.getPattern().matcher((node as Identifier).getName()).matches()) {
            return LinterResult(
                "Invalid identifier format: ${node.getName()}" +
                    " in line ${node.getPos().getLine()} column ${node.getPos().getColumn()}",
                true,
            )
        }
        return LinterResult(null, false)
    }
}

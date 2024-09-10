package rules

import nodes.Node
import utils.LinterResult

interface LinterRule {
    fun lintCode(node: Node): LinterResult
}

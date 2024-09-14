package rules

import dataObjects.LinterResult
import nodes.Node

interface LinterRule {
    fun lintCode(node: Node): LinterResult
}

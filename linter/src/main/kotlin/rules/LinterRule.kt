package main.kotlin.rules

import main.kotlin.main.LinterResult
import nodes.Node

interface LinterRule {
    fun lintCode(node: Node): LinterResult
}

package parsers

import Parser
import Precedence
import Token
import nodes.Assignation
import org.example.nodes.Node

class AssignationParser: Infix {
    override fun parse(parser: Parser, left: Node, token: Token): Node {
        val right = parser.parseExpression()
        return Assignation(left, right)
    }

    override fun getPrecedence(): Int {
        return Precedence.ASSIGNATION
    }
}
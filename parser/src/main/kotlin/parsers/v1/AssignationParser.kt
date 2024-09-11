package parsers.v1

import main.Infix
import main.Parser
import main.Precedence
import main.Token
import nodes.Assignation
import nodes.Node

class AssignationParser : Infix {
    override fun parse(parser: Parser, left: Node, token: Token): Node {
        val right = parser.parseExpression()
        return Assignation(left, right, token.getPosition())
    }

    override fun getPrecedence(): Int {
        return Precedence.ASSIGNATION
    }
}

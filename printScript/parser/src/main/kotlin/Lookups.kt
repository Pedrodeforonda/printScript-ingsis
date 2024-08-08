import nodes.FuncCall
import nodes.Led
import nodes.Nud
import nodes.Stmt
import org.example.nodes.Addition
import org.example.nodes.Logical
import org.example.nodes.NodeTypes
import org.example.nodes.Println
import types.BindingPowers
import types.BindingPowers.BindingPowerTypes

class Lookups {
    private val nudMap: MutableMap<TokenType, Nud> = mutableMapOf()
    private val ledMap: MutableMap<TokenType, Led> = mutableMapOf()
    private val stmtMap: MutableMap<TokenType, Stmt> = mutableMapOf()
    private val PrintlnMap: MutableMap<TokenType, FuncCall> = mutableMapOf()
    private val bpMap: MutableMap<TokenType, BindingPowerTypes> = mutableMapOf()



    fun createTokenLookups() {
        // Logical
        nudMap[TokenType.AND] =


    }


    fun getNud(type: TokenType): Nud? {
        return nudMap[type]?.let { NodeTypes().getNud(it) }
    }

    fun getLed(type: TokenType): NodeTypes? {
        return ledMap[type]
    }

    fun getBP(type: TokenType): BindingPowerTypes? {
        return bpMap[type]
    }


}
package types

class BindingPowers {
    enum class BindingPowerTypes {
        ASSIGNATION,
        EXPRESSION,
        DECLARATION,
        CALLFUNC,
        STRING_TYPE,
        NUMBER_TYPE,
        SUM,
        SUB,
        MUL,
        DIV,
        MOD,
        POW,
        COMPARE,
        AND,
        OR,
        NOT,
        NEG,
        PARENTHESIS,
        NUMBER_LITERAL,
        STRING_LITERAL,

    }

    val bpMap : Map<BindingPowerTypes, Int> = mapOf(
        BindingPowerTypes.ASSIGNATION to 1,
        BindingPowerTypes.EXPRESSION to 2,
        BindingPowerTypes.DECLARATION to 3,
        BindingPowerTypes.CALLFUNC to 4,
        BindingPowerTypes.STRING_TYPE to 5,
        BindingPowerTypes.NUMBER_TYPE to 5,
        BindingPowerTypes.SUM to 6,
        BindingPowerTypes.SUB to 6,
        BindingPowerTypes.MUL to 7,
        BindingPowerTypes.DIV to 7,
        BindingPowerTypes.MOD to 7,
        BindingPowerTypes.POW to 8,
        BindingPowerTypes.COMPARE to 9,
        BindingPowerTypes.AND to 10,
        BindingPowerTypes.OR to 11,
        BindingPowerTypes.NOT to 12,
        BindingPowerTypes.NEG to 13,
        BindingPowerTypes.PARENTHESIS to 14,
        BindingPowerTypes.NUMBER_LITERAL to 15,
        BindingPowerTypes.STRING_LITERAL to 15,
    )

    fun getBP(type: BindingPowerTypes): Int {
        return bpMap[type]!!
    }
}
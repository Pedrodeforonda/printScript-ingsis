package org.example

import Token

class Lexer(private val text: String,
            private val tokenStrategies: ClassicTokenStrategies,
            private val pos: Int = 0,
            private val tokenList: List<Token> = emptyList()
) {
    val currentChar: Char? = if (pos > text.length - 1) null else text[pos]

    fun goToNextPos(): Lexer{
        val newPos = pos + 1
        if (newPos > text.length - 1) {
            return Lexer(text, tokenStrategies, pos, getTokens()) //currentChar = null
        } else {
            return Lexer(text, tokenStrategies, newPos, getTokens())
        }
    }
    private fun canSkip(): Boolean {
        return currentChar!!.isWhitespace() || currentChar == '\r' || currentChar == '\n'
    }

    fun getPos(): Int {
        return pos
    }
    fun getText(): String {
        return text
    }

    fun getTokenStrategies(): ClassicTokenStrategies {
        return tokenStrategies
    }

    fun getTokens(): List<Token> {
        return tokenList
    }
    fun tokenizeAll2(lexer: Lexer): List<Token> { // funcion + imp del lexer
        return updateLexer(lexer).getTokens()
    }

    fun updateLexer(lexer: Lexer): Lexer {
        while (currentChar != null) {
            if (canSkip()) {return updateLexer(goToNextPos())} //gotonextpos es c el nuevo lexer
            for (tokenStrategy in tokenStrategies.getManagers()) {
                val newLexer = tokenStrategy.buildToken(lexer, "")
                if (newLexer != lexer) {
                    return updateLexer(newLexer)
                }
            }//buildToken devuelve un lexer con el token agregado a la lista de tokens y el nuevo pos y char actual
        }
        //devuelva rsta cuando es nulo pq ya se paso del length
        return lexer
    }
}
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
    private fun canSkip(lexer: Lexer): Boolean {
        return lexer.getChar()!!.isWhitespace() || lexer.getChar() == '\r' || lexer.getChar() == '\n'
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

    fun getChar(): Char? {
        return currentChar
    }

    fun getTokens(): List<Token> {
        return tokenList
    }
    fun tokenizeAll2(lexer: Lexer): List<Token> { // funcion + imp del lexer
        return updateLexer(lexer).getTokens()
    }

    fun nextCharNull(lexer: Lexer): Boolean {
        val nextLexer = Lexer(lexer.getText(), lexer.getTokenStrategies(), lexer.getPos() + 1, lexer.getTokens())
        return nextLexer.getChar() == null
    }

    fun updateLexer(lexer: Lexer): Lexer {
        while (lexer.getChar() != null) {
            if (canSkip(lexer)) {
                if (!nextCharNull(lexer)) {
                    return updateLexer(lexer.goToNextPos())
                }
                else {
                    return lexer
                }
            } //gotonextpos es c el nuevo lexer
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
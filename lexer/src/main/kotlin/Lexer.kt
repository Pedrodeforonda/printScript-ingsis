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
    fun nextCharNull(lexer: Lexer): Boolean { //chequea si el siguiente char es nulo
        val nextLexer = Lexer(lexer.getText(), lexer.getTokenStrategies(), lexer.getPos() + 1, lexer.getTokens())
        return nextLexer.getChar() == null
    }

    fun tokenizeAll(lexer: Lexer): List<Token> { // funcion + imp del lexer
        return updateLexer(lexer).getTokens()
    }

    fun updateLexer(lexer: Lexer): Lexer {
        while (lexer.getChar() != null) {
            if (canSkip(lexer)) {// veo si es un character que no necesita una token, y lo salteo
                if (!nextCharNull(lexer)) { //si el siguiente char es nulo, no se puede hacer goToNextPos
                    return updateLexer(lexer.goToNextPos())// come el siguiente char no es nulo, se hace goToNextPos, salteando el char que no necesita token
                }
                else {
                    return lexer //si el siguiente char es nulo, se devuelve el lexer actual, terminando la cadena y la funcion
                }
            } //gotonextpos es c el nuevo lexer
            for (tokenStrategy in tokenStrategies.getStrategies()) {
                val newLexer = tokenStrategy.buildToken(lexer, "")//intento de construir un token
                if (newLexer != lexer) { //si el buildToken devuelve un lexer distinto al actual, se actualiza el lexer, ya que se encontro un token
                    return updateLexer(newLexer) //el newlexer es el lexer actualizado, en donde se seguira iterando
                }
            }//buildToken devuelve un lexer con el token agregado a la lista de tokens y el nuevo pos y char actual
        }
        //devuelva rsta cuando es nulo pq ya se paso del length
        return lexer
    }
}
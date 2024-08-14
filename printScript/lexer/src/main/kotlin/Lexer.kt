package org.example

import Token

class Lexer(private val text: String, private val tokenManagers: ClassicTokenManagers) {
    private var pos = 0
    private var currentChar: Char? = null

    init {
        this.currentChar = text[pos]
    }

    fun goToNextPos() {
        pos++
        currentChar = if (pos > text.length - 1) null else text[pos]
    }

    private fun skip() {
        if (currentChar!!.isWhitespace() || currentChar == '\r' || currentChar == '\n'
            || currentChar == '(' || currentChar == ')') {
            goToNextPos()
        }
    }

     fun tokenizeAll(): List<Token> { //funcion + imp del lexer
            val tokens = mutableListOf<Token>()
            while (currentChar != null) {
                for(manager in tokenManagers.getManagers()){
                    val token = manager.buildToken(this)
                    if(token.getType() != TokenType.NULL_TYPE){
                        tokens.add(token)
                    }
                }
            }
            return tokens
        }
        fun getCurrentChar(): Char? {
            return currentChar
        }

}
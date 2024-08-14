package org.example

import Token
import org.example.tokenManagers.ClassicTokenManagers

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



    private fun string(): CharArray {
        val quote = currentChar
        goToNextPos()
        var result = ""
        while (currentChar != null && currentChar != quote) {
            result += currentChar
            goToNextPos()
        }
        goToNextPos()
        return result.toCharArray()
    }

    fun getNextToken(): Token {
        while (currentChar != null) {
            when {
                currentChar!!.isWhitespace() || currentChar == '\r' || currentChar == '\n'
                        || currentChar == '(' || currentChar == ')' -> {
                    skip()
                    continue
                }
            }

        }
        return Token(charArrayOf(' '), TokenType.NULL_TYPE)
    }


    fun tokenizeAll(): List<Token> { //funcion + imp del lexer
        val tokens = mutableListOf<Token>()
        while (currentChar != null) {
            for(manager in tokenManagers.getManagers()){
                val token = manager.BuildToken(this)
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
    fun getPos(): Int {
        return pos
    }
}
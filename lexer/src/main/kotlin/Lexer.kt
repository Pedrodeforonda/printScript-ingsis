package org.example

import Position
import Token
import java.io.BufferedReader

class Lexer(
    private val iterator: BufferedReader,
    private var pos: Int = 0,
    private val lexerPosition: Position = Position(1, 1),
) {
    private val text = iterator.readText()
    private var current: Char? = text.getOrNull(pos)

    fun goToNextPos() {
        pos += 1
        current = text.getOrNull(pos)
    }

    private fun canSkip(lexer: Lexer): Boolean {
        return lexer.getChar()!!.isWhitespace() || lexer.getChar() == '\r' || lexer.getChar() == '\n'
    }

    fun getPos(): Int {
        return pos
    }
    fun getText(): BufferedReader {
        return iterator
    }

    fun getChar(): Char? {
        return current
    }

    private fun nextCharNull(lexer: Lexer): Boolean { // chequea si el siguiente char es nulo
        val nextLexer =
            Lexer(lexer.getText(), lexer.getPos() + 1, lexerPosition)
        return nextLexer.getChar() == null
    }

    fun tokenizeAll(lexer: Lexer): Sequence<Token> = sequence { // funcion + imp del lexer
        while (lexer.getChar() != null) {
            val token = updateLexer(lexer)
            if (token != null) {
                println(token)
                yield(token)
            }
            goToNextPos()
        }
    }

    private fun updateLexer(lexer: Lexer): Token? {
        if (canSkip(lexer)) {
            return if (!nextCharNull(lexer)) {
                updateLexer(lexer.sumPos())
            } else {
                null
            }
        }
        for (tokenStrategy in ClassicTokenStrategies().listOfStrategies) {
            val newToken = tokenStrategy.buildToken(lexer, "", lexer.getLexerPosition())
            if (newToken != null) {
                return newToken
            }
        }
        return null
    }

    fun getLexerPosition(): Position {
        return Position(1, getPos() + 1)
    }

    fun sumPos(): Lexer {
        this.goToNextPos()
        return this
    }

    fun goToPreviousPos() {
        pos -= 1
        current = text.getOrNull(pos)
    }
}

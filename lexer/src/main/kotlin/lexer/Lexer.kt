package org.example.lexer

import main.Position
import main.Token
import java.io.BufferedReader

class Lexer(
    private val iterator: BufferedReader,
    private var pos: Int = 0,
    private var lexerPosition: Position = Position(1, 1),
) {
    private var current: Char? = iterator.read().toChar()
    private var lineBreak = 0
    private var currentColumn = 0

    fun goToNextPos() {
        pos += 1
        val read = iterator.read()
        if (read != -1) {
            current = read.toChar()
        } else {
            current = null
        }
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

    private fun nextCharNull(): Boolean {
        val read = iterator.read()
        current = read.toChar()
        if (read == -1) {
            current = null
            return true
        }
        return false
    }

    fun tokenizeAll(lexer: Lexer): Sequence<Token> = sequence { // funcion + imp del lexer
        while (lexer.getChar() != null) {
            val token = updateLexer(lexer)
            if (token != null) {
                yield(token)
            }
            if (current == '\n') {
                goToNextPos()
            }
            if (token == null) {
                goToNextPos()
            }
        }
    }

    private fun updateLexer(lexer: Lexer): Token? {
        if (canSkip(lexer)) {
            updateTokenPosition()
            if (!nextCharNull()) {
                sumPos()
                return updateLexer(lexer)
            } else {
                return null
            }
        }
        for (tokenStrategy in ClassicTokenStrategies().listOfStrategies) {
            val newToken = tokenStrategy.buildToken(lexer, "", lexer.tokenPosition())
            if (newToken != null) {
                updateTokenPosition()
                return newToken
            }
        }
        return null
    }

    private fun tokenPosition(): Position {
        return Position(lexerPosition.getLine(), getCurrentColumn())
    }

    private fun updateTokenPosition() {
        if (current == '\n') {
            lexerPosition = Position(lexerPosition.getLine() + 1, 0)
            lineBreak = pos + 1
            currentColumn = 0
        } else {
            currentColumn = pos - lineBreak
        }
    }

    private fun getCurrentColumn(): Int {
        currentColumn = pos - lineBreak + 1
        return currentColumn
    }

    private fun sumPos() {
        pos += 1
    }
}

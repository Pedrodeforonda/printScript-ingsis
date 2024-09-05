package org.example.lexer

import lexer.StrategyList
import main.Position
import main.Token
import java.io.BufferedReader

class Lexer(
    private val iterator: BufferedReader,
    private val strategyList: StrategyList,
    private var pos: Int = 0,
    private var lexerPosition: Position = Position(1, 1),
) {
    private val text = iterator.readText()
    private var current: Char? = text.getOrNull(pos)
    private var lineBreak = 0
    private var currentColumn = 0

    fun goToNextPos() {
        pos += 1
        current = text.getOrNull(pos)
    }

    private fun canSkip(lexer: Lexer): Boolean {
        return lexer.getChar()!!.isWhitespace() || lexer.getChar() == '\r' || lexer.getChar() == '\n'
    }

    fun getChar(): Char? {
        return current
    }

    private fun nextCharNull(): Boolean {
        return text.getOrNull(pos + 1) == null
    }

    fun tokenizeAll(lexer: Lexer): Sequence<Token> = sequence { // funcion + imp del lexer
        while (lexer.getChar() != null) {
            val token = updateLexer(lexer)
            if (token != null) {
                yield(token)
            }
            goToNextPos()
        }
    }

    private fun updateLexer(lexer: Lexer): Token? {
        if (canSkip(lexer)) {
            updateTokenPosition()
            if (!nextCharNull()) {
                return updateLexer(lexer.sumPos())
            } else {
                return null
            }
        }
        for (tokenStrategy in strategyList.getStrategies()) {
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

    private fun sumPos(): Lexer {
        this.goToNextPos()
        return this
    }

    fun goToPreviousPos() {
        pos -= 1
        current = text.getOrNull(pos)
    }
}

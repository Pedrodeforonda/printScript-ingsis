package lexer

import main.Position
import main.Token
import org.example.lexer.ClassicTokenStrategies
import utils.PercentageCollector
import java.io.BufferedReader

class Lexer(
    private val iterator: BufferedReader,
    private val streamByteLength: Int,
    private val percentageColector: PercentageCollector,
    private var pos: Int = 0,
    private var lexerPosition: Position = Position(1, 1),
) {
    private var current: Char? = iterator.read().toChar()
    private var lineBreak = 0
    private var currentColumn = 0
    init {
        percentageColector.reset()
        percentageColector.updateTotalBytes(streamByteLength)
        percentageColector.updateReadBytes(1)
    }

    fun goToNextPos() {
        pos += 1
        val read = iterator.read()
        if (read != -1) {
            current = read.toChar()
            percentageColector.updateReadBytes(1)
        } else {
            current = null
        }
    }

    private fun canSkip(): Boolean {
        return this.getChar()!!.isWhitespace() || this.getChar() == '\r' || this.getChar() == '\n'
    }

    fun getChar(): Char? {
        return current
    }

    private fun nextCharNull(): Boolean {
        val read = iterator.read()
        percentageColector.updateReadBytes(1)
        current = read.toChar()
        if (read == -1) {
            current = null
            return true
        }
        return false
    }

    fun tokenize(): Sequence<Token> = sequence { // funcion + imp del lexer
        while (current != null) {
            val token = generateToken()
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

    private fun generateToken(): Token? {
        if (canSkip()) {
            updateTokenPosition()
            if (!nextCharNull()) {
                sumPos()
                return generateToken()
            } else {
                return null
            }
        }
        for (tokenStrategy in ClassicTokenStrategies().listOfStrategies) {
            val newToken = tokenStrategy.buildToken(this, "", this.tokenPosition())
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

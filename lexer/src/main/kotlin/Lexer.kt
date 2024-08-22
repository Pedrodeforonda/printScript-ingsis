package org.example

import Token

class Lexer(
    private val text: String,
    private val tokenStrategies: ClassicTokenStrategies,
    private val pos: Int = 0,
    private val tokenList: List<Token> = emptyList(),
) {
    val currentChar: Char? = if (pos > text.length - 1) null else text[pos]

    fun goToNextPos(): Lexer {
        val newPos = pos + 1
        if (newPos > text.length - 1) {
            return Lexer(text, tokenStrategies, pos, getTokens()) // currentChar = null
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
    fun nextCharNull(lexer: Lexer): Boolean { // chequea si el siguiente char es nulo
        val nextLexer = Lexer(lexer.getText(), lexer.getTokenStrategies(), lexer.getPos() + 1, lexer.getTokens())
        return nextLexer.getChar() == null
    }

    fun tokenizeAll(lexer: Lexer): List<Token> { // funcion + imp del lexer
        return updateLexer(lexer).getTokens()
    }

    /**
     * Updates the lexer by iterating through the characters and applying token strategies.
     *
     * - If the current character can be skipped (whitespace, carriage return, newline), it moves to the next position.
     * - If the next character is null, it returns the current lexer.
     * - If a token is successfully built by a token strategy, it updates the lexer and continues iterating.
     * - The function returns the updated lexer when the end of the text is reached.
     *
     * @param lexer The current lexer instance.
     * @return The updated lexer instance.
     */
    private fun updateLexer(lexer: Lexer): Lexer {
        while (lexer.getChar() != null) {
            if (canSkip(lexer)) {
                if (!nextCharNull(lexer)) {
                    return updateLexer(lexer.goToNextPos())
                } else {
                    return lexer
                }
            }
            for (tokenStrategy in tokenStrategies.getStrategies()) {
                val newLexer = tokenStrategy.buildToken(lexer, "")
                if (newLexer != lexer) {
                    return updateLexer(newLexer)
                }
            }
        }
        return lexer
    }
}

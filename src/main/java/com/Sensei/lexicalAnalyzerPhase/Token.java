package com.Sensei.lexicalAnalyzerPhase;

public record Token(TokenType type, String lexeme, Object literal, int line, int startPosition, int endPosition) {
    @Override
    public String toString() {

        if (literal != null) {
            return String.format("%-15s : %s (%s)", type, lexeme, literal);
        }
        return String.format("%-15s : %s", type, lexeme);
    }
}
package com.Sensei.lexicalAnalyzerPhase;

import java.util.ArrayList;
import java.util.List;
import com.Sensei.Main;

public class Lexer {
    private String source;
    private List<Token> tokens = new ArrayList<>();
    private int currentPosition = 0;
    private int startPosition = 0;
    private int line = 1;

    public Lexer(String source) {
        this.source = source;
    }


    private boolean isAtEnd() {
        return currentPosition >= source.length();
    }

    public List<Token> tokenize() {
        while (!isAtEnd()) {
            startPosition = currentPosition;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line, startPosition, currentPosition));
        return tokens;
    }

    private char advance() {
        return source.charAt(currentPosition++);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(startPosition, currentPosition);
        tokens.add(new Token(type, text, literal, line, startPosition, currentPosition));
    }

    private boolean match(char c) {
        if (isAtEnd()) return false;
        if (source.charAt(currentPosition) != c) return false;
        currentPosition++;
        return true;
    }

    private char lookahead() {
        if (isAtEnd()) return '\0';
        return source.charAt(currentPosition);
    }

    private char lookaheadNext() {
        if (currentPosition + 1 >= source.length()) return '\0';
        return source.charAt(currentPosition + 1);
    }

    private void tokenizeString() {
        while (lookahead() != '"' && !isAtEnd()) {
            if (lookahead() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            Main.error(line, "Unterminated string.");
            return;
        }

        advance(); // The closing "

        // Trim the surrounding quotes
        String value = source.substring(startPosition + 1, currentPosition - 1);
        addToken(TokenType.STRING, value);
    }

    private void tokenizeDigit() {
        while (Character.isDigit(lookahead())) advance();

        // Look for a fractional part.
        if (lookahead() == '.' && Character.isDigit(lookaheadNext())) {
            advance(); // Consume the "."
            while (Character.isDigit(lookahead())) advance();
        }

        addToken(TokenType.NUMBER, Double.parseDouble(source.substring(startPosition, currentPosition)));
    }

    private void tokenizeIdentifier() {
        while (Character.isLetterOrDigit(lookahead())) advance();

        String text = source.substring(startPosition, currentPosition);
        TokenType type = TokenType.matchKeyword(text);
        if (type == null) type = TokenType.IDENTIFIER;

        addToken(type, null);
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(TokenType.LEFT_PAREN, null); break;
            case ')': addToken(TokenType.RIGHT_PAREN, null); break;
            case '{': addToken(TokenType.LEFT_BRACE, null); break;
            case '}': addToken(TokenType.RIGHT_BRACE, null); break;
            case ',': addToken(TokenType.COMMA, null); break;
            case '.': addToken(TokenType.DOT, null); break;
            case '-': addToken(TokenType.MINUS, null); break;
            case '+': addToken(TokenType.PLUS, null); break;
            case ';': addToken(TokenType.SEMICOLON, null); break;
            case '*': addToken(TokenType.STAR, null); break;
            case '!': addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG, null); break;
            case '=': addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL, null); break;
            case '<': addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS, null); break;
            case '>': addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER, null); break;
            case '/':
                if (match('/')) {
                    // Single-line comment
                    while (lookahead() != '\n' && !isAtEnd()) advance();
                } else if (match('*')) {
                    // Multi-line comment (تصليح اللوجيك هنا مهم)
                    while (!isAtEnd()) {
                        if (lookahead() == '*' && lookaheadNext() == '/') {
                            advance(); // consume *
                            advance(); // consume /
                            break;
                        }
                        if (lookahead() == '\n') line++;
                        advance();
                    }
                } else {
                    addToken(TokenType.SLASH, null);
                }
                break;

            case ' ':
            case '\r':
            case '\t':
                break;

            case '\n':
                line++;
                break;

            case '"': tokenizeString(); break;

            default:
                if (Character.isDigit(c)) {
                    tokenizeDigit();
                } else if (Character.isLetter(c)) {
                    tokenizeIdentifier();
                } else {
                    Main.error(line, "Unexpected character: '" + c + "'");
                }
                break;
        }
    }
}
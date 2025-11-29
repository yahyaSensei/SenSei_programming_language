package com.Sensei.lexicalAnalyzerPhase;
import java.util.HashMap;
import java.util.Map;

public enum TokenType {

    AND("and"),
    CLASS("class"),
    ELSE("else"),
    FALSE("false"),
    FOR("for"),
    FUN("fun"),
    IF("if"),
    NIL("nil"),
    OR("or"),
    PRINT("print"),
    RETURN("return"),
    SUPER("super"),
    THIS("this"),
    TRUE("true"),
    VAR("var"),
    WHILE("while"),


    LEFT_PAREN(null), RIGHT_PAREN(null), LEFT_BRACE(null), RIGHT_BRACE(null),
    COMMA(null), DOT(null), MINUS(null), PLUS(null), SEMICOLON(null), SLASH(null), STAR(null),
    BANG(null), BANG_EQUAL(null), EQUAL(null), EQUAL_EQUAL(null),
    GREATER(null), GREATER_EQUAL(null), LESS(null), LESS_EQUAL(null),
    IDENTIFIER(null), STRING(null), NUMBER(null), EOF(null);


    public final String keywordName;


    private static final Map<String, TokenType> keywords = new HashMap<>();


    static {
        for (TokenType type : values()) {
            if (type.keywordName != null) {
                keywords.put(type.keywordName, type);
            }
        }
    }


    TokenType(String keywordName) {
        this.keywordName = keywordName;
    }


    public static TokenType matchKeyword(String text) {
        return keywords.get(text);
    }
}
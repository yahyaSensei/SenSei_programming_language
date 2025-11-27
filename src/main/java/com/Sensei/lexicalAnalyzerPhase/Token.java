package com.Sensei.lexicalAnalyzerPhase;

public record Token(TokenType type,String lexeme,Object literal,int line,int startPosition,int endPosition) {}

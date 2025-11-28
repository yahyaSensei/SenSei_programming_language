package com.Sensei.lexicalAnalyzerPhase;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String source;
    private List<Token> tokens=new ArrayList<Token>();
    private int currentPosition=0;
    private int startPosition=0;
    private int line=1;
    public Lexer(String source){
        this.source=source;
    }
    private boolean isAtEnd(){
        return source.length()>currentPosition;
    }
    public List<Token> tokenize(){
        while(!isAtEnd()){
        startPosition=currentPosition;
        }
        tokens.add(new Token(TokenType.EOF,"",null,line,startPosition,currentPosition));
        return tokens;
    }
}

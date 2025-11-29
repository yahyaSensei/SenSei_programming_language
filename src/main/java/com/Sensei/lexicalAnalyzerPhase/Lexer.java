package com.Sensei.lexicalAnalyzerPhase;

import java.util.ArrayList;
import java.util.List;
import com.Sensei.Main;

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
        scanToken();
        }
        tokens.add(new Token(TokenType.EOF,"",null,line,startPosition,currentPosition));
        return tokens;
    }

    private char advance(){
        return source.charAt(currentPosition++);
    }

    private void addToken(TokenType type,Object literal){
        String text=source.substring(startPosition,currentPosition);
        tokens.add(new Token(type,text,literal,line,startPosition,currentPosition));
        startPosition=currentPosition;
    }

    private boolean match(char c){
        if(isAtEnd()){return false;}
        if(source.charAt(currentPosition)!=c){return false;}
        currentPosition++;
        return true;
    }

    private char lookahead(){
        if(isAtEnd()){return '\0';}
        return source.charAt(currentPosition);
    }

    private char lookaheadnext(){
        if(currentPosition+1>source.length()){return '\0';}
        return source.charAt(currentPosition+1);
    }

    private void tokenizeString(){
        while(!isAtEnd() && lookahead()!='"'){
            if(lookahead()=='\n'){
                line++;
            }
            advance();
        }
        if(isAtEnd()){
            Main.error(line,"Unexpected end of string");
        }
        if(lookahead()=='"'){
            tokens.add(new Token(TokenType.STRING,source.substring(startPosition+1,currentPosition-1),source.substring(startPosition,currentPosition),line,startPosition,currentPosition));
            startPosition=currentPosition;
        }
    }

    private void tokenizeDigit(){
        while(!isAtEnd() && Character.isDigit(lookahead())){advance();}

        if(lookahead()=='.' && Character.isDigit(lookaheadnext())){
            advance();
        }
        while(!isAtEnd() && Character.isDigit(lookahead())){advance();}

        addToken(TokenType.NUMBER,Double.parseDouble(source.substring(startPosition,currentPosition)));

        startPosition=currentPosition;
    }

    private void tokenizeIdentifier(){
        while(!isAtEnd() && Character.isLetter(lookahead())){advance();}
        String text=source.substring(startPosition,currentPosition);
        TokenType type=TokenType.matchKeyword(text);
        if (type==null) {type=TokenType.IDENTIFIER;}
        tokens.add(new Token(type,text,null,line,startPosition,currentPosition));
    }

    private void scanToken(){
        char c=advance();
        switch(c){
            case '(': addToken(TokenType.LEFT_PAREN,null); break;
            case ')': addToken(TokenType.RIGHT_PAREN,null); break;
            case '{': addToken(TokenType.LEFT_BRACE,null); break;
            case '}': addToken(TokenType.RIGHT_BRACE,null); break;
            case ',': addToken(TokenType.COMMA,null); break;
            case '.': addToken(TokenType.DOT,null); break;
            case '-': addToken(TokenType.MINUS,null); break;
            case '+': addToken(TokenType.PLUS,null); break;
            case ';': addToken(TokenType.SEMICOLON,null); break;
            case '*': addToken(TokenType.STAR,null); break;
            case '!': addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG,null);break;
            case '=': addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL,null);break;
            case '<': addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS,null);break;
            case '>': addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER,null);break;
            case '/':
                if(match('/')){
                    while(lookahead()!='\n' && !isAtEnd())advance();
                }else if(match('*')){
                    while(lookahead()!='*' && lookaheadnext()!='/' && !isAtEnd())advance();
                }
                else{addToken(TokenType.SLASH,null);}
                startPosition=currentPosition;
                break;
            case '"':tokenizeString();break;

            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;

            case '\n':
                line++;
                break;

            default:
                if(Character.isDigit(c)){
                    tokenizeDigit();
                }else if(Character.isLetter(c)){
                    tokenizeIdentifier();
                }
                else{Main.error(line,"Unexpected character.");}
        }
    }

}

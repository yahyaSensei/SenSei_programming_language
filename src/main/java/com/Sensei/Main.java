package com.Sensei;

import com.Sensei.lexicalAnalyzerPhase.Token;

import java.io.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    static boolean hadError=false;
    private static void error(int line, String message){
        report(line,"",message);
    }
    private static void report(int line,String where, String message){
        System.out.println("\u001B[91m"+"\u001B[1m"+"[line " + line + "] Error" + where + ": " + message+"\u001B[0m");
    }
    private static void run(String source){
        Scanner scanner=new Scanner(source);
        List<Token> tokens=new ArrayList<Token>();
        for(Token token:tokens){
            System.out.println(token);
        }
    }
    private static void runFile(String path)throws IOException {
        byte[] bytes= Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if(hadError)System.exit(1);
    }
    private static void runREPL() throws IOException{
         String green = "\u001B[92m";
         String reset = "\u001B[0m";
         String banner = """
    %s
      _____             _____       _ 
     / ____|           / ____|     (_)
    | (___   ___ _ __ | (___   ___  _ 
     \\___ \\ / _ \\ '_ \\ \\___ \\ / _ \\ |
     ____) |  __/ | | |____) |  __/ |
    |_____/ \\___|_| |_|_____/ \\___|_|
    %s
    """.formatted(green, reset);
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        System.out.println(banner);
        System.out.println("this my simple REPL SenSeI v1.0 programming language. Type #exit to exit.\n");
        while(true){
            System.out.print("%s▶▶▶ %s".formatted(green, reset));
            String line=reader.readLine();
            if(line==null || line.equals("#exit")){
                System.out.println("goodbye 🙋🏿‍♀️🙋🏿‍♀️🙋🏿‍♀️");
                break;
            }
            run(line);
            hadError=false;

        }

    }
    public static void main(String[] args) throws Exception {

        if(args.length==0){
            runREPL();
        }else if(args.length==1){
            runFile(args[0]);
        }else{
            System.out.println("Usage: java -jar Sensei.jar [script]");
            System.exit(0);
        }

    }

}
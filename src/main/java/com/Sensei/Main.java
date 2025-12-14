package com.Sensei;

import com.Sensei.lexicalAnalyzerPhase.Lexer;
import com.Sensei.lexicalAnalyzerPhase.Token;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    static boolean hadError = false;


    public static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        // ANSI Code for RED is \u001B[31m, Reset is \u001B[0m
        System.out.println("\u001B[31m" + "[ERROR] Line " + line + " | Error" + where + ": " + message + "\u001B[0m");
        hadError = true;
    }

    private static void run(String source) {

        Lexer lexer = new Lexer(source);


        List<Token> tokens = lexer.tokenize();


        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    private static void runFile(String path) throws IOException {
        
        if (!path.endsWith(".sensei")) {
            System.out.println("\u001B[31m Error: File must have .sensei extension \u001B[0m");
            System.exit(64);
        }

        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(65);
    }

    private static void runREPL() throws IOException {
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


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(banner);
        System.out.println("this my simple REPL SenSeI v1.0 programming language. Type #exit to exit.\n");

        while (true) {
            System.out.print("%s▶▶▶ %s".formatted(green, reset));
            String line = reader.readLine();
            if (line == null || line.equals("#exit")) break;
            run(line);
            hadError = false; // تصفير الخطأ عشان نكمل شغل في الـ REPL
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            runREPL();
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            System.out.println("Usage: java -jar Sensei.jar [script]");
            System.exit(64);
        }
    }
}
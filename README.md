![Gemini_Generated_Image_4bphs44bphs44bph.png](./Gemini_Generated_Image_4bphs44bphs44bph.png)
# Sensei (先生) Programming Language

> **"Coding is not just about logic; it's about bringing order to chaos."**

## ⛩️ The Philosophy (فلسفة المشروع)

In a world full of noise, glitches, and entropy, **Sensei** is an attempt to find structure. Inspired by the crafting of interpreters and the discipline of martial arts, this language aims to be a guide—a _Teacher_—that transforms raw, chaotic text into meaningful actions.

Just like a **Lexer** slices through a stream of characters to find meaning, we slice through the complexities of life to find our path.

This project is a hand-crafted interpreter written in **Java**, following the implementation of _jlox_, but with a soul of its own.

## 🧠 Architecture (The Katana)

The interpreter is being built in phases, mirroring the sharpening of a blade:

- [x] **Phase 1: The Scanner (Lexer)** - _Completed_

    - Breaking down source code into `Tokens`.

    - Handling distinct `TokenTypes` (Keywords, Literals, Operators).

    - Robust Error Reporting (ANSI colored output).

    - Support for comments and string literals.

- [ ] **Phase 2: The Parser** - _Next Step_

    - Converting tokens into an Abstract Syntax Tree (AST).

- [ ] **Phase 3: The Interpreter**

    - Breathing life into the AST.


## 🛠️ Project Structure(until now)

```
com.Sensei
├── Main.java                // Entry point & REPL Loop
├── lexicalAnalyzerPhase
│   ├── Lexer.java           // The tokenizer logic
│   ├── Token.java           // Token object definition
│   └── TokenType.java       // Enum for all supported types
```

## 💻 Installation & Usage

### Prerequisites

- Java Development Kit (JDK) 17 or higher.


### Running the REPL (The Dojo)

Sensei comes with an interactive Read-Eval-Print Loop (REPL) with a custom interface.

1. **Clone the repository:**

    ```
    git clone https://github.com/yahyaSensei/SenSei_programming_language.git
    ```

2. **Navigate to the source:**

    ```
    cd Sensei/src
    ```

3. **Compile and Run:**

    ```
    javac com/Sensei/Main.java
    java com.Sensei.Main
    ```

4. Experience the Interface:

   You will be greeted by the Sensei banner. Start typing your code:

    ```
       _____             _____       _ 
      / ____|           / ____|     (_)
     | (___   ___ _ __ | (___   ___  _ 
      \___ \ / _ \ '_ \ \___ \ / _ \ |
      ____) |  __/ | | |____) |  __/ |
     |_____/ \___|_| |_|_____/ \___|_|
    
     ▶▶▶ var problems = nil;
     ▶▶▶ print "Hello, World";
    ```


## 🔍 Code Snippet (Lexer Logic)

A glimpse into how Sensei analyzes the chaos:

```
// From Lexer.java
private void scanToken() {
    char c = advance();
    switch (c) {
        case '(': addToken(TokenType.LEFT_PAREN, null); break;
        // ...
        case '/':
            if (match('/')) {
                // Single-line comment: Ignoring the noise
                while (lookahead() != '\n' && !isAtEnd()) advance();
            } else {
                addToken(TokenType.SLASH, null);
            }
            break;
        // ...
    }
}
```

## 🤝 Contributing

The dojo is open. If you want to sharpen the blade with me, feel free to open a PR.

## 📜 License

This project is licensed under the MIT License - see the LICENSE file for details.

_Built with ☕ and 💻 by yahya hisham mohamed_

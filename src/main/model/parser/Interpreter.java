package model.parser;

import model.Event;
import model.EventLog;

import static model.parser.TokenType.*;

//Logical statements interpreter (lexer/parser) class
public class Interpreter {
    private String input;
    private int position;
    private Token currentToken;

    //REQUIRES: input in the format of a&b or a|b where a/b are T/F
    //MODIFIES: this
    //EFFECTS: initializes the interpreter
    public Interpreter(String input) {
        this.input = input;
        position = 0;
    }

    //MODIFIES: this
    //EFFECT: gets the next token in the string
    public Token getNextToken() {
        if (position > input.length() - 1) {
            return new Token(EOF, "null");
        }

        Character currentChar = input.charAt(position);

        if (currentChar.equals('&')) {
            position += 1;
            return new Token(AND, "&");
        } else if (currentChar.equals('|')) {
            position += 1;
            return new Token(OR, "|");
        } else if (currentChar.equals('T')) {
            position += 1;
            return new Token(BOOLEAN, "true");
        } else if (currentChar.equals('F')) {
            position += 1;
            return new Token(BOOLEAN, "false");
        } else if (Character.isAlphabetic(currentChar)) {
            position += 1;
            return new Token(VAR, currentChar.toString());
        } else {
            return new Token(ERROR, "error");
        }
    }

    //MODIFIES: this
    //EFFECT: if current token type matches the passed token type, get next, else error
    public void pop(TokenType checkType) {
        if (currentToken.getType() == checkType) {
            currentToken = getNextToken();
        } else {
            currentToken = new Token(ERROR, "error");
        }
    }

    //MODIFIES: this
    //EFFECT: returns the result of an expression of the form: expr -> bool & bool
    public Boolean expr() {
        currentToken = getNextToken();

        boolean left = Boolean.parseBoolean(currentToken.getValue());
        pop(BOOLEAN);

        TokenType op = currentToken.getType();
        if (op.label.equals(AND.label)) {
            pop(AND);
        } else {
            pop(OR);
        }

        boolean right = Boolean.parseBoolean(currentToken.getValue());
        pop(BOOLEAN);

        boolean result = op.label.equals(AND.label) ? left && right : left || right;

        EventLog.getInstance().logEvent(new Event("Eval - Evaluated statement " + input
                + " = " + result));

        return result;
    }

    //MODIFIES: this
    //EFFECT: sets the current token
    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }

    //EFFECT: returns the current token as a string
    public String getCurrentToken() {
        return currentToken.toString();
    }

    //EFFECT: returns the current position
    public int getPosition() {
        return position;
    }

}

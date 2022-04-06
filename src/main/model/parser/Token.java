package model.parser;

//Token class to represent each character in the code
public class Token {
    private TokenType type;
    private String value;

    //MODIFIES: this
    //EFFECTS: initialize the token with a type and value
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    //EFFECT: returns the token as "(type, value)"
    public String toString() {
        return "(" + type.label + ", " + value + ")";
    }

    //EFFECT: returns the type of the token
    public TokenType getType() {
        return type;
    }

    //EFFECT: returns the value of the token
    public String getValue() {
        return value;
    }
}

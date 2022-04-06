package model.parser;

//Represents the types of tokens available
public enum TokenType {
    AND("AND"),
    OR("OR"),
    BOOLEAN("BOOLEAN"),
    VAR("VARIABLE"),
    EOF("EOF"),
    ERROR("ERROR");

    public final String label;

    TokenType(String label) {
        this.label = label;
    }
}

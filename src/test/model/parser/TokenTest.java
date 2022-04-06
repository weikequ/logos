package model.parser;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.parser.TokenType.*;
import static org.junit.jupiter.api.Assertions.*;

//Tests for the Token class
public class TokenTest {
    Token token1;

    @Test
    public void testToString() {
        token1 = new Token(AND,"&");
        assertEquals("(AND, &)", token1.toString());
        token1 = new Token(BOOLEAN,"F");
        assertEquals("(BOOLEAN, F)", token1.toString());
        token1 = new Token(VAR, "a");
        assertEquals("(VARIABLE, a)", token1.toString());
    }

    @Test
    public void testGetType() {
        token1 = new Token(AND, "&");
        assertEquals(AND, token1.getType());
        token1 = new Token(OR, "|");
        assertEquals(OR, token1.getType());
        token1 = new Token(BOOLEAN, "false");
        assertEquals(BOOLEAN, token1.getType());
    }

    @Test
    public void testGetValue() {
        token1 = new Token(AND, "&");
        assertEquals("&", token1.getValue());
        token1 = new Token(OR, "|");
        assertEquals("|", token1.getValue());
        token1 = new Token(BOOLEAN, "false");
        assertEquals("false", token1.getValue());

    }
}

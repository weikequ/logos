package model.parser;

import org.junit.jupiter.api.Test;

import static model.parser.TokenType.*;
import static org.junit.jupiter.api.Assertions.*;

//Tests for the Interpreter class
public class InterpreterTest {
    Interpreter engine;

    @Test
    public void testGetNextTokenVar() {
        engine = new Interpreter("a&b");

        assertEquals("(VARIABLE, a)", engine.getNextToken().toString());
        assertEquals("(AND, &)", engine.getNextToken().toString());
        assertEquals("(VARIABLE, b)", engine.getNextToken().toString());
        assertEquals("(EOF, null)", engine.getNextToken().toString());

        engine = new Interpreter("T|F");

        assertEquals("(BOOLEAN, true)", engine.getNextToken().toString());
        assertEquals("(OR, |)", engine.getNextToken().toString());
        assertEquals("(BOOLEAN, false)", engine.getNextToken().toString());
        assertEquals("(EOF, null)", engine.getNextToken().toString());
    }

    @Test
    public void testGetNextTokenLiteral() {
        engine = new Interpreter("T&F");

        assertEquals("(BOOLEAN, true)", engine.getNextToken().toString());
        assertEquals("(AND, &)", engine.getNextToken().toString());
        assertEquals("(BOOLEAN, false)", engine.getNextToken().toString());
        assertEquals("(EOF, null)", engine.getNextToken().toString());
    }

    @Test
    public void testGetNextTokenError() {
        engine = new Interpreter("$%^&");
        assertEquals("(ERROR, error)", engine.getNextToken().toString());
    }

    @Test
    public void testPop() {
        engine = new Interpreter("T&F");
        engine.setCurrentToken(engine.getNextToken());
        assertEquals("(BOOLEAN, true)", engine.getCurrentToken());
        engine.pop(BOOLEAN);

        assertEquals("(AND, &)", engine.getCurrentToken());
        engine.pop(AND);

        assertEquals("(BOOLEAN, false)", engine.getCurrentToken());
        engine.pop(BOOLEAN);

        assertEquals("(EOF, null)", engine.getCurrentToken());
    }

    @Test
    public void testPopError() {
        engine = new Interpreter("T&F");
        engine.setCurrentToken(engine.getNextToken());
        assertEquals("(BOOLEAN, true)", engine.getCurrentToken());
        engine.pop(VAR);
        assertEquals("(ERROR, error)", engine.getCurrentToken());
    }

    @Test
    public void testExprLiterals() {
        engine = new Interpreter("T&T");
        assertTrue(engine.expr());
        engine = new Interpreter("T&F");
        assertFalse(engine.expr());
        engine = new Interpreter("F&T");
        assertFalse(engine.expr());
        engine = new Interpreter("F&F");
        assertFalse(engine.expr());

        engine = new Interpreter("T|T");
        assertTrue(engine.expr());
        engine = new Interpreter("T|F");
        assertTrue(engine.expr());
        engine = new Interpreter("F|T");
        assertTrue(engine.expr());
        engine = new Interpreter("F|F");
        assertFalse(engine.expr());
    }

    @Test
    public void testGetPosition() {
        engine = new Interpreter("T&F");
        assertEquals(0, engine.getPosition());
        assertEquals("(BOOLEAN, true)", engine.getNextToken().toString());
        assertEquals(1, engine.getPosition());


    }
}

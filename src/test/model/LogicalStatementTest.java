package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Tests for the LogicalStatement class
public class LogicalStatementTest {
    LogicalStatement statement;

    @Test
    public void getVariables() {
        statement = new LogicalStatement("a&b");
        assertEquals("0 - a\n2 - b\n",statement.getVariables());

        statement = new LogicalStatement("c&d");
        assertEquals("0 - c\n2 - d\n",statement.getVariables());

        statement = new LogicalStatement("c&T");
        assertEquals("0 - c\n",statement.getVariables());

        statement = new LogicalStatement("F&d");
        assertEquals("2 - d\n",statement.getVariables());

        statement = new LogicalStatement("T&F");
        assertEquals("T&F", statement.getInput());
        assertEquals(0,statement.getVariableTruthTables().size());
    }

    @Test
    public void testSubstituteVariable() {
        statement = new LogicalStatement("a&b");
        assertEquals("T&b",
                statement.substituteVariable("a&b", "a", 'T'));
        assertEquals("T&F",
                statement.substituteVariable("T&b", "b", 'F'));
    }

    @Test
    public void testCalculateRow() {
        statement = new LogicalStatement("a&b");
        assertEquals("F", statement.calculateRow(0));
        assertEquals("F", statement.calculateRow(1));
        assertEquals("F", statement.calculateRow(2));
        assertEquals("T", statement.calculateRow(3));
    }

    @Test
    public void testGenerateLogicalValues() {
        statement = new LogicalStatement("a&b");
        assertEquals("FFTT", statement.getVariableTruthTables().get(0));
        assertEquals("FTFT", statement.getVariableTruthTables().get(1));

        statement = new LogicalStatement("T&b");
        assertEquals("FT", statement.getVariableTruthTables().get(0));
    }

    @Test
    public void testGenerateTruthTable() {
        statement = new LogicalStatement("a&b");
        assertEquals("FFFT",statement.generateTruthTable());

        statement = new LogicalStatement("c&d");
        assertEquals("FFFT",statement.generateTruthTable());

        statement = new LogicalStatement("c&T");
        assertEquals("FT",statement.generateTruthTable());

        statement = new LogicalStatement("F&d");
        assertEquals("FF",statement.generateTruthTable());
    }

    @Test
    public void testToJson() {
        statement = new LogicalStatement("a&b");
        assertEquals("a&b",statement.toJson().getString("statement"));

        statement = new LogicalStatement("a|b");
        assertEquals("a|b",statement.toJson().getString("statement"));

        statement = new LogicalStatement("T&F");
        assertEquals("T&F",statement.toJson().getString("statement"));

        statement = new LogicalStatement("F|T");
        assertEquals("F|T",statement.toJson().getString("statement"));
    }
}

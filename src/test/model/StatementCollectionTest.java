package model;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Tests for the StatementCollection class
public class StatementCollectionTest {
    StatementCollection testList;

    @BeforeEach
    public void startUp() {
        testList = new StatementCollection();
    }

    @Test
    public void testAdd() {
        assertTrue(testList.isEmpty());

        //add 1 item
        testList.add(new LogicalStatement("a&b"));
        assertEquals(1, testList.size());
        assertEquals("a&b", testList.get(0).getInput());


        //add 2 items
        testList.add(new LogicalStatement("a|b"));
        assertEquals(2, testList.size());
        assertEquals("a&b", testList.get(0).getInput());
        assertEquals("a|b", testList.get(1).getInput());
    }

    @Test
    public void testRemoveOne() {
        assertTrue(testList.isEmpty());

        //add 1 & remove 1
        testList.add(new LogicalStatement("a&b"));
        testList.remove(0);
        assertTrue(testList.isEmpty());

        //add 2 & remove 1
        testList.add(new LogicalStatement("a&b"));
        testList.add(new LogicalStatement("a|b"));
        testList.remove(0);
        assertFalse(testList.isEmpty());
        assertEquals(1, testList.size());
        assertEquals("a|b", testList.get(0).getInput());
    }

    @Test
    public void testRemoveMultiple() {
        //add 2 & remove 2
        testList.add(new LogicalStatement("a&b"));
        testList.add(new LogicalStatement("a|b"));
        testList.remove(0);
        assertFalse(testList.isEmpty());
        testList.remove(0);
        assertTrue(testList.isEmpty());
    }

    @Test
    public void testRemoveAtIndexOne() {
        //add 3 & remove 1 @ position 1
        testList.add(new LogicalStatement("a&b"));
        testList.add(new LogicalStatement("a|b"));
        testList.add(new LogicalStatement("c|a"));
        testList.remove(1);
        assertEquals(2, testList.size());
        assertEquals("a&b", testList.get(0).getInput());
        assertEquals("c|a", testList.get(1).getInput());
    }

    @Test
    public void testRemoveAtIndexZero() {
        //add 3 & remove 1 @ position 0
        testList.add(new LogicalStatement("a&b"));
        testList.add(new LogicalStatement("a|b"));
        testList.add(new LogicalStatement("c|a"));
        testList.remove(0);
        assertEquals(2, testList.size());
        assertEquals("a|b", testList.get(0).getInput());
        assertEquals("c|a", testList.get(1).getInput());
    }

    @Test
    public void testToJson() {
        testList.add(new LogicalStatement("a&b"));
        testList.add(new LogicalStatement("a|b"));
        testList.add(new LogicalStatement("c|a"));

        JSONArray jsonArray = testList.toJson().getJSONArray("statements");
        assertEquals("a&b", jsonArray.getJSONObject(0).getString("statement"));
        assertEquals("a|b", jsonArray.getJSONObject(1).getString("statement"));
        assertEquals("c|a", jsonArray.getJSONObject(2).getString("statement"));
    }
}

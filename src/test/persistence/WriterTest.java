// Works referenced: JsonSerializationDemo

package persistence;

import model.LogicalStatement;
import model.StatementCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Test class for the JSON writer
class WriterTest {
    StatementCollection statementList;

    @BeforeEach
    void setup() {
        statementList = new StatementCollection();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Writer writer = new Writer("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStatementCollection() {
        try {
            Writer writer = new Writer("./data/testWriterEmptyCollection.json");
            writer.open();
            writer.write(statementList);
            writer.close();

            Reader reader = new Reader("./data/testWriterEmptyCollection.json");
            statementList = reader.read();
            assertEquals(0, statementList.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStatementCollection() {
        try {
            statementList.add(new LogicalStatement("a&b"));
            statementList.add(new LogicalStatement("T&b"));
            statementList.add(new LogicalStatement("T&F"));
            statementList.add(new LogicalStatement("F&F"));
            Writer writer = new Writer("./data/testWriterGeneralStatementCollection.json");
            writer.open();
            writer.write(statementList);
            writer.close();

            Reader reader = new Reader("./data/testWriterGeneralStatementCollection.json");
            statementList = reader.read();
            assertEquals("a&b",statementList.get(0).getInput());
            assertEquals("T&b",statementList.get(1).getInput());
            assertEquals("T&F",statementList.get(2).getInput());
            assertEquals("F&F",statementList.get(3).getInput());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
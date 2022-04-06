// Works referenced: JsonSerializationDemo

package persistence;

import model.LogicalStatement;
import model.StatementCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Test class for the JSON Reader
public class ReaderTest {
    StatementCollection statementList;

    @BeforeEach
    void setup() {
        statementList = new StatementCollection();
    }

    @Test
    void testReaderNonExistentFile() {
        Reader reader = new Reader("./data/noSuchFile.json");
        try {
            statementList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStatementCollection() {
        Writer writer = new Writer("./data/testReaderEmptyStatementCollection.json");
        Reader reader = new Reader("./data/testReaderEmptyStatementCollection.json");
        try {
            writer.open();
            writer.write(statementList);
            writer.close();

            statementList = reader.read();
            assertEquals(0, statementList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralStatementCollection() {
        Reader reader = new Reader("./data/testReaderGeneralStatementCollection.json");
        Writer writer = new Writer("./data/testReaderGeneralStatementCollection.json");

        try {
            statementList.add(new LogicalStatement("a&b"));
            statementList.add(new LogicalStatement("a|b"));
            statementList.add(new LogicalStatement("T&F"));
            writer.open();
            writer.write(statementList);
            writer.close();

            statementList = reader.read();
            assertEquals(3, statementList.size());
            assertEquals("a&b",statementList.get(0).getInput());
            assertEquals("a|b",statementList.get(1).getInput());
            assertEquals("T&F",statementList.get(2).getInput());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

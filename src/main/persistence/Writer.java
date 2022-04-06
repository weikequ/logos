// Works referenced: JsonSerializationDemo

package persistence;

import model.StatementCollection;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// A writer that saves a JSON interpretation of the statement collection
public class Writer {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //MODIFIES: this
    //EFFECTS: initializes the JSON Writer class
    public Writer(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: opens the writer, throws FileNotFoundException if destination file cannot be found for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: writes JSON representation of StatementCollection to file
    public void write(StatementCollection collection) {
        JSONObject json = collection.toJson();
        saveToFile(json.toString(TAB));
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to file
    public void saveToFile(String json) {
        writer.print(json);
    }
}

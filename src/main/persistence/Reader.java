// Works referenced: JsonSerializationDemo

package persistence;

import model.LogicalStatement;
import model.StatementCollection;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// A reader that reads the StatementCollection stored in the file
public class Reader {
    private String source;

    // MODIFIES: this
    // EFFECTS: initializes the reader with the input source
    public Reader(String source) {
        this.source = source;
    }

    // EFFECTS: reads the JSON from the file, throw IOException if an error occurs
    public StatementCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStatementCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses StatementCollection from JSON object and returns it
    private StatementCollection parseStatementCollection(JSONObject jsonObject) {
        StatementCollection statementList = new StatementCollection();

        for (Object jsonObj : jsonObject.getJSONArray("statements")) {
            JSONObject json = (JSONObject) jsonObj;
            statementList.add(new LogicalStatement(json.getString("statement")));
        }

        return statementList;
    }

}

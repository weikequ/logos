package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//A collection of logical statements that can be saved and retrieved
public class StatementCollection implements Writable {
    private List<LogicalStatement> statementList;

    //MODIFIES: this
    //EFFECTS: initializes the list
    public StatementCollection() {
        statementList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds a logical statement to the list
    public void add(LogicalStatement statement) {
        statementList.add(statement);
        EventLog.getInstance().logEvent(new Event("Col - Added statement: " + statement.getInput()));
    }

    //REQUIRES: index is [0, size of list]
    //MODIFIES: this
    //EFFECTS: removes the statement at index from the list
    public void remove(int index) {
        EventLog.getInstance().logEvent(new Event("Col - Removed statement at index " + index
                + ": " + statementList.get(index).getInput()));
        statementList.remove(index);
    }

    //REQUIRES: index is [0, size of list]
    //EFFECTS: returns the statement at index
    public LogicalStatement get(int index) {
        return statementList.get(index);
    }

    //EFFECTS: returns whether the collection is empty
    public boolean isEmpty() {
        return statementList.isEmpty();
    }

    //EFFECTS: returns the size of the list
    public int size() {
        return statementList.size();
    }

    // EFFECTS: converts the current statement collection to a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (LogicalStatement statement : statementList) {
            jsonArray.put(statement.toJson());
        }

        json.put("statements", jsonArray);

        EventLog.getInstance().logEvent(new Event("Col - Saved collection to JSON."));
        return json;
    }
}

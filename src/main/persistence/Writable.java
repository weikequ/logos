// Works referenced: JsonSerializationDemo

package persistence;

import org.json.JSONObject;

// Requires subclasses to implement a function to output to JSON
public interface Writable {
    // EFFECTS: returns this object as a JSON object
    JSONObject toJson();
}

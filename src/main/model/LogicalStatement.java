package model;

import model.parser.Interpreter;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Represents logical statements of the form a&b or a|b
public class LogicalStatement implements Writable {
    private final String input;
    private final List<String> variables;
    private final List<Integer> variablePositions;
    private final List<String> variableTruthTables;

    //REQUIRES: input is of the form a&b where a/b can be variables or T/F values
    //MODIFIES: this
    //EFFECTS: initializes the class
    public LogicalStatement(String input) {
        this.input = input;
        variables = new ArrayList<>();
        variablePositions = new ArrayList<>();
        variableTruthTables = new ArrayList<>();

        findVariables();
        if (variables.size() != 0) {
            generateLogicalValues();
        }
    }

    //EFFECT: getter for input
    public String getInput() {
        return input;
    }

    //MODIFIES: this
    //EFFECTS: gets the variables from a logical statement
    private void findVariables() {
        int position = 0;
        for (Character entry : input.toCharArray()) {
            if (Character.isAlphabetic(entry) && !(entry.equals('T') || entry.equals('F'))) {
                variables.add(entry.toString());
                variablePositions.add(position);
            }
            position++;
        }
    }

    //REQUIRES: varString has at least one occurrence of var
    //EFFECTS: substitutes in the generated logical values in place of the variables
    public String substituteVariable(String varString, String var, Character val) {
        return varString.replaceAll(var, val.toString()); //stub
    }

    //REQUIRES: row is within [0, size of the variableTruthTables]
    //EFFECTS: substitutes all the variables available for a given row of values
    public String calculateRow(int row) {
        String substitute = input;

        for (int varEntry = 0; varEntry < variableTruthTables.size(); varEntry++) {
            substitute = substituteVariable(substitute, variables.get(varEntry),
                    variableTruthTables.get(varEntry).charAt(row));
        }

        Interpreter parser = new Interpreter(substitute);

        if (parser.expr()) {
            return "T";
        } else {
            return "F";
        }
    }

    //REQUIRES: variables to have a size > 0
    //MODIFIES: this
    //EFFECTS: generates the logical values for each variable
    public void generateLogicalValues() {
        int size = variables.size();
        int rows = 1 << size;
        int tfCounter = 0;
        String currentColumn;
        String currentVal;

        for (int varNumber = 1; varNumber <= size; varNumber++) {
            currentColumn = "";
            currentVal = "F";

            for (int i = 0; i < rows; i++) {
                currentColumn += currentVal;
                tfCounter++;
                if (tfCounter >= rows / (1 << varNumber)) {
                    tfCounter = 0;
                    if (currentVal.equals("F")) {
                        currentVal = "T";
                    } else {
                        currentVal = "F";
                    }
                }
            }
            variableTruthTables.add(currentColumn);
        }
    }

    //REQUIRES: there are 1 or more variables
    //EFFECTS: generate the truth table for the expression
    public String generateTruthTable() {
        String result = "";

        for (int row = 0; row < variableTruthTables.get(0).length(); row++) {
            result += calculateRow(row);
        }

        EventLog.getInstance().logEvent(new Event("LS - Generated truth table for " + getInput()
                + ": " + result));

        return result;
    }

    //EFFECTS: returns the variables with their positions as strings
    public String getVariables() {
        String result = "";

        for (int i = 0; i < variables.size(); i++) {
            result += variablePositions.get(i) + " - " + variables.get(i) + "\n";
        }

        return result; //stub
    }

    //EFFECTS: getter for variableTruthTables
    public List<String> getVariableTruthTables() {
        return variableTruthTables;
    }

    //EFFECTS: returns the statement as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("statement", input);
        return json;
    }
}

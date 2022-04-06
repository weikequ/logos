package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

// Custom class for the entry text field, extending jtextfield
public class EntryTextField extends JTextField {

    private static final String MS_LIGHT = "./data/resources/fonts/Montserrat-Light.ttf";

    private String checkFormat;

    Font textFont;


    // MODIFIES: this
    // EFFECTS: initializes the string with a regex format checker
    public EntryTextField(String promptText, String checkFormat) {
        super(promptText);
        this.checkFormat = checkFormat;
        setBorder(javax.swing.BorderFactory.createEmptyBorder());
        initializeFonts(14f);
        setFont(textFont);
        setForeground(MainWindow.FADED_BG_COLOR);
        setHorizontalAlignment(0);
    }

    // EFFECTS: checks if the input string is valid according to the given regex
    public boolean isValid(String input) {
        return input.matches(checkFormat);
    }

    // MODIFIES: this
    // EFFECTS: initializes fonts for the class
    private void initializeFonts(Float fontSize) {
        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File(MS_LIGHT)).deriveFont(fontSize);
        } catch (IOException e) {
            System.out.println("IO error!");
        } catch (FontFormatException e) {
            System.out.println("Format Error!");
        }
    }
}

package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// Custom JLabel class with modifications
public class DescLabel extends JLabel {

    private static final String MS_LIGHT = "./data/resources/fonts/Montserrat-Light.ttf";

    private Font labelFont;

    // MODIFIES: this
    // EFFECTS: constructor with custom properties
    public DescLabel(String text, Float fontSize) {
        super(text);
        initializeFonts(fontSize);
        setFont(labelFont);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes fonts for this class
    private void initializeFonts(Float fontSize) {
        try {
            labelFont = Font.createFont(Font.TRUETYPE_FONT, new File(MS_LIGHT)).deriveFont(fontSize);
        } catch (IOException e) {
            System.out.println("IO error!");
        } catch (FontFormatException e) {
            System.out.println("Format Error!");
        }
    }
}

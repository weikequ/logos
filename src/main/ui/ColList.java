package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// Extended JList class with customizations
public class ColList extends JList<String> {
    private static final String MS_LIGHT = "./data/resources/fonts/Montserrat-Light.ttf";
    private Font listFont;


    // MODIFIES: this
    // EFFECTS: constructs the custom class with properties below
    public ColList(DefaultListModel<String> model, Float fontSize) {
        super(model);
        initializeFonts(fontSize);
        setFont(listFont);
        setBorder(javax.swing.BorderFactory.createEmptyBorder());
        setForeground(MainWindow.MENU_COLOR);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // MODIFIES: this
    // EFFECTS: initializes the fonts used
    private void initializeFonts(Float fontSize) {
        try {
            listFont = Font.createFont(Font.TRUETYPE_FONT, new File(MS_LIGHT)).deriveFont(fontSize);
        } catch (IOException e) {
            System.out.println("IO error!");
        } catch (FontFormatException e) {
            System.out.println("Format Error!");
        }
    }
}

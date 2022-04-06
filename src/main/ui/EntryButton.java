package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

// Custom button for submission/entries extending jbutton
public class EntryButton extends JButton implements MouseListener {

    private MainWindow window;
    private String actionCommand;

    private static final String MS_LIGHT = "./data/resources/fonts/Montserrat-Light.ttf";
    private static final String MS_BOLD = "./data/resources/fonts/Montserrat-Bold.ttf";

    private Font pressedFont;
    private Font notPressedFont;


    // MODIFIES: this
    // EFFECTS: initializes the button with custom settings
    public EntryButton(String label, Float fontSize, MainWindow window, String actionCommand) {
        super(label);
        this.window = window;
        setActionCommand(actionCommand);
        initializeFonts(fontSize);
        initializeButton();
    }

    // MODIFIES: this
    // EFFECTS: initialies the button with custom settings, but uses a default 16f font
    public EntryButton(String label, MainWindow window, String actionCommand) {
        super(label);
        this.window = window;
        setActionCommand(actionCommand);
        initializeFonts(16f);
        initializeButton();
    }


    // MODIFIES: this
    // EFFECTS: additional customizations to the button
    private void initializeButton() {
        addActionListener(window);
        setFont(notPressedFont);
        setForeground(MainWindow.BG_COLOR);
        setBackground(MainWindow.ENTRY_BUTTON_COLOR);
        setBorderPainted(false);
        setFocusPainted(false);
        addMouseListener(this);
    }

    // MODIFIES: this
    // EFFECTS: initializes the fonts used in the buttons
    private void initializeFonts(Float fontSize) {
        try {
            pressedFont = Font.createFont(Font.TRUETYPE_FONT, new File(MS_LIGHT)).deriveFont(fontSize);
            notPressedFont = Font.createFont(Font.TRUETYPE_FONT, new File(MS_BOLD)).deriveFont(fontSize);
        } catch (IOException e) {
            System.out.println("IO error!");
        } catch (FontFormatException e) {
            System.out.println("Format Error!");
        }
    }

    // EFFECTS: do nothing
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    // EFFECTS: do nothing
    @Override
    public void mousePressed(MouseEvent e) {
    }

    // EFFECTS: do nothing
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    // MODIFIES: this
    // EFFECTS: change font on hover
    @Override
    public void mouseEntered(MouseEvent e) {
        setFont(pressedFont);
    }

    // MODIFIES: this
    // EFFECTS: change font on hover
    @Override
    public void mouseExited(MouseEvent e) {
        setFont(notPressedFont);
    }
}

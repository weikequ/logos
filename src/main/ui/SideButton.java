package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

// Custom side buttons extending JButton
public class SideButton extends JButton implements MouseListener {

    private MainWindow window;

    private static final String MS_LIGHT = "./data/resources/fonts/Montserrat-Light.ttf";
    private static final String MS_BOLD = "./data/resources/fonts/Montserrat-Bold.ttf";

    private Font pressedFont;
    private Font notPressedFont;

    private Boolean pressed = false;

    // MODIFIES: this
    // EFFECTS: initializes class with some customizations
    public SideButton(String label, Float fontSize, MainWindow window, String actionCommand) {
        super(label);
        this.window = window;
        setActionCommand(actionCommand);
        initializeFonts(fontSize);
        initializeButton();
    }

    // MODIFIES: this
    // EFFECTS: initializes the class with a default font size of 16f
    public SideButton(String label, MainWindow window, String actionCommand) {
        super(label);
        this.window = window;
        setActionCommand(actionCommand);
        initializeFonts(16f);
        initializeButton();
    }

    public Boolean getPressed() {
        return pressed;
    }

    // MODIFIES: this
    // EFFECTS: initializes the button with additional modifications
    private void initializeButton() {
        addActionListener(window);
        setContentAreaFilled(false);
        setFont(notPressedFont);
        setForeground(MainWindow.BG_COLOR);
        setBorderPainted(false);
        setFocusPainted(false);
        addMouseListener(this);
    }

    // MODIFIES: this
    // EFFECTS: initializes the fonts for the class
    private void initializeFonts(Float fontSize) {
        try {
            notPressedFont = Font.createFont(Font.TRUETYPE_FONT, new File(MS_LIGHT)).deriveFont(fontSize);
            pressedFont = Font.createFont(Font.TRUETYPE_FONT, new File(MS_BOLD)).deriveFont(fontSize);
        } catch (IOException e) {
            System.out.println("IO error!");
        } catch (FontFormatException e) {
            System.out.println("Format Error!");
        }
    }

    // MODIFIES: this
    // EFFECTS: handles changes in color when pressed
    public void pressButton() {
        if (!pressed) {
            setContentAreaFilled(true);
            setForeground(MainWindow.MENU_COLOR);
            setBackground(MainWindow.BG_COLOR);
            setFont(pressedFont);
        } else {
            setContentAreaFilled(false);
            setForeground(MainWindow.BG_COLOR);
            setBackground(MainWindow.MENU_COLOR);
            setFont(notPressedFont);
        }
        pressed = !pressed;
    }

    // MODIFIES: this, MainWindow
    // EFFECTS: runs press button and calls togglesidebuttons
    @Override
    public void mouseClicked(MouseEvent e) {
        pressButton();
        window.toggleSideButtons(this);
    }

    // EFFECTS: no effect
    @Override
    public void mousePressed(MouseEvent e) {
    }

    // EFFECTS: no effect
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    // MODIFIES: this
    // EFFECTS: change appearance on hover
    @Override
    public void mouseEntered(MouseEvent e) {
        if (pressed) {
            setFont(notPressedFont);
        } else {
            setContentAreaFilled(true);
            setForeground(MainWindow.MENU_COLOR);
            setBackground(MainWindow.BG_COLOR);
        }
    }

    // MODIFIES: this
    // EFFECTS: change appearance on hover
    @Override
    public void mouseExited(MouseEvent e) {
        if (pressed) {
            setFont(pressedFont);
        } else {
            setContentAreaFilled(false);
            setForeground(MainWindow.BG_COLOR);
            setBackground(MainWindow.MENU_COLOR);
        }
    }
}

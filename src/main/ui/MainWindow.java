package ui;

import model.Event;
import model.EventLog;
import model.LogicalStatement;
import model.StatementCollection;
import model.parser.Interpreter;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Main window class of the GUI and handles all the layouts and events
public class MainWindow extends JFrame implements ActionListener {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final int SPLITTER_LOC = 123;

    private static final String SPLASH_DIR = "./data/resources/splash.png";
    private static final String ICON_DIR = "./data/resources/icon.png";
    private static final String JSON_STORE = "./data/statementcollection.json";

    private JPanel splashPanel;

    private JSplitPane mainSplitPane;
    private JPanel menuPanel;
    private JPanel mainPanel;

    private ArrayList<SideButton> sideButtonList;
    private ArrayList<JPanel> mainPanelList;

    private EntryTextField evalInput;
    private DescLabel evalResult;
    private EntryTextField tblInput;
    private DescLabel tblResult;
    private ColList colList;
    private DefaultListModel<String> colListModel = new DefaultListModel<>();
    private EntryTextField colInput;
    private StatementCollection collection = new StatementCollection();
    private DescLabel loadResult;
    private DescLabel saveResult;

    private Reader reader;
    private Writer writer;

    public static final Color BG_COLOR = Color.decode("#F0FFF3");
    public static final Color MENU_COLOR = Color.decode("#4E4E4E");
    public static final Color ENTRY_BUTTON_COLOR = Color.decode("#79C68A");
    public static final Color FADED_BG_COLOR = Color.decode("#737B7D");

    private final ImageIcon windowIcon = new ImageIcon(ICON_DIR);

    // MODIFIES: this
    // EFFECTS: initializes the main window and its required components
    public MainWindow() {
        super("Logos");
        reader = new Reader(JSON_STORE);
        writer = new Writer(JSON_STORE);
        initializeFrame();
        initializePanels();
        initializeSplitPane();
        initializeMainPanel();
        initializeMenuPanel();
        setupEval();
        setupTable();
        setupCol();
        setupLoad();
        setupSave();
        pack();
        showSplash();
    }

    // MODIFIES: this
    // EFFECTS: initializes the main jframe
    private void initializeFrame() {
        setIconImage(windowIcon.getImage());
        getContentPane().setLayout(new CardLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:

    // MODIFIES: this
    // EFFECTS: initializes the major components of the frame
    private void initializePanels() {
        splashPanel = new JPanel();
        JLabel splashBg = new JLabel(new ImageIcon(SPLASH_DIR));
        splashPanel.add(splashBg);
        mainSplitPane = new JSplitPane();
        menuPanel = new JPanel();
        mainPanel = new JPanel();
        mainPanelList = new ArrayList<>();

        add(splashPanel, "Splash Panel");
        add(mainSplitPane, "Main Split Panel");
    }

    // MODIFIES: this
    // EFFECTS: initializes the split pane and assignes them
    private void initializeSplitPane() {
        mainSplitPane.setDividerSize(0);
        mainSplitPane.setEnabled(false);

        menuPanel.setBackground(MENU_COLOR);
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setLayout(new CardLayout());

        mainSplitPane.setLeftComponent(menuPanel);
        mainSplitPane.setRightComponent(mainPanel);
        mainSplitPane.setDividerLocation(SPLITTER_LOC);
    }

    // MODIFIES: this
    // EFFECTS: initializes the main card layout panel
    private void initializeMainPanel() {
        mainPanelList.add(new JPanel());
        mainPanelList.add(new JPanel());
        mainPanelList.add(new JPanel());
        mainPanelList.add(new JPanel());
        mainPanelList.add(new JPanel());

        mainPanel.add(mainPanelList.get(0), "Eval");
        mainPanel.add(mainPanelList.get(1), "Table");
        mainPanel.add(mainPanelList.get(2), "Col");
        mainPanel.add(mainPanelList.get(3), "Load");
        mainPanel.add(mainPanelList.get(4), "Save");

        for (JPanel panel : mainPanelList) {
            panel.setBackground(BG_COLOR);
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes the menu panel
    private void initializeMenuPanel() {

        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        JLabel iconImage = new JLabel(new ImageIcon(ICON_DIR));
        iconImage.setMaximumSize(new Dimension(123, 121));
        menuPanel.add(iconImage);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 74)));

        sideButtonList = new ArrayList<>();
        sideButtonList.add(new SideButton("T v F", 24f, this, "switchEval"));
        sideButtonList.add(new SideButton("a v b", 24f, this, "switchTable"));
        sideButtonList.add(new SideButton("collection", this, "switchCol"));
        sideButtonList.add(new SideButton("load", this, "switchLoad"));
        sideButtonList.add(new SideButton("save", this, "switchSave"));

        for (SideButton button : sideButtonList) {
            button.setMaximumSize(new Dimension(123, 63));
            menuPanel.add(button);
        }

    }

    // MODIFIES: this
    // EFFECTS: sets up the eval page in the mainpanel
    private void setupEval() {
        sideButtonList.get(0).pressButton();
        JPanel eval = mainPanelList.get(0);

        eval.setLayout(null);

        evalInput = new EntryTextField("Enter a ‘T&F’ form statement...", "[TF][\\&\\|][TF]");
        EntryButton evalBtn = new EntryButton("Evaluate", 14f, this, "evalBtn");
        evalResult = new DescLabel("", 24f);

        eval.add(evalInput);
        evalInput.setBounds(226, 206, 282, 42);
        eval.add(evalBtn);
        evalBtn.setBounds(569, 206, 102, 42);
        eval.add(evalResult);
        evalResult.setBounds(251, 300, 400, 65);
    }

    // MODIFIES: this
    // EFFECTS: sets up the table page in the mainpanel
    private void setupTable() {
        JPanel tbl = mainPanelList.get(1);

        tbl.setLayout(null);

        tblInput = new EntryTextField("Enter a ‘a&b’ form statement...",
                "[TF(a-z)][\\&\\|][TF(a-z)]");
        EntryButton tblBtn = new EntryButton("Generate Truth Table", 14f,
                this, "tblBtn");
        tblResult = new DescLabel("", 24f);

        tbl.add(tblInput);
        tblInput.setBounds(181, 206, 282, 42);
        tbl.add(tblBtn);
        tblBtn.setBounds(524, 206, 197, 42);
        tbl.add(tblResult);
        tblResult.setBounds(251, 300, 400, 65);
    }

    // MODIFIES: this
    // EFFECTS: sets up the collections page in the mainpanel
    private void setupCol() {
        JPanel col = mainPanelList.get(2);
        col.setLayout(null);
        colList = new ColList(colListModel, 18f);
        EntryButton colAddBtn = new EntryButton("Add", 14f, this, "colAddBtn");
        EntryButton colEvalBtn = new EntryButton("Evaluate Selected",
                14f, this, "colEvalBtn");
        EntryButton colTblBtn = new EntryButton("Generate Table for Selected",
                14f, this, "colTblBtn");
        EntryButton colRemoveBtn = new EntryButton("Remove Selected",
                14f, this, "colRemoveBtn");
        colInput = new EntryTextField("Add a statement...", "[TF(a-z)][\\&\\|][TF(a-z)]");
        col.add(colList);
        colList.setBounds(194, 141, 200, 500);
        col.add(colAddBtn);
        colAddBtn.setBounds(460, 235, 67, 42);
        col.add(colEvalBtn);
        colEvalBtn.setBounds(460, 320, 247, 42);
        col.add(colTblBtn);
        colTblBtn.setBounds(460, 405, 247, 42);
        col.add(colRemoveBtn);
        colRemoveBtn.setBounds(460, 490, 247, 42);
        col.add(colInput);
        colInput.setBounds(551, 235, 156, 42);
    }

    // MODIFIES: this
    // EFFECTS: sets up the load page in the mainpanel
    private void setupLoad() {
        JPanel load = mainPanelList.get(3);

        load.setLayout(null);

        EntryButton loadBtn = new EntryButton("Load File",
                14f, this, "loadBtn");
        loadResult = new DescLabel("", 24f);

        load.add(loadBtn);
        loadBtn.setBounds(398, 206, 105, 42);
        load.add(loadResult);
        loadResult.setBounds(251, 292, 400, 55);
    }

    // MODIFIES: this
    // EFFECTS: sets up the save page in the mainpanel
    private void setupSave() {
        JPanel save = mainPanelList.get(4);

        save.setLayout(null);

        EntryButton saveBtn = new EntryButton("Save File",
                14f, this, "saveBtn");
        saveResult = new DescLabel("", 24f);

        save.add(saveBtn);
        saveBtn.setBounds(398, 206, 105, 42);
        save.add(saveResult);
        saveResult.setBounds(251, 292, 400, 55);

    }

    // MODIFIES: this
    // EFFECTS: shows the splash screen at the beginning of the program
    private void showSplash() {
        CardLayout cl = (CardLayout) (getContentPane().getLayout());
        cl.show(this.getContentPane(), "Splash Panel");

        Timer timer = new Timer(1000, this);
        timer.setActionCommand("switchBG");
        timer.setRepeats(false);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: toggles the side buttons if they are already pressed
    public void toggleSideButtons(SideButton current) {
        for (SideButton button : sideButtonList) {
            if (button.getPressed() & button != current) {
                button.pressButton();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: receives actionevents for the main window, routes button requests
    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (getContentPane().getLayout());
        CardLayout clMain = (CardLayout) (mainPanel.getLayout());
        switch (e.getActionCommand()) {
            case "switchBG": actionSwitchBG(cl);
                break;
            case "switchEval": actionSwitchMain("Eval", clMain);
                break;
            case "switchTable": actionSwitchMain("Table", clMain);
                break;
            case "switchCol": actionSwitchMain("Col",clMain);
                break;
            case "switchLoad": actionSwitchMain("Load",clMain);
                break;
            case "switchSave": actionSwitchMain("Save",clMain);
                break;
            case "evalBtn": actionEvalBtn();
                break;
            case "tblBtn": actionTblBtn();
                break;
            default:
                actionPerformed2(e,clMain);
        }
    }

    // MODIFIES: this
    // EFFECTS: part 2 of actionperformed due to checkstyle
    private void actionPerformed2(ActionEvent e, CardLayout clMain) {
        switch (e.getActionCommand()) {
            case "colAddBtn": actionColAddBtn();
                break;
            case "colEvalBtn": actionColEvalBtn(clMain);
                break;
            case "colTblBtn": actionColTblBtn(clMain);
                break;
            case "colRemoveBtn": actionColRemoveBtn();
                break;
            case "loadBtn": actionLoadBtn();
                break;
            case "saveBtn": actionSaveBtn();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: switches the splash screen
    private void actionSwitchBG(CardLayout cl) {
        cl.show(this.getContentPane(), "Main Split Panel");
        pack();
    }

    // MODIFIES: this
    // EFFECTS: switches to the card on mainPanel with name name
    private void actionSwitchMain(String name, CardLayout cl) {
        cl.show(mainPanel, name);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: run interpreter for the evaluate button
    private void actionEvalBtn() {
        if (evalInput.isValid(evalInput.getText())) {
            Interpreter engine = new Interpreter(evalInput.getText());
            evalResult.setText(engine.expr().toString());
            evalInput.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        } else {
            evalResult.setText("Unusable format. Please retry.");
            evalInput.setBorder(BorderFactory.createLineBorder(Color.red));
        }
    }

    // MODIFIES: this
    // EFFECTS: run logical statements table gen for tbl button
    private void actionTblBtn() {
        if (tblInput.isValid(tblInput.getText())) {
            LogicalStatement statement = new LogicalStatement(tblInput.getText());
            tblResult.setText(statement.generateTruthTable());
            tblInput.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        } else {
            tblResult.setText("Unusable format. Please retry.");
            tblInput.setBorder(BorderFactory.createLineBorder(Color.red));
        }
    }

    // MODIFIES: this
    // EFFECTS: collections page add statement button
    private void actionColAddBtn() {
        if (colInput.isValid(colInput.getText())) {
            colListModel.addElement(colInput.getText());
            collection.add(new LogicalStatement(colInput.getText()));
            colInput.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        } else {
            colInput.setBorder(BorderFactory.createLineBorder(Color.red));
        }
    }

    // MODIFIES: this
    // EFFECTS: collections page evaluate btn
    private void actionColEvalBtn(CardLayout cl) {
        if (!colList.isSelectionEmpty()) {
            cl.show(mainPanel, "Eval");
            evalInput.setText(colList.getSelectedValue());
            sideButtonList.get(0).pressButton();
            toggleSideButtons(sideButtonList.get(0));
        }
    }

    // MODIFIES: this
    // EFFECTS: collections page table btn
    private void actionColTblBtn(CardLayout cl) {
        if (!colList.isSelectionEmpty()) {
            cl.show(mainPanel, "Table");
            tblInput.setText(colList.getSelectedValue());
            sideButtonList.get(1).pressButton();
            toggleSideButtons(sideButtonList.get(1));
        }
    }

    // MODIFIES: this
    // EFFECTS: collections page remove entry button
    private void actionColRemoveBtn() {
        if (!colList.isSelectionEmpty()) {
            collection.remove(colList.getSelectedIndex());
            colListModel.remove(colList.getSelectedIndex());
        }
    }

    // MODIFIES: this
    // EFFECTS: load file from hard drive
    private void actionLoadBtn() {
        try {
            collection = reader.read();
        } catch (IOException ex) {
            loadResult.setText("File error!");
        }
        colListModel.clear();
        for (int i = 0; i < collection.size(); i++) {
            colListModel.addElement(collection.get(i).getInput());
        }
        loadResult.setText("Loaded successfully!");
    }

    // MODIFIES: this
    // EFFECTS: save current collection to hard drive
    private void actionSaveBtn() {
        try {
            writer.open();
            writer.write(collection);
            writer.close();
        } catch (FileNotFoundException ex) {
            saveResult.setText("File error!");
        }
        saveResult.setText("Saved successfully!");
    }

    // MODIFIES: this
    // EFFECTS: prints out the log on window close
    @Override
    public void dispose() {
        System.out.println("Logos Application Log:\n\n");
        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString() + "\n");
        }
        super.dispose();
    }
}

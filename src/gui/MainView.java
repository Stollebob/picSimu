package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Thomas on 25.05.2014.
 */
public class MainView extends JFrame {
    private JSplitPane mainPane;
    private JPanel leftPane;
    private JPanel rightPane;

    private JButton stopButton;
    private JButton startButton;
    private JButton resetbutton;
    private JButton nextButton;
    private JButton openButton;
    private JButton helpButton;
 /*Menu End*/

    private JPanel bankPanel;
    private JPanel registerPanel;
    private ScrollPane bankTableScroller;
    private JTable bankTable;
    private JTable registerATable;
    private JTable registerBTable;
    private JLabel b0Statuslabel;
    private JCheckBox b0indfbit7;
    private JCheckBox b0indfbit6;
    private JCheckBox b0indfbit5;
    private JCheckBox b0indfbit4;
    private JCheckBox b0indfbit3;
    private JCheckBox b0indfbit2;
    private JCheckBox b0indfbit1;
    private JCheckBox b0indfbit0;


    private JEditorPane editorText;
    private JPanel values;
    private JTextField textFieldW;
    private JTextField textFieldCycles;
    private JTextField textFieldPC;
    private JPanel stack;

    private CustomTableModel tableModel;
    private JTextArea programArea;

    public MainView(String title) throws HeadlessException
    {
        super(title);
        initializeMainView();
        this.add(this.mainPane);
    }

    private void initializeMainView()
    {
        this.setSize(800, 600);
        this.setLocation(300, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        initializePanes();
        this.mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.leftPane, this.rightPane);
//        this.mainPane.setDividerLocation(-15);
    }

    private void initializePanes()
    {
        initializeLeftPane();
        this.add(leftPane);
        initializeRightPane();
        this.add(rightPane);
    }

    private void initializeLeftPane()
    {
        initializeBank();
        leftPane = new JPanel();
        leftPane.add(bankPanel, BorderLayout.NORTH);
        Dimension preferredSize = new Dimension(300, 600);
        leftPane.setPreferredSize(preferredSize);
        this.leftPane.setMinimumSize(preferredSize);
        this.leftPane.setMaximumSize(preferredSize);

    }

    private void initializeRightPane()
    {
        initializeRegisterBanks();
        this.rightPane = new JPanel();
        this.programArea = new JTextArea(10, 10);
        Dimension programmAreaPreferredSize = new Dimension(30, 30);
        this.programArea.setPreferredSize(programmAreaPreferredSize);
        this.programArea.setMinimumSize(programmAreaPreferredSize);
        this.programArea.setMaximumSize(programmAreaPreferredSize);
        this.programArea.setEditable(false);
        /*
        JScrollPane scrollPane = new JScrollPane(this.programArea);
        scrollPane.setPreferredSize(new Dimension(20, 20));
        this.rightPane.add(scrollPane);
        */
        this.rightPane.add(programArea);
        Dimension preferredSize = new Dimension(500, 600);
        this.rightPane.setPreferredSize(preferredSize);
        this.rightPane.setMinimumSize(preferredSize);
        this.rightPane.setMaximumSize(preferredSize);
    }

    private void initializeBank()
    {
        bankTableScroller = new ScrollPane();
        bankPanel = new JPanel();
        tableModel = new CustomTableModel();
        bankTable = new JTable(tableModel);
        bankTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for(int column = 0; column <= 8 ; column++)
        {
            bankTable.getColumnModel().getColumn(column).setPreferredWidth(25);
        }
        bankTableScroller.setPreferredSize(new Dimension(250, 300));
        bankTableScroller.add(bankTable);
        bankPanel.add(bankTableScroller);
        bankPanel.setPreferredSize(new Dimension(300, 300));
    }

    private void initButtons()
    {
        openButton = new JButton("Open");
        openButton.setToolTipText("Programm öffnen");
//        openButton.addActionListener(buttonListener);
        startButton = new JButton("Start");
        startButton.setEnabled(false);
        startButton.setToolTipText("Programm starten");
//        startButton.addActionListener(buttonListener);
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.setEnabled(false);
//        stopButton.addActionListener(buttonListener);
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
//        nextButton.addActionListener(buttonListener);
        resetbutton = new JButton("Reset");
        resetbutton.setEnabled(false);
//        resetbutton.addActionListener(buttonListener);
        helpButton = new JButton("Help");
//        helpButton.addActionListener(buttonListener);
    }
}

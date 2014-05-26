package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Bastian on 24/05/2014.
 */

public class Main extends JFrame {
    private JPanel mainpanel;
 /*Menu Begin*/

    private JButton stopButton;
    private JButton StartButton;
    private JButton Resetbutton;
    private JButton NextButton;
    private JButton OpenButton;
    private JButton HelpButton;
 /*Menu End*/

    private JEditorPane editorText;
    private JPanel Values;
    private JTextField textFieldW;
    private JTextField textFieldCycles;
    private JTextField textFieldPC;
    private JPanel Stack;
    private JTable bankTable;
    private JPanel tableBank;


    public Main() throws HeadlessException {
        super("PicSimu");
        setContentPane(mainpanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

 /*Menu initialisieren*/

    private void initButtons()
    {
        OpenButton = new JButton("Open");
        OpenButton.setToolTipText("Programm öffnen");
//        OpenButton.addActionListener(buttonListener);
        StartButton = new JButton("Start");
        StartButton.setEnabled(false);
        StartButton.setToolTipText("Programm starten");
//        StartButton.addActionListener(buttonListener);
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.setEnabled(false);
//        stopButton.addActionListener(buttonListener);
        NextButton = new JButton("Next");
        NextButton.setEnabled(false);
//        NextButton.addActionListener(buttonListener);
        Resetbutton = new JButton("Reset");
        Resetbutton.setEnabled(false);
//        Resetbutton.addActionListener(buttonListener);
        HelpButton = new JButton("Help");
//        HelpButton.addActionListener(buttonListener);
    }

    public void setData(IndfBean data) {
    }

    public void getData(IndfBean data) {
    }

    public boolean isModified(IndfBean data) {
        return false;
    }

    private void createUIComponents() {
        bankTable = new JTable(new CustomTableModel());
    }
}


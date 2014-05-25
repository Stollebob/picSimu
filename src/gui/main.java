package gui;

import javax.swing.*;

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

    private JPanel Bank0;
    private JLabel b0Statuslabel;
    private JCheckBox b0indfbit7;
    private JCheckBox b0indfbit6;
    private JCheckBox b0indfbit5;
    private JCheckBox b0indfbit4;
    private JCheckBox b0indfbit3;
    private JCheckBox b0indfbit2;
    private JCheckBox b0indfbit1;
    private JCheckBox b0indfbit0;
    private JPanel Bank1;
    private JEditorPane editorText;
    private JPanel Values;
    private JTextField textFieldW;
    private JTextField textFieldCycles;
    private JTextField textFieldPC;
    private JPanel Stack;


    public void initializeMainView()
    {
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
}


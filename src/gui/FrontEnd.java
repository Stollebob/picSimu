package gui;

import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by Bastian on 24/05/2014.
 */

public class FrontEnd extends JFrame {
    private JPanel mainpanel;
 /*Menu Begin*/
    private JButton stopButton;
    private JButton StartButton;
    private JButton Resetbutton;
    private JButton NextButton;
    private JButton OpenButton;
    private JButton helpButton;
 /*Menu End*/

    private JEditorPane editorText;
    private JPanel Values;
    private JTextField textFieldW;
    private JTextField textFieldCycles;
    private JTextField textFieldPC;
    private JPanel Stack;
    private JTable bankTable;
    private JPanel tableBank;
    private CustomTableModel customTableModel;


    public FrontEnd() throws HeadlessException {
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
        helpButton = new JButton("Help");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello World!!!!");
                try
                {
                    Desktop.getDesktop().open(new File("D:\\35007b.pdf"));
                }
                catch (IOException ex)
                {
                }
            }
        });
    }

    public void setData(IndfBean data) {
    }

    public void getData(IndfBean data) {
    }

    public boolean isModified(IndfBean data) {
        return false;
    }

    private void createUIComponents() {
        customTableModel = new CustomTableModel();
        bankTable = new JTable(customTableModel);
    }

    public void redrawGui(MemoryManagementUnit mmu) throws InvalidRegisterException
    {
        for(int counter = 0; counter <= 255; counter++)
        {
            String hexAddress = Integer.toHexString(counter);
            String hexValue = new BigInteger("" + mmu.getRegister(hexAddress).getIntValue(), 10).toString(16);
            int offset = counter%8;
            int row = (counter - offset) / 8;
            this.customTableModel.setValueAt(hexValue, row, offset + 1);
        }
    }
}


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
        helpButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    Desktop.getDesktop().open(new File("resources\\help\\35007b.pdf"));
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        OpenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
        initButtons();
        customTableModel = new CustomTableModel();
        bankTable = new JTable(customTableModel);
    }

    public void redrawGui(MemoryManagementUnit mmu) throws InvalidRegisterException
    {
        for(int counter = 0; counter <= 255; counter++)
        {
            String hexAddress = Integer.toHexString(counter);
            String hexValue =  Integer.toHexString(mmu.getRegister(hexAddress).getIntValue());
            int offset = counter%8;
            int row = (counter - offset) / 8;
            this.customTableModel.setValueAt(row, offset + 1, hexValue);
        }
    }
}

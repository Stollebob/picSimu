package gui;

import controller.event.open.OpenEvent;
import controller.event.open.OpenListener;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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
    /* Stack Overview */
    private JTextField jTextStack0;
    private JTextField jTextStack1;
    private JTextField jTextStack2;
    private JTextField jTextStack3;
    private JTextField jTextStack4;
    private JTextField jTextStack5;
    private JTextField jTextStack6;
    private JTextField jTextStack7;
    private CustomTableModel customTableModel;



    private OpenListener fileOpenListener;



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

        OpenButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if(f.isDirectory())
                        {
                            return true;
                        }
                        String fileExtension = null;
                        String name = f.getName();
                        int pos = name.lastIndexOf(".");
                        if(pos > 0 && pos < name.length() - 1)
                        {
                            fileExtension = name.substring(pos + 1);
                        }
                        if(fileExtension != null && fileExtension.equalsIgnoreCase("LST"))
                        {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }
                });
                int returnVal = chooser.showOpenDialog(FrontEnd.this);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = chooser.getSelectedFile();
                    firePropertyChange(file);
                }
            }
        });
    }

    private void createUIComponents()
    {
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

    private void firePropertyChange(File toOpen)
    {
        fileOpenListener.actionPerformed(new OpenEvent(toOpen));
    }

    public void addFileOpenListener(OpenListener listener)
    {
        this.fileOpenListener = listener;
    }

    /* Update Text into Stack-Overview */
    private void setjTextStack(){
        for(int i = 0; i < 7; i++){
            ("jTextStack"+i).setText();
        }
    }
}

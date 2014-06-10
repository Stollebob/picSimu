package gui;

import controller.event.open.OpenEvent;
import controller.event.open.OpenListener;
import controller.event.start.StartEvent;
import controller.event.start.StartListener;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by Bastian on 24/05/2014.
 */

public class FrontEnd extends JFrame implements ActionListener {
    private JPanel mainpanel;
 /*Menu Begin*/
    private JButton stopButton;
    private JButton startButton;
    private JButton resetButton;
    private JButton nextButton;
    private JButton openButton;
    private JButton helpButton;
 /*Menu End*/

    private JEditorPane editorText;
    private JPanel values;
    private JTextField textFieldW;
    private JTextField textFieldCycles;
    private JTextField textFieldPC;
    private JPanel stack;
    private JTable bankTable;
    private JPanel tableBank;
    /* stack Overview */
    private JTextField jTextStack0;
    private JTextField jTextStack1;
    private JTextField jTextStack2;
    private JTextField jTextStack3;
    private JTextField jTextStack4;
    private JTextField jTextStack5;
    private JTextField jTextStack6;
    private JTextField jTextStack7;
    private CustomTableModel customTableModel;

    private Timer timer;

    private OpenListener fileOpenListener;
    private StartListener startListener;



    public FrontEnd() throws HeadlessException {
        super("PicSimu");
        setContentPane(mainpanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        stopButton.addActionListener(this);
        startButton.addActionListener(this);
        resetButton.addActionListener(this);
        nextButton.addActionListener(this);
        openButton.addActionListener(this);
        helpButton.addActionListener(this);
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
        this.setjTextStack(mmu);
    }

    private void firePropertyChange(File toOpen)
    {
        fileOpenListener.actionPerformed(new OpenEvent(toOpen));
    }

    public void addFileOpenListener(OpenListener listener)
    {
        this.fileOpenListener = listener;
    }

    /* Update Text into stack-Overview */
    private void setjTextStack(MemoryManagementUnit mmu)
    {
        Stack<Integer> stack = mmu.getStackData();
        resetJTextStack();
        switch(stack.size())
        {
            case 8:
            {
                jTextStack7.setText("" + stack.elementAt(7));
            }
            case 7:
            {
                jTextStack6.setText("" + stack.elementAt(6));
            }
            case 6:
            {
                jTextStack5.setText("" + stack.elementAt(5));
            }
            case 5:
            {
                jTextStack4.setText("" + stack.elementAt(4));
            }
            case 4:
            {
                jTextStack3.setText("" + stack.elementAt(3));
            }
            case 3:
            {
                jTextStack2.setText("" + stack.elementAt(2));
            }
            case 2:
            {
                jTextStack1.setText("" + stack.elementAt(1));
            }
            case 1:
            {
                jTextStack0.setText("" + stack.elementAt(0));
                break;
            }
        }
    }

    private void resetJTextStack() {
        jTextStack0.setText("");
        jTextStack1.setText("");
        jTextStack2.setText("");
        jTextStack3.setText("");
        jTextStack4.setText("");
        jTextStack5.setText("");
        jTextStack6.setText("");
        jTextStack7.setText("");
    }

    private void firePropertyChange()
    {
        this.startListener.actionPerformed(new StartEvent());
    }

    public void addStartListener(StartListener listener)
    {
        this.startListener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.helpButton)
        {
            try
            {
                Desktop.getDesktop().open(new File("resources\\help\\35007b.pdf"));
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == this.nextButton)
        {
        }
        else if(e.getSource() == this.openButton)
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }
                    String fileExtension = null;
                    String name = f.getName();
                    int pos = name.lastIndexOf(".");
                    if (pos > 0 && pos < name.length() - 1) {
                        fileExtension = name.substring(pos + 1);
                    }
                    if (fileExtension != null && fileExtension.equalsIgnoreCase("LST")) {
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

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                firePropertyChange(file);
            }
        }
        else if(e.getSource() == this.startButton)
        {
            firePropertyChange();
        }
        else if(e.getSource() == this.resetButton)
        {
        }
        else if(e.getSource() == this.startButton)
        {
        }
    }
}

package gui;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;

/**
 * Created by Thomas on 25.05.2014.
 */
public class MainView extends JFrame {
    private JPanel mainpanel;
 /*Menu Begin*/

    private JButton stopButton;
    private JButton startButton;
    private JButton resetbutton;
    private JButton nextButton;
    private JButton openButton;
    private JButton helpButton;
 /*Menu End*/

    private JPanel bank0Panel;
    private JTable bank0Table;
    private JLabel b0Statuslabel;
    private JCheckBox b0indfbit7;
    private JCheckBox b0indfbit6;
    private JCheckBox b0indfbit5;
    private JCheckBox b0indfbit4;
    private JCheckBox b0indfbit3;
    private JCheckBox b0indfbit2;
    private JCheckBox b0indfbit1;
    private JCheckBox b0indfbit0;


    private JPanel bank1;
    private JEditorPane editorText;
    private JPanel values;
    private JTextField textFieldW;
    private JTextField textFieldCycles;
    private JTextField textFieldPC;
    private JPanel stack;

    public MainView(String title) throws HeadlessException
    {
        super(title);
        initializeMainView();
    }

    private void initializeMainView()
    {
        this.setSize(800, 600);
        this.setLocation(300, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        initializeBank0();
        this.add(bank0Panel);
    }

    private void initializeBank0()
    {
        bank0Panel = new JPanel();
        String [][] columnData = new String[8][31];
        for (int rowValue = 0; rowValue <= 30; rowValue ++)
        {
            for(int columnValue = 0; columnValue <= 7; columnValue++)
            {
                if(columnValue == 0)
                {
                    columnData[columnValue][rowValue] = new BigInteger("" + rowValue * 8, 10).toString(16);
                }
                else
                {
                    columnData[columnValue][rowValue] = "00";
                }

            }
        }
        bank0Table = new JTable(columnData, new String[]{" ","00","01","02","03","04","05","06","07"});
        bank0Panel.add(bank0Table);
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

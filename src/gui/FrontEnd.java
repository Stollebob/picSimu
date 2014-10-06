package gui;

import controller.event.frequency.FrequencyChangeEvent;
import controller.event.frequency.FrequencyChangeListener;
import com.sun.org.apache.xpath.internal.operations.Bool;
import controller.event.next.NextEvent;
import controller.event.next.NextListener;
import controller.event.open.OpenEvent;
import controller.event.open.OpenListener;
import controller.event.reset.ResetEvent;
import controller.event.reset.ResetListener;
import controller.event.start.StartEvent;
import controller.event.start.StartListener;
import controller.event.stop.StopEvent;
import controller.event.stop.StopListener;
import controller.event.tris.TrisChangeEvent;
import controller.event.tris.TrisChangeListener;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Stack;

/**
 * Created by Bastian on 24/05/2014.
 */

public class FrontEnd extends JFrame implements View, ActionListener, ChangeListener, TableModelListener
{
    private JPanel mainpanel;
 /*Menu Begin*/
    private JButton stopButton;
    private JButton startButton;
    private JButton resetButton;
    private JButton nextButton;
    private JButton openButton;
    private JButton helpButton;
 /* Menu End */
 /* Values, Registers and Banks Begin */
    private JTable editorText;
    private JPanel values;
    private JTextField textFieldW;
    private JTextField textFieldCycles;
    private JTextField textFieldPC;
    private JTextField textFieldDC;
    private JTextField textFieldC;
    private JTextField textFieldZ;
    private JPanel stack;
    private JPanel tableBank;
    private JTable bankTable;
    private JPanel registerRA;
    private JTable registerATable;
    private JPanel registerRB;
    private JTable registerBTable;
 /* Values, Registers and Banks Begin */
 /* stack Overview Start */
    private JTextField jTextStack0;
    private JTextField jTextStack1;
    private JTextField jTextStack2;
    private JTextField jTextStack3;
    private JTextField jTextStack4;
    private JTextField jTextStack5;
    private JTextField jTextStack6;
    private JTextField jTextStack7;
    private JSlider frequenzSlider;
    private JTextField TextFieldFrequenz;
    /* stack Overview End */
    private CustomTableModel customTableModel;

    private OpenListener fileOpenListener;
    private StartListener startListener;
    private StopListener stopListener;
    private NextListener nextListener;
    private ResetListener resetListener;
    private TrisChangeListener trisChangeListener;
    private FrequencyChangeListener frequencyChangeListener;

 /* Initialization FrontEnd Start */
    public FrontEnd() throws HeadlessException {
        super("PicSimu");
        setContentPane(mainpanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        /* Activating Buttons */
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
        editorText = (JTable) makeEditor();
        registerATable = (JTable)makeTrisTable();
        registerATable.getModel().addTableModelListener(this);
        registerBTable = (JTable)makeTrisTable();
        registerBTable.getModel().addTableModelListener(this);
        frequenzSlider = new JSlider();
        frequenzSlider.addChangeListener(this);
    }

    private void redrawGui(MemoryManagementUnit mmu) throws InvalidRegisterException
    {
        for(int counter = 0; counter <= 255; counter++)
        {
            String hexAddress = Integer.toHexString(counter);
            String hexValue =  Integer.toHexString(mmu.getRegister(hexAddress).getIntValue());
            int offset = counter%8;
            int row = (counter - offset) / 8;
            this.customTableModel.setValueAt(row, offset + 1, hexValue);
        }
        /*setup Tris A and B*/
        DefaultTableModel modelA = (DefaultTableModel) registerATable.getModel();
        DefaultTableModel modelB = (DefaultTableModel) registerBTable.getModel();

        modelA.addRow(convertBinaryToObjectArray(mmu.getRegister("5").getBinaryValue()));
        modelB.addRow(convertBinaryToObjectArray(mmu.getRegister("6").getBinaryValue()));

        if(modelA.getRowCount() > 1)
        {
            modelA.removeRow(0);
        }
        if(modelB.getRowCount() > 1)
        {
            modelB.removeRow(0);
        }

        /*setup "values"*/
        this.textFieldW.setText("" + new BigInteger("" + mmu.getWorkingRegister().getIntValue(), 10).toString(16));
        this.textFieldCycles.setText("" + mmu.getCycles());
        this.textFieldPC.setText("" + new BigInteger("" + mmu.getPC(), 10).toString(16));
        if (mmu.isCarry())
        {
            this.textFieldC.setText("1");
        } else {
            this.textFieldC.setText("0");
        }

        if (mmu.isDigitCarry())
        {
            this.textFieldDC.setText("1");
        } else {
            this.textFieldDC.setText("0");
        }
        if (mmu.isZero())
        {
            this.textFieldZ.setText("1");
        } else {
            this.textFieldZ.setText("0");
        }
        this.TextFieldFrequenz.setText("" + frequenzSlider.getValue());

        /*Highlighting*/
        DefaultTableModel model = (DefaultTableModel) editorText.getModel();
        if(model.getRowCount() > 0)
        {
            int index = 0;
            String pc;
            pc = new BigInteger("" + mmu.getPC(), 10).toString(16);
            while(pc.length() < 4)//führende Nullen anhängen
            {
                pc = "0" + pc;
            }
            pc = " " + pc;//führendes Leerzeichen anhängen
            for(; index < model.getRowCount(); index++)
            {
                String courentValue = (String)model.getValueAt(index, 1);
                if(courentValue.equalsIgnoreCase(pc))
                {
                    break;
                }
            }
            editorText.setRowSelectionInterval(index, index);
            editorText.scrollRectToVisible(editorText.getCellRect(index, 0, true));
            if(index < model.getRowCount())
            {
                editorText.setRowSelectionInterval(index, index);
                editorText.scrollRectToVisible(editorText.getCellRect(index, 0, true));
                if((Boolean)model.getValueAt(index, 0))
                {
                    fireStopEvent();
                }
            }
        }

        this.setjTextStack(mmu);
        this.repaint();
    }

    private Object[] convertBinaryToObjectArray(String binayValue) throws InvalidRegisterException {
        Object[] insert = new Object[8];
        BigInteger value = new BigInteger(binayValue, 2);
        for (int counter = 0; counter < 8; counter++)
        {
            insert[counter] = value.testBit(counter);
        }
        return insert;
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
        Stack<String> stack = mmu.getStackData();
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

    private void resetJTextStack()
    {
        jTextStack0.setText("");
        jTextStack1.setText("");
        jTextStack2.setText("");
        jTextStack3.setText("");
        jTextStack4.setText("");
        jTextStack5.setText("");
        jTextStack6.setText("");
        jTextStack7.setText("");
    }

    private void fireStartEvent()
    {
        this.startListener.actionPerformed(new StartEvent());
    }

    public void addStartListener(StartListener listener)
    {
        this.startListener = listener;
    }

    private void fireStopEvent()
    {
        this.stopListener.actionPerformed(new StopEvent());
    }

    public void addStopListener(StopListener listener)
    {
        this.stopListener = listener;
    }

    private void fireNextEvent()
    {
        this.nextListener.actionPerformed(new NextEvent());
    }

    public void addNextListener(NextListener listener)
    {
        this.nextListener = listener;
    }

    private void fireResetEvent()
    {
        this.resetListener.actionPerformed(new ResetEvent());
    }

    public void addResetListener(ResetListener listener)
    {
        this.resetListener = listener;
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
            fireNextEvent();
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
            fireStartEvent();
        }
        else if(e.getSource() == this.resetButton)
        {
            fireResetEvent();
        }
        else if(e.getSource() == this.stopButton)
        {
            fireStopEvent();
        }
    }

    @Override
    public void update(MemoryManagementUnit mmu)
    {
        try
        {
            redrawGui(mmu);
        }
        catch (InvalidRegisterException e)
        {
            e.printStackTrace();
        }
    }

    public void addLineToEditor(String line)
    {
        ((DefaultTableModel)editorText.getModel()).addRow(new Object[]{false, " "+ line.substring(0, 4), line.substring(5)});
    }

    public void clearEditor()
    {
        DefaultTableModel model = (DefaultTableModel) editorText.getModel();
        for(int index = model.getRowCount() -1; index >= 0 ; index--)
        {
            model.removeRow(index);
        }
    }

    public JComponent makeEditor()
    {
        String[] columnNames = {"test", "test", "test"};
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
            @Override public Class<?> getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        JTable table = new JTable(model);
        //Größe auf Checkoxen anpassen
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(0).setMaxWidth(20);
        //Größe auf PC anpassen
        table.getColumnModel().getColumn(1).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setMaxWidth(40);
        table.setTableHeader(null);
        table.setRowHeight(20);
        table.setAutoCreateRowSorter(true);
        return table;
    }

    public JComponent makeTrisTable()
    {
        String[] columnNames = {"test", "test", "test", "test", "test", "test", "test", "test"};
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
            @Override public Class<?> getColumnClass(int column)
            {
                return Boolean.class;
            }
        };
        JTable table = new JTable(model);
        for(int index = 0; index < 8; index++)
        {
            table.getColumnModel().getColumn(index).setPreferredWidth(30);
            table.getColumnModel().getColumn(index).setMaxWidth(30);
        }
        table.setTableHeader(null);
        table.setRowHeight(20);
        table.setAutoCreateRowSorter(true);
        return table;
    }

    public void stateChanged(ChangeEvent e)
    {
        if(this.frequenzSlider == e.getSource())
        {
            if(!frequenzSlider.getValueIsAdjusting())
            {
                int frequenz = frequenzSlider.getValue();
                long delay = 1/(frequenz * 10^6);
                firePropertyChange(delay);
            }
        }
    }

    @Override
    public void tableChanged(TableModelEvent e)
    {
        int columIndex = e.getColumn();
        if(columIndex != TableModelEvent.ALL_COLUMNS)
        {
            if(e.getSource().equals(registerATable.getModel()))
            {
                firePropertyChange("5", columIndex, (boolean)registerATable.getValueAt(0, columIndex));
            }
            else
            {
                firePropertyChange("6", columIndex, (boolean)registerBTable.getValueAt(0, columIndex));
            }
        }

    }

    private void firePropertyChange(String hexAdress, int index, boolean newValue)
    {
        trisChangeListener.actionPerformed(new TrisChangeEvent(hexAdress, index, newValue));
    }

    public void addTrisChangeListener(TrisChangeListener listener)
    {
        this.trisChangeListener = listener;
    }

    private void firePropertyChange(long newDelay)
    {
        if(frequencyChangeListener != null)
        {
            frequencyChangeListener.actionPerformed(new FrequencyChangeEvent(newDelay));
        }
    }

    public void addFrequencyChangeListener(FrequencyChangeListener listener)
    {
        this.frequencyChangeListener = listener;
    }
}


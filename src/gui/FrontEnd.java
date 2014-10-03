package gui;

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
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.EventObject;
import java.util.Stack;

/**
 * Created by Bastian on 24/05/2014.
 */

public class FrontEnd extends JFrame implements View, ActionListener
{
    private JPanel mainpanel;
 /*Menu Begin*/
    private JButton stopButton;
    private JButton startButton;
    private JButton resetButton;
    private JButton nextButton;
    private JButton openButton;
    private JButton helpButton;
 /*Menu End*/

    private JTable editorText;
    private JPanel values;
    private JTextField textFieldW;
    private JTextField textFieldCycles;
    private JTextField textFieldPC;
    private JPanel stack;
    private JTable bankTable;
    private JPanel tableBank;
    private JPanel RegisterRA;
    /* stack Overview */
    private JTextField jTextStack0;
    private JTextField jTextStack1;
    private JTextField jTextStack2;
    private JTextField jTextStack3;
    private JTextField jTextStack4;
    private JTextField jTextStack5;
    private JTextField jTextStack6;
    private JTextField jTextStack7;
    private JPanel RegisterRB;
    private JTable registerATable;
    private JTable registerBTable;

    private CustomTableModel customTableModel;

    private OpenListener fileOpenListener;
    private StartListener startListener;
    private StopListener stopListener;
    private NextListener nextListener;
    private ResetListener resetListener;



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
        editorText = (JTable) makeEditor();
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
//            bankTable.setModel(customTableModel);
        }
        this.textFieldW.setText("" + new BigInteger("" + mmu.getWorkingRegister().getIntValue(), 10).toString(16));
        this.textFieldCycles.setText("" + mmu.getCycles());
        this.textFieldPC.setText("" + new BigInteger("" + mmu.getPC(), 10).toString(16));

        this.setjTextStack(mmu);
        this.repaint();
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
        ((DefaultTableModel)editorText.getModel()).addRow(new Object[]{false, line});
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
        String[] columnNames = {"test", "test"};
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
            @Override public Class<?> getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        JTable table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(0).setMaxWidth(20);
        table.setTableHeader(null);
        table.setRowHeight(20);
        table.setAutoCreateRowSorter(true);
        return table;
    }
}

package commands;

import exceptions.InvalidRegisterException;
import gui.FrontEnd;
import register.MemoryManagementUnit;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 16.05.2014.
 */
public class CommandExecutor
{
    private List<Command> commandList = new ArrayList<>();
    private MemoryManagementUnit mmu = new MemoryManagementUnit();
    private int cycles = 0;
    private boolean interrupt = false;

    public void setCommandList(List<Command> commandList)
    {
        this.commandList = commandList;
    }

    public void work(final FrontEnd view) throws InvalidRegisterException
    {
        javax.swing.Timer timer = new Timer(100 , new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    view.redrawGui(mmu);
                }
                catch (InvalidRegisterException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        timer.setRepeats(true);
        timer.start();

        while(mmu.getPC() < commandList.size())
        {
            //Cycles in den Timer übertragen (+1 um Interrupt nicht beim Start zu beachten)
            mmu.getRegister("01h").setIntValue(cycles+1);
            //Auf Interrupt prüfen und gegebenenfalls bearbeiten.
            do {
               interrupt = mmu.getInterrupt();
            } while (interrupt == true);
            System.out.println(mmu.getPC());
            Command command = commandList.get(mmu.getPC());
            mmu.incPC();
            mmu = command.execute(mmu);
            cycles += command.getCycles();
        }
    }
}

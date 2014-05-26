package commands;

import exceptions.InvalidRegisterException;
import gui.FrontEnd;
import register.MemoryManagementUnit;

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

    public CommandExecutor(List<Command> commandList)
    {
        this.commandList = commandList;
    }

    public void work(FrontEnd view) throws InvalidRegisterException
    {
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
            view.redrawGui(mmu);
        }
    }
}

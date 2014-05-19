package commands;

import exceptions.InvalidRegisterException;
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

    public CommandExecutor(List<Command> commandList)
    {
        this.commandList = commandList;
    }

    public void work() throws InvalidRegisterException
    {
        while(mmu.getPC() < commandList.size())
        {
            Command command = commandList.get(mmu.getPC());
            mmu.incPC();
            mmu = command.execute(mmu);
            cycles += command.getCycles();
        }
    }
}

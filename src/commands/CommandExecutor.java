package commands;

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
    private int clock = 0;

    public CommandExecutor(List<Command> commandList)
    {
        this.commandList = commandList;
    }

    public void work()
    {
        for(int pc = 0; pc < commandList.size(); pc++)
        {
            Command command = commandList.get(pc);
            mmu = command.execute(mmu);
            clock += command.getCycles();
        }
    }
}

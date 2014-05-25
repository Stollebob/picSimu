package commands;

import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;
import register.Register;

import java.math.BigInteger;
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
            int interrupt = mmu.getInterrupt();
            if ((interrupt == 3) || (interrupt == 2) || (interrupt == 1))
            while ((interrupt == 3) || (interrupt == 2) || (interrupt == 1)){
                interrupt = mmu.getInterrupt();
            }
            System.out.println(mmu.getPC());
            Command command = commandList.get(mmu.getPC());
            mmu.incPC();
            mmu = command.execute(mmu);
            cycles += command.getCycles();
        }
    }
}

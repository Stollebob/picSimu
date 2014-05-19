package commands.LiteralAndControlOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

/**
 * Created by Thomas on 19.05.2014.
 */
public class Return extends LiteralAndControlOperation implements Command
{
    private int cycles = 2;

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        try
        {
            mmu.reloadPcFromStack();
        }
        catch (InvalidRegisterException e)
        {
            e.printStackTrace();
        }
        return mmu;
    }

    @Override
    public int getCycles()
    {
        return cycles;
    }
}
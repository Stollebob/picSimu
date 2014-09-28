package commands.LiteralAndControlOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

/**
 * Created by Thomas on 19.05.2014.
 */
public class RetLW extends LiteralAndControlOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycles = 2;

    public RetLW(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        try
        {
            mmu.setWorkingRegister(decodeSingle8BitArgument(commandString));
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
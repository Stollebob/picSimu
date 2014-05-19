package commands.LiteralAndControlOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by bastian.donat on 19.05.2014.
 */
public class GoTo extends LiteralAndControlOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycles = 2;

    public GoTo(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        try
        {
            String argument = decodeSingle11BitArgument(commandString);
            int intK = new BigInteger(argument, 2).intValue();
            mmu.setPC(intK);
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

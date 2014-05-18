package commands.LiteralAndControlOperations;

import commands.Command;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by Thor on 17.05.14.
 */
public class MovLW extends LiteralAndControlOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public MovLW(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        String argument = decodeSingle8BitArgument(commandString);
        int argumentK = new BigInteger(argument, 2).intValue();
        mmu.getWorkingRegister().setIntValue(argumentK);
        return mmu;
    }

    @Override
    public int getCycles()
    {
        return cycels;
    }
}
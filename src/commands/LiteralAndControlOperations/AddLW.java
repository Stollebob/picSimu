package commands.LiteralAndControlOperations;

import commands.Command;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by Thor on 18.05.14.
 */
public class AddLW extends LiteralAndControlOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycles = 1;

    public AddLW(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        String argument = decodeSingle8BitArgument(commandString);
        int intW = mmu.getWorkingRegister().getIntValue();
        int intK = new BigInteger(argument, 2).intValue();
        int result = intK + intW;
        mmu.setWorkingRegister(result);
        checkZ(result);
        checkDC(intK, intW, true);
        checkC(intK, intW, true);
        return mmu;
    }

    @Override
    public int getCycles()
    {
        return cycles;
    }
}
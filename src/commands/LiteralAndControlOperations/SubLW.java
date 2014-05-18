package commands.LiteralAndControlOperations;

import commands.Command;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by Thor on 18.05.14.
 */
public class SubLW extends LiteralAndControlOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public SubLW(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        String argument = decodeSingle8BitArgument(commandString);
        int intW = mmu.getWorkingRegister().getIntValue();
        int intK = new BigInteger(argument, 2).intValue();
        int result = intK - intW;
        mmu.getWorkingRegister().setIntValue(result);
        checkZ(result);
        checkDC(intK, intW , false);
        checkC(intK, intW , false);
        return mmu;
    }

    @Override
    public int getCycles()
    {
        return cycels;
    }
}
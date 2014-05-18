package commands.LiteralAndControlOperations;

import commands.Command;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by Thor on 18.05.14.
 */
public class AndLW extends LiteralAndControlOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public AndLW(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        String argument = decodeSingle8BitArgument(commandString);
        String binaryValue = mmu.getWorkingRegister().getBinaryValue();
        BigInteger argumentK = new BigInteger(argument, 2);
        int result = new BigInteger(binaryValue ,2).and(argumentK).intValue();
        mmu.getWorkingRegister().setIntValue(result);
        checkZ(result);
        return mmu;
    }

    @Override
    public int getCycles()
    {
        return cycels;
    }
}
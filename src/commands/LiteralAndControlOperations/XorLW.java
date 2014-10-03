package commands.LiteralAndControlOperations;

import commands.Command;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by Thor on 18.05.14.
 */
public class XorLW extends LiteralAndControlOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public XorLW(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        String argument = decodeSingle8BitArgument(commandString);
        String binaryValue = mmu.getWorkingRegister().getBinaryValue();
        BigInteger argumentK = new BigInteger(argument, 2);
        int result = new BigInteger(binaryValue ,2).xor(argumentK).intValue();
        mmu.setWorkingRegister(result);
        if(checkZ(result))
        {
            mmu.setZero();
        }
        else
        {
            mmu.resetZero();
        }
        return mmu;
    }

    @Override
    public int getCycles()
    {
        return cycels;
    }
}
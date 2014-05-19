package commands.LiteralAndControlOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by bastian.donat on 19.05.2014.
 */
public class RetFie extends LiteralAndControlOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycles = 2;

    public RetFie(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        try
        {
            mmu.reloadPcFromStack();
            String intConBinaryValue = mmu.getRegister("0Bh").getBinaryValue();
            int result = new BigInteger(intConBinaryValue, 2).setBit(8).intValue();
            mmu.getRegister("0Bh").setIntValue(result);
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
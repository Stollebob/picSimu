package commands.ByteOrientedFileRegisterOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by Thor on 17.05.14.
 */
public class MovWF extends ByteOrientedFileRegisterOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public MovWF(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        try
        {
            String argument;
            argument = decodeSingleArgument(commandString);
            BigInteger f = new BigInteger(argument, 2);
            mmu.setRegisterIntValue(f.toString(16), mmu.getWorkingRegister().getIntValue());
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
        return cycels;
    }
}
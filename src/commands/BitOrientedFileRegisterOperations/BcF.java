package commands.BitOrientedFileRegisterOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;
import register.Register;

import java.math.BigInteger;

/**
 * Created by Thor on 17.05.14.
 */
public class BcF extends BitOrientedFileRegisterOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public BcF(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        try
        {
            String[] arguments;
            arguments = decodeArguments(commandString);
            int argB = new BigInteger(arguments[0] , 2).intValue();
            int indexInString = 7 - argB;
            Register register_f = mmu.getRegister(new BigInteger("0" + arguments[1], 2).toString(16));
            int newValue = new BigInteger(register_f.getBinaryValue() , 2).clearBit(indexInString).intValue();
            register_f.setIntValue(newValue);
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
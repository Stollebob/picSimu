package commands.BitOrientedFileRegisterOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;
import register.Register;

import java.math.BigInteger;

/**
 * Created by Thor on 17.05.14.
 */
public class BsF extends BitOrientedFileRegisterOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public BsF(String commandString)
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
            int indexInString = argB;
            String address = new BigInteger("0" + arguments[1], 2).toString(16);
            Register register_f = mmu.getRegister(address);
            int newValue = new BigInteger(register_f.getBinaryValue() , 2).setBit(indexInString).intValue();
            mmu.setRegisterIntValue(address, newValue);
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
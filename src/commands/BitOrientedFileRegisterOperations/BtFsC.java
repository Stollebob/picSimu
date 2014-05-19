package commands.BitOrientedFileRegisterOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;
import register.Register;

import java.math.BigInteger;

/**
 * Created by Thor on 17.05.14.
 */
public class BtFsC extends BitOrientedFileRegisterOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 2;

    public BtFsC(String commandString)
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
            if(!new BigInteger(register_f.getBinaryValue() , 2).testBit(indexInString))
            {
                mmu.incPC();//PC hochzählen um nächste instruktion zu überspringen
                cycels++;
            }
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
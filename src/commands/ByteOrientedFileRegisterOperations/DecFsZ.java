package commands.ByteOrientedFileRegisterOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;
import register.Register;

import java.math.BigInteger;

/**
 * Created by Thor on 17.05.14.
 */
public class DecFsZ extends ByteOrientedFileRegisterOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 2;

    public DecFsZ(String commandString)
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

            BigInteger f = new BigInteger("0" + arguments[1], 2);
            Register register_f = mmu.getRegister(f.toString(16));
            int result = register_f.getIntValue() - 1;
            if(result == 0)
            {
                if (arguments[0].equals("0"))//d == 0 -> in W speichern
                {
                    mmu.getWorkingRegister().setIntValue(result);
                }
                else//d == 1 -> in F speichern
                {
                    register_f.setIntValue(result);
                }
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
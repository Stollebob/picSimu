package commands.ByteOrientedFileRegisterOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;
import register.Register;

import java.math.BigInteger;

/**
 * Created by Thor on 17.05.14.
 */
public class ComF extends ByteOrientedFileRegisterOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public ComF(String commandString)
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
            String address_f = f.toString(16);
            Register register_f = mmu.getRegister(address_f);
            int result = (~register_f.getIntValue() & 0xff);
            if(arguments[0].equals("0"))//d == 0 -> in W speichern
            {
                mmu.setWorkingRegister(result);
            }
            else//d == 1 -> in F speichern
            {
                mmu.setRegisterIntValue(address_f, result);
            }
            if(checkZ(result))
            {
                mmu.setZero();
            }
            else
            {
                mmu.resetZero();
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
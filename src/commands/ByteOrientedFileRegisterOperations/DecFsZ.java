package commands.ByteOrientedFileRegisterOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by Thor on 17.05.14.
 */
public class DecFsZ extends ByteOrientedFileRegisterOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycles = 1;

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

            BigInteger f = new BigInteger(arguments[1], 2);
            int result = mmu.getRegister(f.toString(16)).getIntValue() - 1;
            if (arguments[0].equals("0"))//d == 0 -> in W speichern
            {
                mmu.setWorkingRegister(result);
            }
            else//d == 1 -> in F speichern
            {
                mmu.getRegister(f.toString(16)).setIntValue(result);
            }
            if(result == 0)
            {
                cycles++;
                mmu.incPC();
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
        return cycles;
    }
}
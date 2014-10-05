package commands.ByteOrientedFileRegisterOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;
import register.Register;

import java.math.BigInteger;

/**
 * Created by Thor on 17.05.14.
 */
public class RRF extends ByteOrientedFileRegisterOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public RRF(String commandString)
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
            String address = f.toString(16);
            Register register_f = mmu.getRegister(address);
            String result = register_f.getBinaryValue();

            int length = result.length();
            String newCarry = result.substring(length - 1 , length);//Das neue Carry ist das 1. Bit vom alten Wert aus f!
            result = result.substring(0, length - 1);
            if(mmu.isCarry())
            {
                result = "1" + result;
                if(newCarry.equals("0"))//Wenn newCarry = 1 ist, dann bleibt das Carry gleich!
                {
                    mmu.resetCarry();
                }
            }
            else
            {
                result = "0" + result;
                if(newCarry.equals("1"))//Wenn newCarry = 0aa ist, dann bleibt das Carry gleich!
                {
                    mmu.setCarry();
                }
            }

            if (arguments[0].equals("0"))//d == 0 -> in W speichern
            {
                mmu.setWorkingRegister(result);
            }
            else//d == 1 -> in F speichern
            {
                mmu.setRegisterBinaryValue(address, result);
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
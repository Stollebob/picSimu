package commands.ByteOrientedFileRegisterOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by Thor on 17.05.14.
 */
public class AddWF extends ByteOrientedFileRegisterOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public AddWF(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        try
        {
            String[] arguments = decodeArguments(commandString);
            String f = new BigInteger("0" + arguments[1], 2).toString(16);
            while(f.length() <2)
            {
                f = "0" + f + "h";//TODO: Basti auf fehler hinweisen?
            }
            System.out.println("Register Hex addresse:" + f);
            int intValue_W = mmu.getWorkingRegister().getIntValue();
            System.out.println(f);
            int intValue_F = mmu.getRegister(f).getIntValue();

            int result = intValue_W + intValue_F;
            if(checkC(intValue_W, intValue_F, true))
            {
                //TODO: Event trigern, das CarryBit setzt
            }

            if(checkDC(intValue_W, intValue_F, true))
            {
                //TODO: Event trigern, das DigitCarryBit setzt
            }

            if(checkZ(result))
            {
                //TODO: Event trigern, das ZeroBit setzt
            }
            if(arguments[0].equals("0"))//d == 0 -> in W speichern
            {
                mmu.getWorkingRegister().setIntValue(result);
            }
            else//d == 1 -> in F speichern
            {
                mmu.getRegister(f).setIntValue(result);
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

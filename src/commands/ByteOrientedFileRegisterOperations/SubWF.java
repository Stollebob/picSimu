package commands.ByteOrientedFileRegisterOperations;

import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;
import register.Register;

import java.math.BigInteger;

/**
 * Created by Thor on 17.05.14.
 */
public class SubWF extends ByteOrientedFileRegisterOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public SubWF(String commandString)
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
            BigInteger value_f = new BigInteger(register_f.getBinaryValue(), 2);
            BigInteger value_w = new BigInteger(mmu.getWorkingRegister().getBinaryValue(), 2);
            value_w = value_w.subtract(new BigInteger("100000000",2));//two's complement
            BigInteger BigResult = value_f;
            BigResult.add(value_w);
            int result = BigResult.intValue();

            if (arguments[0].equals("0"))//d == 0 -> in W speichern
            {
                mmu.setWorkingRegister(result);
            }
            else//d == 1 -> in F speichern
            {
                register_f.setIntValue(result);
            }
            checkZ(result);
            if(checkC(result))
            {
                mmu.setCarry();
            }
            else
            {
                mmu.resetCarry();
            }
            if(checkDC(value_w.intValue(), value_f.intValue()))
            {
                mmu.setDigitCarry();
            }
            else
            {
                mmu.resetDigitCarry();
            }

            if(checkZ(result))
            {
                mmu.setZero();
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
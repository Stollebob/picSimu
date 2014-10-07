package commands.LiteralAndControlOperations;

import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import commands.Command;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by Thor on 18.05.14.
 */
public class SubLW extends LiteralAndControlOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public SubLW(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        String argument = decodeSingle8BitArgument(commandString);
        BigInteger intW = new BigInteger(mmu.getWorkingRegister().getBinaryValue(), 2);
        BigInteger intK = new BigInteger(argument, 2);
        intW = intW.not().add(BigInteger.ONE);//two's complement
        int result = intK.intValue();
        result += intW.intValue();
        if(result < 0)//negativ value -> add 256
        {
            result += 256;
        }
        mmu.setWorkingRegister(result);
        if(checkZ(result))
        {
            mmu.setZero();
        }
        else
        {
            mmu.resetZero();
        }
        if(checkDC(intK.intValue(), intW.intValue()))
        {
            mmu.setDigitCarry();
        }
        else
        {
            mmu.resetDigitCarry();
        }
        if(checkC(result))
        {
            mmu.setCarry();
        }
        else
        {
            mmu.resetCarry();
        }
        return mmu;
    }

    @Override
    public int getCycles()
    {
        return cycels;
    }
}
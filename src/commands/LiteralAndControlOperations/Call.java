package commands.LiteralAndControlOperations;

import commands.Command;
import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import java.math.BigInteger;

/**
 * Created by Thomas on 19.05.2014.
 */
public class Call extends LiteralAndControlOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycles = 2;

    public Call(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        try
        {
            String argument = decodeSingle11BitArgument(commandString);
            mmu.addPcToStack();
            BigInteger k = new BigInteger(argument, 2);

            String addToPCLATH = "0";

            if(k.testBit(8))
            {
                addToPCLATH = "1";
            }
            if(k.testBit(9))
            {
                addToPCLATH = "1" + addToPCLATH;
            }
            else
            {
                addToPCLATH = "0" +addToPCLATH;
            }

            mmu.setRegisterBinaryValue("0Ah", mmu.getRegister("0Ah").getBinaryValue().substring(0,6) + addToPCLATH);

            mmu.setPC(k.intValue());
            mmu.pushPCLATH();
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
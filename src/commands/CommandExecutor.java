package commands;

import exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;
import register.Register;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 16.05.2014.
 */
public class CommandExecutor
{
    private List<Command> commandList = new ArrayList<>();
    private MemoryManagementUnit mmu = new MemoryManagementUnit();
    private int cycles = 0;

    public CommandExecutor(List<Command> commandList)
    {
        this.commandList = commandList;
    }

    public void work() throws InvalidRegisterException
    {
        while(mmu.getPC() < commandList.size())
        {
            System.out.println(mmu.getPC());
            Command command = commandList.get(mmu.getPC());
            mmu.incPC();
            mmu = command.execute(mmu);
            cycles += command.getCycles();
            schmitTrigger();
        }
    }

    void schmitTrigger()
    {
        try
        {
            Register register = mmu.getRegister("06h");
            if(cycles%2 == 1)
            {
                register.setIntValue(new BigInteger(register.getBinaryValue(), 2).setBit(0).intValue());
            }
            else
            {
                register.setIntValue(new BigInteger(register.getBinaryValue(), 2).clearBit(0).intValue());
            }
        }
        catch (InvalidRegisterException e)
        {
            e.printStackTrace();
        }
    }
}

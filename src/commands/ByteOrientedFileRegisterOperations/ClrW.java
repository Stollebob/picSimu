package commands.ByteOrientedFileRegisterOperations;

import commands.Command;
import register.MemoryManagementUnit;

/**
 * Created by Thor on 17.05.14.
 */
public class ClrW extends ByteOrientedFileRegisterOperation implements Command
{
    private String commandString;//Arguments etc. as Binary String
    private int cycels = 1;

    public ClrW(String commandString)
    {
        this.commandString = commandString;
    }

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        mmu.setWorkingRegister(0);
        mmu.setZero();//Z setzen
        return mmu;
    }

    @Override
    public int getCycles()
    {
        return cycels;
    }
}
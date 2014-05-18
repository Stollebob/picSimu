package commands.ByteOrientedFileRegisterOperations;

import commands.Command;
import register.MemoryManagementUnit;

/**
 * Created by Thor on 17.05.14.
 */
public class NOP extends ByteOrientedFileRegisterOperation implements Command
{
    private int cycels = 1;

    @Override
    public MemoryManagementUnit execute(MemoryManagementUnit mmu)
    {
        return mmu;
    }

    @Override
    public int getCycles()
    {
        return cycels;
    }
}
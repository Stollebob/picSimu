package commands;

import register.MemoryManagementUnit;

/**
 * Created by Thomas on 16.05.2014.
 */
public interface Command
{
    public MemoryManagementUnit execute(MemoryManagementUnit mmu);
    public int getCycles();
}

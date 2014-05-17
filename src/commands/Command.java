package commands;

import register.MemoryManagementUnit;

/**
 * Created by Thomas on 16.05.2014.
 */
public abstract class Command {
    int pc;//Program counter

    protected Command(int pc)
    {
        this.pc = pc;
    }

    public int getPc()
    {
        return pc;
    }

    public void execute(MemoryManagementUnit mmu)
    {
        //To override!
    }
}

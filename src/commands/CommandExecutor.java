package commands;

import exceptions.InvalidRegisterException;
import gui.FrontEnd;
import register.MemoryManagementUnit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Thomas on 16.05.2014.
 */
public class CommandExecutor implements Runnable
{
    private List<Command> commandList;
    private MemoryManagementUnit mmu;
    private boolean interrupt = false;
    private boolean pause = true;
    private boolean stop = false;
    private Object lock = new Object();

    public CommandExecutor(MemoryManagementUnit mmu, List<Command> commandList)
    {
        this.mmu = mmu;
        this.commandList = commandList;
    }

    @Override
    public void run() {
        try
        {
            while(pause)
            {
                try
                {
                    synchronized (lock)
                    {
                        lock.wait();
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            while(!stop && mmu.getPC() < commandList.size())
            {
                next();
                while(pause)
                {
                    try
                    {
                        synchronized (lock)
                        {
                            lock.wait();
                        }
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (InvalidRegisterException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized void next() throws InvalidRegisterException
    {
        if(!stop && mmu.getPC() < commandList.size())
        {
            //Cycles in den Timer übertragen (+1 um Interrupt nicht beim Start zu beachten)
            mmu.setRegisterIntValue("01h", mmu.getCycles()+1);
            //Auf Interrupt prüfen und gegebenenfalls bearbeiten.
            do
            {
                interrupt = mmu.getInterrupt();
            }
            while (interrupt == true);
            System.out.println(mmu.getPC());
            Command command = commandList.get(mmu.getPC());
            mmu.incPC();
            mmu = command.execute(mmu);
            for(int i = 0; i < command.getCycles(); i++)
            {
                mmu.incrementCycles();
            }
        }
    }

    public void  stop()
    {
        synchronized (lock)
        {
            this.stop = true;
            pause = false;
            lock.notifyAll();
        }
    }

    public synchronized void pause()
    {
        pause = true;
    }

    public void start()
    {
        synchronized (lock)
        {
            pause = false;
            lock.notifyAll();
        }
    }

    public MemoryManagementUnit getMMU()
    {
        return mmu;
    }
}

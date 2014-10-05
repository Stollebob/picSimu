package controller;

import commands.Command;
import commands.CommandExecutor;
import controller.event.next.NextEvent;
import controller.event.next.NextListener;
import controller.event.open.OpenEvent;
import controller.event.open.OpenListener;
import controller.event.reset.ResetEvent;
import controller.event.reset.ResetListener;
import controller.event.start.StartEvent;
import controller.event.start.StartListener;
import controller.event.stop.StopEvent;
import controller.event.stop.StopListener;
import controller.event.tris.TrisChangeEvent;
import controller.event.tris.TrisChangeListener;
import decoder.CommandDecoder;
import exceptions.InvalidRegisterException;
import gui.FrontEnd;
import parser.LstParser;
import register.MemoryManagementUnit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 26.05.2014.
 */
public class Controller implements OpenListener , StartListener, StopListener, NextListener, ResetListener, TrisChangeListener
{
    private List<Command> commandList = new ArrayList<>();
    private FrontEnd view;
    private CommandExecutor executor;
    private MemoryManagementUnit mmu = new MemoryManagementUnit();
    private Thread t;

    public void init()
    {
        view = new FrontEnd();
        view.addFileOpenListener(this);
        view.addStartListener(this);
        view.addStopListener(this);
        view.addNextListener(this);
        view.addResetListener(this);
        view.addTrisChangeListener(this);
        mmu.addView(view);
    }

    @Override
    public void actionPerformed(OpenEvent event)
    {
        try
        {
            ArrayList<String> lineList = (ArrayList<String>) Files.readAllLines(Paths.get(event.getFile().toURI()), StandardCharsets.ISO_8859_1);
            LstParser parser = new LstParser(lineList);
            List<String> result = parser.parse();
            CommandDecoder decoder = new CommandDecoder();
            commandList = decoder.decode(result);
            executor = new CommandExecutor(mmu,commandList);
            view.clearEditor();
            for(String line: lineList)
            {
                view.addLineToEditor(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(StartEvent event)
    {
        if(t == null)
        {
            if(executor != null )
            {
                System.out.println("start");
                t = new Thread(executor);
                t.start();
                executor.start();
            }
        }
        else
        {
            executor.start();
        }
    }

    @Override
    public void actionPerformed(StopEvent event)
    {
        System.out.println("stop");
        if(t != null)
        {
            executor.pause();
        }
    }

    @Override
    public void actionPerformed(NextEvent event)
    {
        if(t != null)
        {
            try
            {
                executor.next();
            }
            catch (InvalidRegisterException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            if(executor != null )
            {
                try
                {
                    System.out.println("start");
                    t = new Thread(executor);
                    t.start();
                    executor.next();
                }
                catch (InvalidRegisterException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ResetEvent event)
    {
        System.out.println("reset");
        if(t != null)
        {
            executor.stop();
        }
        mmu = new MemoryManagementUnit();
        mmu.addView(view);
        executor = new CommandExecutor(mmu, commandList);
        while(t.isAlive())
        {
            view.update(mmu);
        }
        t = null;

    }

    @Override
    public void actionPerformed(TrisChangeEvent event)
    {
        if(mmu != null)
        {
            try
            {
                mmu.getRegister(event.getSourceHexAddres()).setBit(event.getIndexOfChange(), event.isNewValue());
            }
            catch (InvalidRegisterException e)
            {
                e.printStackTrace();
            }
        }
        view.update(mmu);
    }
}

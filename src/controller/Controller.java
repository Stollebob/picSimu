package controller;

import commands.Command;
import commands.CommandExecutor;
import controller.event.open.OpenEvent;
import controller.event.open.OpenListener;
import controller.event.start.StartEvent;
import controller.event.start.StartListener;
import decoder.CommandDecoder;
import exceptions.InvalidRegisterException;
import gui.FrontEnd;
import parser.LstParser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 26.05.2014.
 */
public class Controller implements OpenListener , StartListener
{
    private List<Command> commandList = new ArrayList<>();
    private FrontEnd view;
    CommandExecutor executor = new CommandExecutor();

    public void execute()
    {
        view = new FrontEnd();
        view.addFileOpenListener(this);
        view.addStartListener(this);
    }

    @Override
    public void actionPerformed(OpenEvent event)
    {
        LstParser parser = new LstParser(Paths.get(event.getFile().toURI()), StandardCharsets.ISO_8859_1);
        List<String> result = parser.parse();
        CommandDecoder decoder = new CommandDecoder();
        commandList = decoder.decode(result);
    }

    @Override
    public void actionPerformed(StartEvent event)
    {
        executor.setCommandList(commandList);
        try
        {
            executor.work(view);
        }
        catch (InvalidRegisterException e)
        {
            e.printStackTrace();
        }
    }
}

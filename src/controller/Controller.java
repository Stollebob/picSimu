package controller;

import commands.Command;
import commands.CommandExecutor;
import controller.event.open.OpenEvent;
import controller.event.open.OpenListener;
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
public class Controller implements OpenListener
{
    private List<String> result = new ArrayList<>();
    public void execute()
    {
        FrontEnd view = new FrontEnd();
        view.addFileOpenListener(this);


        CommandDecoder decoder = new CommandDecoder();
        List<Command> commandList = decoder.decode(result);
        CommandExecutor executor = new CommandExecutor(commandList);
        try
        {
            executor.work(view);
        }
        catch (InvalidRegisterException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(OpenEvent event)
    {
        LstParser parser = new LstParser(Paths.get(event.getFile().toURI()), StandardCharsets.ISO_8859_1);
        result = parser.parse();
    }
}

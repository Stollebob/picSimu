package main;

import commands.Command;
import commands.CommandExecutor;
import decoder.CommandDecoder;
import exceptions.InvalidRegisterException;
import gui.FrontEnd;
import parser.LstParser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Thomas on 07.04.2014.
 */
public class PicSimu {

    public static void main(String[] args)
    {
        FrontEnd view = new FrontEnd();
        //MainView view = new MainView("picSimu");
        LstParser parser = new LstParser(Paths.get("D:\\Binaerzaehler.LST"), StandardCharsets.ISO_8859_1);
        List<String> result = parser.parse();

        CommandDecoder decoder = new CommandDecoder();
        List<Command> commandList = decoder.decode(result);
        CommandExecutor executor = new CommandExecutor(commandList);
        try
        {
            executor.work();
        }
        catch (InvalidRegisterException e)
        {
            e.printStackTrace();
        }
    }
}

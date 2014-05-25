package main;

import commands.Command;
import commands.CommandExecutor;
import decoder.CommandDecoder;
import exceptions.InvalidRegisterException;
import gui.Main;
import gui.MainView;
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
        MainView view = new MainView("picSimu");
        LstParser parser = new LstParser(Paths.get("C:\\Users\\Thomas\\Desktop\\DHBW - Jahr2\\SimTest1.LST"), StandardCharsets.ISO_8859_1);
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

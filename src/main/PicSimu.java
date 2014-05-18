package main;

import commands.Command;
import commands.CommandExecutor;
import decoder.CommandDecoder;
import parser.LstParser;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Thomas on 07.04.2014.
 */
public class PicSimu {

    public static void main(String[] args)
    {
        LstParser parser = new LstParser(Paths.get("C:\\Users\\Thomas\\Desktop\\DHBW - Jahr2\\picsimu\\test\\Binaerzaehler.LST"), Charset.defaultCharset());
        List<String> result = parser.parse();

        CommandDecoder decoder = new CommandDecoder();
        List<Command> commandList = decoder.decode(result);
        CommandExecutor executor = new CommandExecutor(commandList);
        executor.work();
    }
}

package commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 16.05.2014.
 */
public class CommandExecutor
{
    List<Command> commandList = new ArrayList<>();

    public CommandExecutor(List<Command> commandList)
    {
        this.commandList = commandList;
    }

    public void work()
    {
        for(int pc = 0; pc < commandList.size(); pc++)
        {

        }
    }
}

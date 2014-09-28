package parser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 07.04.2014.
 */
public class LstParser
{
    private ArrayList<String> lineList = null;

    public LstParser(List<String> lineList)
    {
        this.lineList = (ArrayList<String>) lineList;
    }

    public List<String> parse()
    {
        ArrayList<String> result = new ArrayList<>();
        String line;
        for(int i = 0; i < lineList.size(); i++)
        {
            line = lineList.get(i).substring(5,9);
            if(!line.equals("    "))
            {
                result.add(line);
            }
        }

        return result;
    }

}

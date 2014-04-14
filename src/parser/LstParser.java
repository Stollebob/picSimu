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
    private Path lstFile;
    private Charset encoding;

    public LstParser(Path lstFile, Charset encoding)
    {
        this.lstFile = lstFile;
        this.encoding = encoding;
    }

    public List<String> parse()
    {
        ArrayList<String> lineList = null;
        ArrayList<String> result = new ArrayList<>();
        String line = "";
        try
        {
            lineList = (ArrayList<String>) Files.readAllLines(lstFile, encoding);
            for(int i = 0; i < lineList.size(); i++)
            {
                line = lineList.get(i).substring(5,9);
                if(!line.equals("    "))
                {
                    result.add(line);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

}

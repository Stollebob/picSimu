package main;

import decoder.CommandDecoder;
import parser.LstParser;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Thomas on 07.04.2014.
 */
public class PicSimu {

    public static void main(String[] args)
    {
        LstParser parser = new LstParser(Paths.get("C:\\Users\\Thomas\\Desktop\\DHBW - Jahr2\\picsimu\\test\\Binaerzaehler.LST"), Charset.defaultCharset());
        ArrayList<String> result = (ArrayList) parser.parse();

        CommandDecoder decoder = new CommandDecoder();
        decoder.decode(result);
        /*for(int i = 0 ; i < result.size() ; i++)
        {
            System.out.println(result.get(i));
        }*/
        }
        }

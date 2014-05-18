package commands.LiteralAndControlOperations;

import commands.Operation;

/**
 * Created by Thor on 17.05.14.
 */
public class LiteralAndControlOperation extends Operation
{
    public String decodeSingle8BitArgument(String argumentString)
    {
        String arg_k = argumentString.substring(8,16);
        return arg_k;

    }
}
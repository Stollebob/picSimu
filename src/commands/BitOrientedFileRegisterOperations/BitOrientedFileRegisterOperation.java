package commands.BitOrientedFileRegisterOperations;

import commands.Operation;

/**
 * Created by Thor on 17.05.14.
 */
public abstract class BitOrientedFileRegisterOperation extends Operation
{
    public String[] decodeArguments(String argumentString)
    {
        String arg_b = argumentString.substring(6,9);
        String arg_f = argumentString.substring(9,16);
        String[] result={arg_b, arg_f};
        return result;
    }
}

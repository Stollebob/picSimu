package commands.ByteOrientedFileRegisterOperations;

import commands.Operation;

/**
 * Created by Thor on 17.05.14.
 */
public abstract class ByteOrientedFileRegisterOperation extends Operation
{
    public String[] decodeArguments(String argumentString)
    {
        String arg_d = argumentString.substring(8,9);
        String arg_f = argumentString.substring(9,16);
        String[] result={arg_d, arg_f};
        return result;
    }
    public String decodeSingleArgument(String argumentString)
    {
        String arg_f = argumentString.substring(9,16);
        return arg_f;
    }
}

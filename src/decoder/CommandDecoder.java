package decoder;

import Exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Thomas on 07.04.2014.
 */
public class CommandDecoder
{
    private MemoryManagementUnit mmu = new MemoryManagementUnit();
    public void decode(List<String> lineList)
    {
        String[] arguments;
        String argument ="";
        for(int i = 0; i < lineList.size() ; i++)
        {
            String line = new BigInteger(lineList.get(i) , 16).toString(2);
            line = addLeadingZeros(line);
            if(line.startsWith("0000"))
            {
                boolean found = false;
                switch(line.substring(4,8))
                {
                    case "0111":
                        found = true;
                        try
                        {
                            addWF(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("ADDWF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "0101":
                        found = true;
                        try
                        {
                            andWF(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("ANDWF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1001":
                        found = true;
                        try
                        {
                            comf(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("COMF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "0011":
                        found = true;
                        try
                        {
                            decf(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("DECF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1011":
                        found = true;
                        try
                        {
                            decfsz(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("DECFSZ " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1010":
                        found = true;
                        try
                        {
                            incf(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("INCF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        arguments = argumentDFFFFFFF(line);
                        break;
                    case "1111":
                        found = true;
                        try
                        {
                            incfsz(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("INCFSZ " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        arguments = argumentDFFFFFFF(line);
                        break;
                    case "1000":
                        found = true;
                        System.out.println("MOVF");
                        arguments = argumentDFFFFFFF(line);
                        break;
                    case "1101":
                        found = true;
                        System.out.println("RLF");
                        arguments = argumentDFFFFFFF(line);
                        break;
                    case "1100":
                        found = true;
                        System.out.println("RRF");
                        arguments = argumentDFFFFFFF(line);
                        break;
                    case "0010":
                        found = true;
                        System.out.println("SUBWF");
                        arguments = argumentDFFFFFFF(line);
                        break;
                    case "1110":
                        found = true;
                        System.out.println("SWAPF");
                        arguments = argumentDFFFFFFF(line);
                        break;
                    case "0110":
                        found = true;
                        System.out.println("XORWF");
                        arguments = argumentDFFFFFFF(line);
                        break;
                }
                if(!found)
                {
                    switch(line.substring(4,9))
                    {
                        case "00011":
                            found = true;
                            System.out.println("CLRF");
                            argument = argumentFFFFFFF(line);
                            break;
                        case "00010":
                            found = true;
                            System.out.println("CLRW");//Argumentlos
                            break;
                        case "00001":
                            found = true;
                            System.out.println("MOVWF");
                            argument = argumentFFFFFFF(line);
                            break;
                    }
                    if(!found)
                    {
                        switch(line.substring(9,16))
                        {
                            case "1100100":
                                found = true;
                                System.out.println("CLRWDT");//Argumentlos
                                break;
                            case "0001001":
                                found = true;
                                System.out.println("RETFIE");//Argumentlos
                                break;
                            case "0001000":
                                found = true;
                                System.out.println("RETURN");//Argumentlos
                                break;
                            case "1100011":
                                found = true;
                                System.out.println("SLEEP");//Argumentlos
                                break;
                            default:
                                System.out.println("NOP");//Argumentlos
                                break;
                        }
                    }
                }
            }
            else if(line.startsWith("0001"))
            {
                switch(line.substring(4,6))
                {
                    case "00":
                        System.out.println("BCF");
                        arguments = argumentBBBFFFFFFF(line);
                        break;
                    case "01":
                        System.out.println("BSF");
                        arguments = argumentBBBFFFFFFF(line);
                        break;
                    case "10":
                        System.out.println("BTFSC");
                        arguments = argumentBBBFFFFFFF(line);
                        break;
                    case "11":
                        System.out.println("BTFSS");
                        arguments = argumentBBBFFFFFFF(line);
                        break;
                }
            }
            else if(line.startsWith("0010"))
            {
                if(line.substring(4,15).startsWith("1"))
                {
                    System.out.println("GOTO");
                    argument = argumentKKKKKKKKKKK(line);
                }
                else
                {
                    System.out.println("CALL");
                    argument = argumentKKKKKKKKKKK(line);
                }
            }
            else if(line.startsWith("0011"))
            {
                boolean found = false;
                switch(line.substring(4,6))
                {
                    case "00":
                        found = true;
//                        System.out.println("MOVLW");
                        argument = argumentKKKKKKKK(line);
                        movLW(argument);
                        break;
                    case "01":
                        found = true;
//                        System.out.println("RETLW");
                        argument = argumentKKKKKKKK(line);
                        retLW(argument);
                        break;
                }
                if(!found)
                {
                    switch(line.substring(4,7))
                    {
                        case "111":
                            found = true;
//                            System.out.println("ADDLW");
                            argument = argumentKKKKKKKK(line);
                            addLW(argument);
                            break;
                        case "110":
                            found = true;
//                            System.out.println("SUBLW");
                            argument = argumentKKKKKKKK(line);
                            subLW(argument);
                            break;
                    }
                    if(!found)
                    {
                        switch(line.substring(4,8))
                        {
                            case "1001":
//                                System.out.println("ANDLW");
                                argument = argumentKKKKKKKK(line);
                                andLW(argument);
                                break;
                            case "1000":
//                                System.out.println("IORLW");
                                argument = argumentKKKKKKKK(line);
                                xOrLW(argument);
                                break;
                            case "1010":
//                                System.out.println("XORLW");
                                argument = argumentKKKKKKKK(line);
                                xOrLW(argument);
                                break;
                        }
                    }
                }
            }
        }
    }


    private void movLW(String argument){
        int argumentK = new BigInteger(argument, 2).intValue();
        mmu.getWorkingRegister().setIntValue(argumentK);
    }

    private void retLW(String argument){
        /*
        TODO: retLW
        String binaryValue = mmu.getWorkingRegister().getBinaryValue();
        BigInteger argumentK = new BigInteger(argument, 2);
        int result = new BigInteger(mmu.getWorkingRegister().getBinaryValue(),2).add(argumentK).intValue();
        mmu.getWorkingRegister().setIntValue(result);
        checkZ();*/
    }

    private void addLW(String argument){
        String binaryValue = mmu.getWorkingRegister().getBinaryValue();
        BigInteger argumentK = new BigInteger(argument, 2);
        int result = new BigInteger(mmu.getWorkingRegister().getBinaryValue(),2).add(argumentK).intValue();
        mmu.getWorkingRegister().setIntValue(result);
        checkZ(result);
    }

    private void subLW(String argument){
        String binaryValue = mmu.getWorkingRegister().getBinaryValue();
        BigInteger argumentK = new BigInteger(argument, 2);
        int result = new BigInteger(mmu.getWorkingRegister().getBinaryValue(),2).subtract(argumentK).intValue();
        mmu.getWorkingRegister().setIntValue(result);
        checkZ(result);
    }

    private void andLW(String argument){
        String binaryValue = mmu.getWorkingRegister().getBinaryValue();
        BigInteger argumentK = new BigInteger(argument, 2);
        int result = new BigInteger(mmu.getWorkingRegister().getBinaryValue(),2).and(argumentK).intValue();
        mmu.getWorkingRegister().setIntValue(result);
        checkZ(result);
    }

    private void iOrLW(String argument){
        String binaryValue = mmu.getWorkingRegister().getBinaryValue();
        BigInteger argumentK = new BigInteger(argument, 2);
        int result = new BigInteger(mmu.getWorkingRegister().getBinaryValue(),2).or(argumentK).intValue();
        mmu.getWorkingRegister().setIntValue(result);
        checkZ(result);
    }

    private void xOrLW(String argument){
        String binaryValue = mmu.getWorkingRegister().getBinaryValue();
//        BigInteger argumentW = new BigInteger(binaryValue);
        BigInteger argumentK = new BigInteger(argument, 2);
        int result = new BigInteger(mmu.getWorkingRegister().getBinaryValue(),2).xor(argumentK).intValue();
//        System.out.println("XORLW "+ binaryValue + " ## " + argument + " ## Xor Value: " + result);
        mmu.getWorkingRegister().setIntValue(result);
//        System.out.println(mmu.getWorkingRegister().getBinaryValue());
        checkZ(result);
    }

    private void andWF(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        int result = new BigInteger(mmu.getWorkingRegister().getBinaryValue(), 2).and(f).intValue();

        if(arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setIntValue(result);
        }
        else//d == 1 -> in F speichern
        {
            mmu.getRegister(f.toString(16)).setIntValue(result);
        }
        checkZ(result);
    }

    private void addWF(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);
        String f = new BigInteger("0" + arguments[1], 2).toString(16);
        while(f.length() <2)
        {
            f = "0" + f + "h";
        }
        System.out.println("Register Hex addresse:" + f);
        int intValue_W = mmu.getWorkingRegister().getIntValue();
        System.out.println(f);
        int intValue_F = mmu.getRegister(f).getIntValue();
        int result = intValue_W + intValue_F;
        checkC(intValue_W, intValue_F, true);
        checkDC(intValue_W, intValue_F, true);
        checkZ(result);
        if(arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setIntValue(result);
        }
        else//d == 1 -> in F speichern
        {
            mmu.getRegister(f).setIntValue(result);
        }
    }

    private void comf(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        int result = new BigInteger(mmu.getWorkingRegister().getBinaryValue(), 2).not().intValue();

        if(arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setIntValue(result);
        }
        else//d == 1 -> in F speichern
        {
            mmu.getRegister(f.toString(16)).setIntValue(result);
        }
        checkZ(result);
    }

    private void decf(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        int result = f.intValue() - 1;

        if(arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setIntValue(result);
        }
        else//d == 1 -> in F speichern
        {
            mmu.getRegister(f.toString(16)).setIntValue(result);
        }
        checkZ(result);
    }

    private void decfsz(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        int result = f.intValue() - 1;
        if(result == 0)
        {
            if (arguments[0].equals("0"))//d == 0 -> in W speichern
            {
                mmu.getWorkingRegister().setIntValue(result);
            }
            else//d == 1 -> in F speichern
            {
                mmu.getRegister(f.toString(16)).setIntValue(result);
            }
            //nop
        }
        //nop
        return;
    }

    private void incf(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        int result = f.intValue() + 1;
        checkZ(result);
        if (arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setIntValue(result);
        }
        else//d == 1 -> in F speichern
        {
            mmu.getRegister(f.toString(16)).setIntValue(result);
        }
        //nop
        return;
    }

    private void incfsz(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        int result = f.intValue() + 1;
        if(result == 0)
        {
            if (arguments[0].equals("0"))//d == 0 -> in W speichern
            {
                mmu.getWorkingRegister().setIntValue(result);
            }
            else//d == 1 -> in F speichern
            {
                mmu.getRegister(f.toString(16)).setIntValue(result);
            }
            //nop
        }
        //nop
        return;
    }

    private String addLeadingZeros(String bin)
    {
        while(bin.length() < 16)
        {
            bin = "0" + bin;
        }
        return bin;
    }

    private String[] argumentDFFFFFFF(String line)
    {
        String arg_d = line.substring(8,9);
        String arg_f = line.substring(9,16);
        System.out.println(arg_d);
        System.out.println(arg_f);
        System.out.println( );
        String[] result={arg_d, arg_f};
        return result;
    }

    private String argumentFFFFFFF(String line)
    {
        String arg_f = line.substring(9,16);
        System.out.println(arg_f);
        System.out.println( );
        return arg_f;
    }

    private String[] argumentBBBFFFFFFF(String line)
    {
        String arg_b = line.substring(6,9);
        String arg_f = line.substring(9,16);
        System.out.println(arg_b);
        System.out.println(arg_f);
        System.out.println( );
        String[] result={arg_b, arg_f};
        return result;
    }

    private String argumentKKKKKKKKKKK(String line)
    {
        String arg_k = line.substring(5,16);
        System.out.println(arg_k);
        System.out.println( );
        return arg_k;
    }

    private String argumentKKKKKKKK(String line)
    {
        String arg_k = line.substring(8,16);
        System.out.println(arg_k);
        System.out.println( );
        return arg_k;
    }

    private void checkC(int valueOne, int valueTwo, boolean isAdd)
    {

    }

    private void checkDC(int valueOne, int valueTwo, boolean isAdd)
    {}

        
    private void checkZ(int result)
    {
        return;
    }
    /*private void checkZ(int valueOne, int valueTwo, boolean isAdd)
    {
        int result;
        int lowerBits;
        if(isAdd)
        {
            result = valueOne + valueTwo;
            lowerBits = result & 0x0F;
            if(result > 255)
            {
                //TODO: CarryBit setzen
                result -= 255;
                if(result == 0)
                {
                    //TODO: Zero bit setzen!
                }
            }
        }
        else
        {
            result = valueOne - valueTwo;
            if(result < 0)
            {
                //TODO: CarryBit setzen!
                result += 255;
            }
            if(result == 0)
            {
                //TODO: ZeroBit setzen!
            }
        }
    }*/
}

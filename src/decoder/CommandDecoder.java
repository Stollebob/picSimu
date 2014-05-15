package decoder;

import Exceptions.InvalidRegisterException;
import register.MemoryManagementUnit;
import register.Register;

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
                        break;
                    case "1000":
                        found = true;
                        try
                        {
                            movF(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("MOVF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1101":
                        found = true;
                        try
                        {
                            rlf(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("RLF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1100":
                        found = true;
                        try
                        {
                            rrf(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("RRF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "0010":
                        found = true;
                        try
                        {
                            subWF(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("SUBWF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1110":
                        found = true;
                        try
                        {
                            swapF(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("SWAPF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "0110":
                        found = true;
                        try
                        {
                            xOrWF(line);
                        }
                        catch (InvalidRegisterException e)
                        {
                            e.printStackTrace();
                        }
                        System.out.println("XORWF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                }
                if(!found)
                {
                    switch(line.substring(4,9))
                    {
                        case "00011":
                            found = true;
                            try
                            {
                                clrF(line);
                            }
                            catch (InvalidRegisterException e)
                            {
                                e.printStackTrace();
                            }
                            System.out.println("CLRF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                            break;
                        case "00010":
                            found = true;
                            clrW(line);
                            System.out.println("CLRW " + "... " + mmu.getWorkingRegister().getBinaryValue());//Argumentlos
                            break;
                        case "00001":
                            found = true;
                            try
                            {
                                movWF(line);
                            }
                            catch (InvalidRegisterException e)
                            {
                                e.printStackTrace();
                            }
                            System.out.println("MOVWF " + "... " + mmu.getWorkingRegister().getBinaryValue());
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
            f = "0" + f + "h";//TODO: Basti auf fehler hinweisen?
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
        String address_f = f.toString(16);
        Register register_f = mmu.getRegister(address_f);
        int result = new BigInteger(register_f.getBinaryValue(), 2).not().intValue();

        if(arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setIntValue(result);
        }
        else//d == 1 -> in F speichern
        {
            register_f.setIntValue(result);
        }
        checkZ(result);
    }

    private void decf(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        Register register_f = mmu.getRegister(f.toString(16));
        int result = register_f.getIntValue() - 1;

        if(arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setIntValue(result);
        }
        else//d == 1 -> in F speichern
        {
            register_f.setIntValue(result);
        }
        checkZ(result);
    }

    private void decfsz(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        Register register_f = mmu.getRegister(f.toString(16));
        int result = register_f.getIntValue() - 1;
        if(result == 0)
        {
            if (arguments[0].equals("0"))//d == 0 -> in W speichern
            {
                mmu.getWorkingRegister().setIntValue(result);
            }
            else//d == 1 -> in F speichern
            {
                register_f.setIntValue(result);
            }
            //nop
        }
        //nop
    }

    private void incf(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        Register register_f = mmu.getRegister(f.toString(16));
        int result = register_f.getIntValue() + 1;
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
    }

    private void incfsz(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        Register register_f = mmu.getRegister(f.toString(16));
        int result = register_f.getIntValue() + 1;
        if(result == 0)
        {
            if (arguments[0].equals("0"))//d == 0 -> in W speichern
            {
                mmu.getWorkingRegister().setIntValue(result);
            }
            else//d == 1 -> in F speichern
            {
                register_f.setIntValue(result);
            }
            //nop
        }
        //nop
    }

    private void movF(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        Register register_f = mmu.getRegister(f.toString(16));
        int result = register_f.getIntValue();
        if (arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setIntValue(result);
        }
        else//d == 1 -> in F speichern
        {
            register_f.setIntValue(result);
        }
        checkZ(result);
        //nop
    }

    private void rlf(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        Register register_f = mmu.getRegister(f.toString(16));
        String result = register_f.getBinaryValue();

        String newCarry = result.substring(0,1);//Das neue Carry ist das 8. Bit vom alten Wert aus f!
        result = result.substring(1);
        if(mmu.isCarry())
        {
            result += "1";
            if(newCarry.equals("0"))//Wenn newCarry = 1 ist, dann bleibt das Carry gleich!
            {
                mmu.resetCarry();
            }
        }
        else
        {
            result += "0";
            if(newCarry.equals("1"))//Wenn newCarry = 0aa ist, dann bleibt das Carry gleich!
            {
                mmu.setCarry();
            }
        }

        if (arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setBinaryValue(result);
        }
        else//d == 1 -> in F speichern
        {
            register_f.setBinaryValue(result);
        }
        //nop
    }

    private void rrf(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        Register register_f = mmu.getRegister(f.toString(16));
        String result = register_f.getBinaryValue();

        int length = result.length();
        String newCarry = result.substring(length - 1 , length);//Das neue Carry ist das 1. Bit vom alten Wert aus f!
        result = result.substring(0, length - 1);
        if(mmu.isCarry())
        {
            result = "1" + result;
            if(newCarry.equals("0"))//Wenn newCarry = 1 ist, dann bleibt das Carry gleich!
            {
                mmu.resetCarry();
            }
        }
        else
        {
            result = "0" + result;
            if(newCarry.equals("1"))//Wenn newCarry = 0aa ist, dann bleibt das Carry gleich!
            {
                mmu.setCarry();
            }
        }

        if (arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setBinaryValue(result);
        }
        else//d == 1 -> in F speichern
        {
            register_f.setBinaryValue(result);
        }
        //nop
    }

    private void subWF(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        Register register_f = mmu.getRegister(f.toString(16));
        int value_f = register_f.getIntValue();
        int value_w = mmu.getWorkingRegister().getIntValue();
        int result = value_f - value_w;

        if (arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setIntValue(result);
        }
        else//d == 1 -> in F speichern
        {
            register_f.setIntValue(result);
        }
        checkZ(result);
        checkC(value_f, value_w, false);
        checkDC(value_f, value_w, false);
        //nop
    }

    private void swapF(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);

        BigInteger f = new BigInteger("0" + arguments[1], 2);
        Register register_f = mmu.getRegister(f.toString(16));
        String value_f = register_f.getBinaryValue();
        String result = value_f.substring(4,8) + value_f.substring(0,4);

        if (arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setBinaryValue(result);
        }
        else//d == 1 -> in F speichern
        {
            register_f.setBinaryValue(result);
        }
        //nop
    }

    private void xOrWF(String line) throws InvalidRegisterException
    {
        String[] arguments;
        arguments = argumentDFFFFFFF(line);
        BigInteger f = new BigInteger("0" + arguments[1], 2);
        Register register_f = mmu.getRegister(f.toString(16));
        int result = new BigInteger(mmu.getWorkingRegister().getBinaryValue(),2).xor(new BigInteger(register_f.getBinaryValue() , 2)).intValue();

        if (arguments[0].equals("0"))//d == 0 -> in W speichern
        {
            mmu.getWorkingRegister().setIntValue(result);
        }
        else//d == 1 -> in F speichern
        {
            register_f.setIntValue(result);
        }
        mmu.getWorkingRegister().setIntValue(result);
        checkZ(result);
        //nop
    }

    private void clrF(String line) throws InvalidRegisterException
    {
        String argument;
        argument = argumentFFFFFFF(line);
        BigInteger f = new BigInteger("0" + argument, 2);
        mmu.getRegister(f.toString(16)).setIntValue(0);
        checkZ(0);//Z setzen
        //nop
    }

    private void clrW(String line)
    {
        mmu.getWorkingRegister().setIntValue(0);
        checkZ(0);//Z setzen
        //nop
    }

    private void movWF(String line) throws InvalidRegisterException
    {
        String argument;
        argument = argumentFFFFFFF(line);
        BigInteger f = new BigInteger("0" + argument, 2);
        mmu.getRegister(f.toString(16)).setIntValue(mmu.getWorkingRegister().getIntValue());
        //nop
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

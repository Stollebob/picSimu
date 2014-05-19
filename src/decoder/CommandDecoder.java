package decoder;

import commands.BitOrientedFileRegisterOperations.BcF;
import commands.BitOrientedFileRegisterOperations.BsF;
import commands.BitOrientedFileRegisterOperations.BtFsC;
import commands.BitOrientedFileRegisterOperations.BtFsS;
import commands.ByteOrientedFileRegisterOperations.*;
import commands.Command;
import commands.LiteralAndControlOperations.*;
import register.MemoryManagementUnit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 07.04.2014.
 */
public class CommandDecoder
{
    private MemoryManagementUnit mmu = new MemoryManagementUnit();
    public List<Command> decode(List<String> lineList)
    {
        List<Command> commandList = new ArrayList<>();
        String argument;
        for (String aLineList : lineList)
        {
            String line = new BigInteger(aLineList, 16).toString(2);
            line = addLeadingZeros(line);
            if (line.startsWith("0000")) {
                boolean found = false;
                switch (line.substring(4, 8)) {
                    case "0111":
                        found = true;
                        commandList.add(new AddWF(line));

                        System.out.println("ADDWF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "0101":
                        found = true;
                        commandList.add(new AndWF(line));

                        System.out.println("ANDWF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1001":
                        found = true;
                        commandList.add(new ComF(line));

                        System.out.println("COMF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "0011":
                        found = true;
                        commandList.add(new DecF(line));
                        System.out.println("DECF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1011":
                        found = true;
                        commandList.add(new DecFsZ(line));

                        System.out.println("DECFSZ " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1010":
                        found = true;
                        commandList.add(new IncF(line));

                        System.out.println("INCF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1111":
                        found = true;
                        commandList.add(new IncFsZ(line));

                        System.out.println("INCFSZ " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1000":
                        found = true;
                        commandList.add(new MovF(line));

                        System.out.println("MOVF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1101":
                        found = true;
                        commandList.add(new RLF(line));

                        System.out.println("RLF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1100":
                        found = true;
                        commandList.add(new RRF(line));

                        System.out.println("RRF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "0010":
                        found = true;
                        commandList.add(new SubWF(line));

                        System.out.println("SUBWF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "1110":
                        found = true;
                        commandList.add(new SwapF(line));

                        System.out.println("SWAPF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "0110":
                        found = true;
                        commandList.add(new XorWF(line));

                        System.out.println("XORWF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                }
                if (!found) {
                    switch (line.substring(4, 9)) {
                        case "00011":
                            found = true;
                            commandList.add(new ClrF(line));

                            System.out.println("CLRF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                            break;
                        case "00010":
                            found = true;
                            commandList.add(new ClrW(line));

                            System.out.println("CLRW " + "... " + mmu.getWorkingRegister().getBinaryValue());//Argumentlos
                            break;
                        case "00001":
                            found = true;
                            commandList.add(new MovWF(line));

                            System.out.println("MOVWF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                            break;
                    }
                    if (!found) {
                        switch (line.substring(9, 16)) {
                            case "1100100":
                                found = true;
                                //TODO: WatchDog?!
                                System.out.println("CLRWDT");//Argumentlos
                                break;
                            case "0001001":
                                found = true;
                                commandList.add(new RetFie(line));
                                //TODO: Interrupts?!
                                System.out.println("RETFIE");//Argumentlos
                                break;
                            case "0001000":
                                found = true;
                                //TODO: stack?!
                                System.out.println("RETURN");//Argumentlos
                                break;
                            case "1100011":
                                found = true;
                                //TODO: den check ich nicht xD
                                System.out.println("SLEEP");//Argumentlos
                                break;
                            default:
                                commandList.add(new NOP());

                                System.out.println("NOP " + "... " + mmu.getWorkingRegister().getBinaryValue());//Argumentlos
                                break;
                        }
                    }
                }
            } else if (line.startsWith("0001")) {
                switch (line.substring(4, 6)) {
                    case "00":
                        commandList.add(new BcF(line));

                        System.out.println("BCF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "01":
                        commandList.add(new BsF(line));

                        System.out.println("BSF " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "10":
                        commandList.add(new BtFsC(line));

                        System.out.println("BTFSC " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                    case "11":
                        commandList.add(new BtFsS(line));

                        System.out.println("BTFSS " + "... " + mmu.getWorkingRegister().getBinaryValue());
                        break;
                }
            } else if (line.startsWith("0010")) {
                if (line.substring(4, 15).startsWith("1")) {
                    System.out.println("GOTO");
                    argument = argumentKKKKKKKKKKK(line);
                } else
                {
                    commandList.add(new Call(line));
                    System.out.println("CALL");
                    argument = argumentKKKKKKKKKKK(line);
                }
            } else if (line.startsWith("0011")) {
                boolean found = false;
                switch (line.substring(4, 6)) {
                    case "00":
                        found = true;
                        commandList.add(new MovLW(line));

//                        System.out.println("MOVLW");
                        break;
                    case "01":
                        found = true;
//                        System.out.println("RETLW");
                        argument = argumentKKKKKKKK(line);
                        retLW(argument);//TODO: idee überlegen
                        break;
                }
                if (!found) {
                    switch (line.substring(4, 7)) {
                        case "111":
                            found = true;
                            commandList.add(new AddLW(line));

//                            System.out.println("ADDLW");
                            break;
                        case "110":
                            found = true;
                            commandList.add(new SubLW(line));

//                            System.out.println("SUBLW");
                            break;
                    }
                    if (!found) {
                        switch (line.substring(4, 8)) {
                            case "1001":
                                commandList.add(new AndLW(line));

//                              System.out.println("ANDLW");
                                break;
                            case "1000":
                                commandList.add(new IorLW(line));

//                              System.out.println("IORLW");
                                break;
                            case "1010":
                                commandList.add(new XorLW(line));

//                              System.out.println("XORLW");
                                break;
                        }
                    }
                }
            }
        }
        return commandList;
    }

    private void retLW(String argument){
        /*
        TODO: retLW
        String binaryValue = mmu.getWorkingRegister().getBinaryValue();
        BigInteger argumentK = new BigInteger(argument, 2);
        int result = new BigInteger(mmu.getWorkingRegister().getBinaryValue(),2).add(argumentK).intValue();
        mmu.getWorkingRegister().setIntValue(result);
        checkZ();*/
//        nop();
    }

    private String addLeadingZeros(String bin)
    {
        while(bin.length() < 16)
        {
            bin = "0" + bin;
        }
        return bin;
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
package decoder;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Thomas on 07.04.2014.
 */
public class CommandDecoder
{
    public void decode(List<String> lineList)
    {
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
                        System.out.println("ADDWF");//TODO: 2 Argumente dfff ffff
                    case "0101":
                        found = true;
                        System.out.println("ANDWF");//TODO: 2 Argumente dfff ffff
                        break;
                    case "1001":
                        found = true;
                        System.out.println("COMF");//TODO: 2 Argumente dfff ffff
                        break;
                    case "0011":
                        found = true;
                        System.out.println("DECF");//TODO: 2 Argumente dfff ffff
                        break;
                    case "1011":
                        found = true;
                        System.out.println("DECFSZ");//TODO: 2 Argumente dfff ffff
                        break;
                    case "1010":
                        found = true;
                        System.out.println("INCF");//TODO: 2 Argumente dfff ffff
                        break;
                    case "1111":
                        found = true;
                        System.out.println("INCFSZ");//TODO: 2 Argumente dfff ffff
                        break;
                    case "1000":
                        found = true;
                        System.out.println("MOVF");//TODO: 2 Argumente dfff ffff
                        break;
                    case "1101":
                        found = true;
                        System.out.println("RLF");//TODO: 2 Argumente dfff ffff
                        break;
                    case "1100":
                        found = true;
                        System.out.println("RRF");//TODO: 2 Argumente dfff ffff
                        break;
                    case "0010":
                        found = true;
                        System.out.println("SUBWF");//TODO: 2 Argumente dfff ffff
                        break;
                    case "1110":
                        found = true;
                        System.out.println("SWAPF");//TODO: 2 Argumente dfff ffff
                        break;
                    case "0110":
                        found = true;
                        System.out.println("XORWF");//TODO: 2 Argumente dfff ffff
                        break;
                }
                if(!found)
                {
                    switch(line.substring(4,9))
                    {
                        case "00011":
                            found = true;
                            System.out.println("CLRF");//TODO: 1 Argument fff ffff
                            break;
                        case "00010":
                            found = true;
                            System.out.println("CLRW");//Argumentlos
                            break;
                        case "00001":
                            found = true;
                            System.out.println("MOVWF");//TODO: 1 Argument fff ffff
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
                        System.out.println("BCF");//TODO: 2 Argumente bb bfff ffff
                        break;
                    case "01":
                        System.out.println("BSF");//TODO: 2 Argumente bb bfff ffff
                        break;
                    case "10":
                        System.out.println("BTFSC");//TODO: 2 Argumente bb bfff ffff
                        break;
                    case "11":
                        System.out.println("BTFSS");//TODO: 2 Argumente bb bfff ffff
                        break;
                }
            }
            else if(line.startsWith("0010"))
            {
                if(line.substring(4,15).startsWith("1"))
                {
                    System.out.println("GOTO");//TODO: 1 Argument kkk kkkk kkkk
                }
                else
                {
                    System.out.println("CALL");//TODO: 1 Argument kkk kkkk kkkk
                }
            }
            else if(line.startsWith("0011"))
            {
                boolean found = false;
                switch(line.substring(4,6))
                {
                    case "00":
                        found = true;
                        System.out.println("MOVLW");//TODO: 1 Argument xx kkkk kkkk
                        break;
                    case "01":
                        found = true;
                        System.out.println("RETLW");//TODO: 1 Argument xx kkkk kkkk
                        break;
                }
                if(!found)
                {
                    switch(line.substring(4,7))
                    {
                        case "111":
                            found = true;
                            System.out.println("ADDLW");//TODO: 1 Argument x kkkk kkkk
                            break;
                        case "110":
                            found = true;
                            System.out.println("SUBLW");//TODO: 1 Argument x kkkk kkkk
                            break;
                    }
                    if(!found)
                    {
                        switch(line.substring(4,8))
                        {
                            case "1001":
                                System.out.println(i+" ANDLW");//TODO: 1 Argument kkkk kkkk
                                break;
                            case "1000":
                                System.out.println("IORLW");//TODO: 1 Argument kkkk kkkk
                                break;
                            case "1010":
                                System.out.println("XORLW");//TODO: 1 Argument kkkk kkkk
                                break;
                        }
                    }
                }
            }
        }
    }

    private String addLeadingZeros(String bin)
    {
        while(bin.length() < 16)
        {
            bin = "0" + bin;
        }
        return bin;
    }
}

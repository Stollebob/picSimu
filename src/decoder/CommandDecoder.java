package decoder;

import javax.xml.bind.SchemaOutputResolver;
import java.math.BigDecimal;
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
                line = substringFourToFifteen(line);
                switch(line.substring(0,4))
                {
                    case "0111":
                        found = true;
                        System.out.println("ADDWF");
                        break;
                    case "0101":
                        found = true;
                        System.out.println("ANDWF");
                        break;
                    case "1001":
                        found = true;
                        System.out.println("COMF");
                        break;
                    case "0011":
                        found = true;
                        System.out.println("DECF");
                        break;
                    case "1011":
                        found = true;
                        System.out.println("DECFSZ");
                        break;
                    case "1010":
                        found = true;
                        System.out.println("INCF");
                        break;
                    case "1111":
                        found = true;
                        System.out.println("INCFSZ");
                        break;
                    case "1000":
                        found = true;
                        System.out.println("MOVF");
                        break;
                    case "1101":
                        found = true;
                        System.out.println("RLF");
                        break;
                    case "1100":
                        found = true;
                        System.out.println("RRF");
                        break;
                    case "0010":
                        found = true;
                        System.out.println("SUBWF");
                        break;
                    case "1110":
                        found = true;
                        System.out.println("SWAPF");
                        break;
                    case "0110":
                        found = true;
                        System.out.println("XORWF");
                        break;
                }
                if(!found)
                {
                    switch(line.substring(0,5))
                    {
                        case "00011":
                            found = true;
                            System.out.println("CLRF");
                            break;
                        case "00010":
                            found = true;
                            System.out.println("CLRW");
                            break;
                        case "00001":
                            found = true;
                            System.out.println("MOVWF");
                            break;
                    }
                    if(!found)
                    {
                        switch(line.substring(5,12))
                        {
                            case "1100100":
                                found = true;
                                System.out.println("CLRWDT");
                                break;
                            case "0001001":
                                found = true;
                                System.out.println("RETFIE");
                                break;
                            case "0001000":
                                found = true;
                                System.out.println("RETURN");
                                break;
                            case "1100011":
                                found = true;
                                System.out.println("SLEEP");
                                break;
                            default:
                                System.out.println("NOP");
                                break;
                        }
                    }
                }
            }
            else if(line.startsWith("0001"))
            {
                line = substringFourToFifteen(line);
                switch(line.substring(0,2))
                {
                    case "00":
                        System.out.println("BCF");
                        break;
                    case "01":
                        System.out.println("BSF");
                        break;
                    case "10":
                        System.out.println("BTFSC");
                        break;
                    case "11":
                        System.out.println("BTFSS");
                        break;
                }
            }
            else if(line.startsWith("0010"))
            {
                line = substringFourToFifteen(line);
                if(line.startsWith("1"))
                {
                    System.out.println("GOTO");
                }
                else
                {
                    System.out.println("CALL");
                }
            }
            else if(line.startsWith("0011"))
            {
                boolean found = false;
                line = substringFourToFifteen(line);
                switch(line.substring(0,2))
                {
                    case "00":
                        found = true;
                        System.out.println("MOVLW");
                        break;
                    case "01":
                        found = true;
                        System.out.println("RETLW");
                        break;
                }
                if(!found)
                {
                    switch(line.substring(0,3))
                    {
                        case "111":
                            found = true;
                            System.out.println("ADDLW");
                            break;
                        case "110":
                            found = true;
                            System.out.println("SUBLW");
                            break;
                    }
                    if(!found)
                    {
                        switch(line.substring(0,4))
                        {
                            case "1001":
                                found = true;
                                System.out.println("ANDLW");
                                break;
                            case "1000":
                                found = true;
                                System.out.println("IORLW");
                                break;
                            case "1010":
                                found = true;
                                System.out.println("XORLW");
                                break;
                        }
                    }
                }
            }
        }
    }

    private String substringFourToFifteen(String line)
    {
        return line.substring(4 , 15);
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

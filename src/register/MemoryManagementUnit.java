package register;

import exceptions.InvalidRegisterException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas on 28.04.2014.
 */
public class MemoryManagementUnit
{
    private Map<String, Register> bank_0 = new HashMap<>();
    private Map<String, Register> bank_1 = new HashMap<>();
    private Register w = new Register("WORKING REGISTER", "00000000");

    public MemoryManagementUnit()
    {
        initialize();
    }

    private void initialize()
    {
        Register pcl = new Register("PCL", "00000000");
        Register status = new Register("STATUS", "00011000");
        Register fsr = new Register("FSR", "00000000");
        Register indf = new Register("INDF", "00000000");
        Register pclath = new Register("PCLATH", "00000000");
        Register intcon = new Register("INTCON", "00000000");

        bank_0.put("00h", indf);
        bank_0.put("01h", new Register("TMR0", "00000000"));
        bank_0.put("02h", pcl);
        bank_0.put("03h", status);
        bank_0.put("04h", fsr);
        bank_0.put("05h", new Register("PORTA", "00000000"));
        bank_0.put("06h", new Register("PORTB", "00000000"));
        bank_0.put("08h", new Register("EEDATA", "00000000"));
        bank_0.put("09h", new Register("EEADR", "00000000"));
        bank_0.put("0Ah", pclath);
        bank_0.put("0Bh", intcon);

        Register gpr1 = new Register("GPR", "00000000");
        bank_0.put("0Ch", gpr1);
        bank_1.put("8Ch", gpr1);
        Register gpr2 = new Register("GPR", "00000000");
        bank_0.put("0Dh", gpr2);
        bank_1.put("8Dh", gpr2);
        Register gpr3 = new Register("GPR", "00000000");
        bank_0.put("0Fh", gpr3);
        bank_1.put("8Fh", gpr3);

        for(int first = 1; first <= 4; first++)
        {
            for(int second = 0; second <= 16; second++)
            {
                String firstString = "" + first;
                String secondString = convertIntToHexString(second);
                Register gpr = new Register("GPR", "00000000");
                bank_0.put(firstString + secondString + "h", gpr);
                firstString = convertIntToHexString(first + 8);
                bank_1.put(firstString + secondString + "h", gpr);
            }
        }

        bank_1.put("80h", indf);
        bank_1.put("81h", new Register("OPTION_REG", "11111111"));
        bank_1.put("82h", pcl);
        bank_1.put("83h", status);
        bank_1.put("84h", fsr);
        bank_1.put("85h",  new Register("TRISA", "00011111"));
        bank_1.put("86h",  new Register("TRISB", "11111111"));
        bank_1.put("88h",  new Register("EECON1", "00000000"));
        bank_1.put("89h",  new Register("EECON2", "00000000"));
        bank_1.put("0Ah", pclath);
        bank_1.put("0Bh", intcon);
    }

    private String convertIntToHexString(int second)
    {
        String result = "" + second;
        switch(second)
        {
            case 10:
                result = "A";
                break;
            case 11:
                result = "B";
                break;
            case 12:
                result = "C";
                break;
            case 13:
                result = "D";
                break;
            case 14:
                result = "E";
                break;
            case 15:
                result = "F";
                break;
            default:
        }
        return result;
    }

    public Register getRegisterFromBank_0(String address)
    {
        return bank_0.get(address);
    }

    public Register getRegisterFromBank_1(String address)
    {
        return bank_1.get(address);
    }

    public Register getRegister(String address) throws InvalidRegisterException {
        if(!address.contains("h"))
        {
            address += "h";
        }
        if(address.length() != 3)
        {
            address = "0" + address;
        }
        System.out.println(address);
        Register registerFromBank_0 = getRegisterFromBank_0(address);
        Register registerFromBank_1 = getRegisterFromBank_1(address);
        if (registerFromBank_0 != null)
        {
            return registerFromBank_0;
        }
        else if(registerFromBank_1 != null)
        {
            return registerFromBank_1;
        }
        InvalidRegisterException up = new InvalidRegisterException();
        throw up;
    }

    public Register getWorkingRegister()
    {
        return w;
    }

    public boolean isCarry()
    {
        return true;
    }

    public void setCarry()
    {

    }

    public void resetCarry()
    {

    }



}

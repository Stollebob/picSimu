package register;

import exceptions.InvalidRegisterException;
import gui.View;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by Thomas on 28.04.2014.
 */
public class MemoryManagementUnit
{
    private Map<String, Register> bank_0 = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Map<String, Register> bank_1 = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Register w = new Register("WORKING REGISTER", "00000000");
    private Stack<Integer> stack = new Stack<>();
    private List<View> viewList = new ArrayList<>();
    private int cycles = 0;

    public MemoryManagementUnit()
    {
        initialize();
    }

    private void initialize()
    {
        Register pcl = new Register("PCL", "00000000");
        Register status = new Register("STATUS", "00011000");
        Register fsr = new Register("FSR", "00000001");
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
        bank_0.put("0Eh", gpr3);
        bank_1.put("8Eh", gpr3);
        Register gpr4 = new Register("GPR", "00000000");
        bank_0.put("0Fh", gpr4);
        bank_1.put("8Fh", gpr4);

        for(int first = 1; first <= 4; first++)
        {
            for(int second = 0; second < 16; second++)
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

    private Register getRegisterFromBank_0(String address)
    {
        return bank_0.get(address);
    }

    private Register getRegisterFromBank_1(String address)
    {
        return bank_1.get(address);
    }

    public Register getRegister(String address) throws InvalidRegisterException {
        address.toUpperCase(Locale.GERMANY);
        int value;
        if(!address.contains("h"))
        {
            value = new BigInteger(address,16).intValue();
            address += "h";
        }
        else
        {
            value = new BigInteger(address.substring(0 , address.lastIndexOf("h")),16).intValue();
        }
        if(address.length() != 3)
        {
            address = "0" + address;
        }
        Register registerFromBank_0 = getRegisterFromBank_0(address);
        Register registerFromBank_1 = getRegisterFromBank_1(address);
        if (registerFromBank_0 != null)
        {
            if(address.equals("00h"))
            {
                Register fsr = getRegister("04h");
                String indirectAddress = new BigInteger(fsr.getBinaryValue(), 2).toString(16);
                return getRegister(indirectAddress);
                /*BigInteger bigInteger = new BigInteger(registerFromBank_0.getBinaryValue(), 2);
                String hexAddress = bigInteger.toString(16);
                int indirectAddress = bigInteger.intValue();
                return indirectAddress <= 0 ? registerFromBank_0 : getRegister(hexAddress);*/
            }
            return registerFromBank_0;
        }
        else if(registerFromBank_1 != null)
        {
            return registerFromBank_1;
        }
        if(value >= 0 && value <= 255)
        {
            return new Register("GPR", 0);
        }
        throw new InvalidRegisterException();
    }

    public Register getWorkingRegister()
    {
        return w;
    }

    public void setWorkingRegister(int newValue)
    {
        w.setIntValue(newValue);
        firePropertyChanged();
    }

    public void setWorkingRegister(String newValue)
    {
        w.setBinaryValue(newValue);
        firePropertyChanged();
    }

    public void setZero()
    {
        Register status = bank_0.get("03h");
        new BigInteger(status.getBinaryValue(), 2).setBit(2);
        firePropertyChanged();
    }

    public boolean isZero()
    {
        Register status = bank_0.get("03h");
        return new BigInteger(status.getBinaryValue(), 2).testBit(2);
    }

    public void resetZero()
    {
        Register status = bank_0.get("03h");
        new BigInteger(status.getBinaryValue(), 2).clearBit(1);
        firePropertyChanged();
    }

    public void setDigitCarry()
    {
        Register status = bank_0.get("03h");
        new BigInteger(status.getBinaryValue(), 2).setBit(1);
        firePropertyChanged();
    }

    public boolean isDigitCarry()
    {
        Register status = bank_0.get("03h");
        return new BigInteger(status.getBinaryValue(), 2).testBit(1);
    }

    public void resetDigitCarry()
    {
        Register status = bank_0.get("03h");
        new BigInteger(status.getBinaryValue(), 2).clearBit(2);
        firePropertyChanged();
    }

    public boolean isCarry()
    {
        Register status = bank_0.get("03h");
        return new BigInteger(status.getBinaryValue(), 2).testBit(0);
    }

    public void setCarry()
    {
        Register status = bank_0.get("03h");
        new BigInteger(status.getBinaryValue(), 2).setBit(0);
        firePropertyChanged();
    }

    public void resetCarry()
    {
        Register status = bank_0.get("03h");
        new BigInteger(status.getBinaryValue(), 2).clearBit(0);
    }

    //Gibt den PC zurück
    public int getPC() throws InvalidRegisterException
    {
        return (getRegister("0Ah").getIntValue() << 8) + getRegister("02h").getIntValue();
    }

    //Setzt den PC auf übergebenen Wert
    public void setPC(int pc) throws InvalidRegisterException {
        //Oberen 5 bit im PCLATH speichern
        getRegister("0Ah").setIntValue ((pc & 0x1F00) >> 0b1000); //0b0001 1111 0000 0000 >> 0b0000 0000 0001 1111
        //Unteren 8 bit im PCL speichern
        getRegister("02h").setIntValue(pc & 0x00FF);    //0b0000 0000 1111 1111
        firePropertyChanged();
    }

    //Prüft ob Interrupts TMR0 & RB0 gesetzt sind.
    public boolean getInterrupt() throws InvalidRegisterException {
        if ((getInterruptenabled() == 3) || (getInterruptenabled() == 1)){
            if ((getRegister("06h").getIntValue() & 0x01) == 0x01) {
                return true;
            }
        }

        if ((getInterruptenabled() == 3) || (getInterruptenabled() == 2)){
            if ((getRegister("01h").getIntValue() & 0x00) == 0) {
                return true;
            }
        }
        return false;
    }

    //Prüft ob Interrupt TMR0 & RB0 aktiviert ist.
    public int getInterruptenabled() throws InvalidRegisterException {
        if ((getRegister("0Bh").getIntValue() & 0x1F) == 0x1F) {
            return 3;
        } else if ((getRegister("0Bh").getIntValue() & 0x1F) == 0x10) {
            return 2;
        } else if ((getRegister("0Bh").getIntValue() & 0x1F) == 0x0F) {
            return 1;
        }
        return 0;
    }


    //Inkrementiert den PC
    public void incPC() throws InvalidRegisterException
    {
        setPC(getPC() + 1);
        firePropertyChanged();
    }

    public void addPcToStack() throws InvalidRegisterException
    {
        if(this.stack.size() < 8)
        {
            this.stack.push(new Integer(new BigInteger(""+ this.getPC(), 10).toString(16)));
        }

    }

    public void reloadPcFromStack() throws InvalidRegisterException
    {
        if(stack.size() > 0)
        {
            this.setPC(new BigInteger("" + this.stack.pop(), 16).intValue());
        }
    }

    public Stack<Integer> getStackData()
    {
        return this.stack;
    }

    public void addView(View view)
    {
        if(view != null)
        {
            viewList.add(view);
            firePropertyChanged();
        }
    }

    public void removeView(View view)
    {
        viewList.remove(view);
    }

    private void firePropertyChanged()
    {
        if(viewList.size() > 0)
        {
            for(View view:viewList)
            {
                view.update(this);
            }
        }
    }

    public int getCycles()
    {
        return cycles;
    }

    public void incrementCycles()
    {
        this.cycles++;
        firePropertyChanged();
    }
}

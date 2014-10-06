package register;

import java.math.BigInteger;

/**
 * Created by Thomas on 28.04.2014.
 */
public class Register
{
    private String name;
    private int intValue;

    public Register(String name, int intValue)
    {
        this.name = name;
        this.setIntValue(intValue);
    }

    public Register(String name, String binaryValue)
    {
        this.name = name;
        this.setBinaryValue(binaryValue);
    }

    public int getIntValue()
    {
        return intValue;
    }

    public String getBinaryValue()
    {
        String result = new BigInteger("" + intValue, 10).toString(2);
        while(result.length() < 8)
        {
            result = "0" + result;
        }
        return result;
    }

    public void setBinaryValue(String binaryValue)
    {
       setIntValue(new BigInteger(binaryValue, 2).intValue());
    }

    public void setIntValue(int intValue)
    {
        if(intValue < 0)
        {
            intValue = new BigInteger("" + intValue , 10).abs().intValue();
        }
        while(intValue > 255)
        {
            intValue -= 256;
        }
        this.intValue = intValue;
    }

    public String getName()
    {
        return name;
    }

    public void setBit(int index, boolean value)
    {
        BigInteger newRegisterValue = new BigInteger("" + intValue, 10);
        if(value)
        {
            newRegisterValue = newRegisterValue.setBit(index);
        }
        else
        {
            newRegisterValue = newRegisterValue.clearBit(index);
        }
        intValue = newRegisterValue.intValue();
    }
}

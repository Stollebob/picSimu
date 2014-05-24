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
        boolean negativ = false;
        String result = new BigInteger("" + intValue, 10).toString(2);
        if(result.charAt(0) == '-')
        {
            result = result.substring(1);
            negativ = true;
        }
        while(result.length() < 8)
        {
            result = "0" + result;
        }
        return negativ ? "-" + result : result;
    }

    public void setBinaryValue(String binaryValue)
    {
        this.intValue = new BigInteger(binaryValue, 2).intValue();
    }

    public void setIntValue(int intValue)
    {
        this.intValue = intValue;
    }

    public String getName()
    {
        return name;
    }
}

package commands;

/**
 * Created by Thor on 17.05.14.
 */
public abstract class Operation
{
    protected boolean checkZ(int result)
    {
        return result == 0; //gibt true zurück, wenn das ergebnis 0 ist
    }

    protected boolean checkDC(int intValue_W, int intValue_F)
    {
        int result = (intValue_W & 0x0F) + (intValue_F & 0x0F);//addiere jeweils die letzen 4 Bit
        return result != (result & 0x0F);//prüfen, ob das 5. Bit 1 ist
    }

    protected boolean checkC(int result)
    {
        return result != (result & 0xFF);
    }
}

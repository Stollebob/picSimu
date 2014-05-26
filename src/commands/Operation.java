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

    protected boolean checkDC(int intValue_W, int intValue_F, boolean b)
    {
        if (((intValue_W & 0x0F) + (intValue_F & 0x0F) < 16) || (intValue_W + intValue_F  > 0))
        {
            return true;
        }
        return false;
        //TODO:gibt true zurück, wenn es ein DC gab
    }

    protected boolean checkC(int intValue_W, int intValue_F, boolean b)
    {
        return true; //TODO:gibt true zurück, wenn es ein Carry gab
    }
}

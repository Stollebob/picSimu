package controller.event.tris;

/**
 * Created by Thor on 05.10.14.
 */
public class TrisChangeEvent
{
    String sourceHexAddres;
    int indexOfChange;
    boolean newValue;

    public TrisChangeEvent(String sourceHexAddres, int indexOfChange, boolean newValue)
    {
        this.sourceHexAddres = sourceHexAddres;
        this.indexOfChange = indexOfChange;
        this.newValue = newValue;
    }

    public String getSourceHexAddres()
    {
        return sourceHexAddres;
    }

    public int getIndexOfChange()
    {
        return indexOfChange;
    }

    public boolean isNewValue()
    {
        return newValue;
    }
}

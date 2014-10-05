package controller.event.frequency;

/**
 * Created by Thor on 05.10.14.
 */
public class FrequencyChangeEvent
{
    long newDelay;

    public FrequencyChangeEvent(long newDelay)
    {
        this.newDelay = newDelay;
    }

    public long getDelay()
    {
        return newDelay;
    }
}

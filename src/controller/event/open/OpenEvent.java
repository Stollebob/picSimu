package controller.event.open;

import java.io.File;

/**
 * Created by Thomas on 26.05.2014.
 */
public class OpenEvent
{
    File toOpen;

    public OpenEvent(File toOpen)
    {
        this.toOpen = toOpen;
    }

    public File getFile()
    {
        return toOpen;
    }
}

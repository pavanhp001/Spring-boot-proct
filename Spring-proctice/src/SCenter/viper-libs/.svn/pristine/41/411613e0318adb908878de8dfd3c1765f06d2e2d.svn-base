package com.A.util;

import java.io.Serializable;

/**
 * 
 * @author ebaugh
 * 
 */
public class StopWatch implements Serializable
{
    private static final long serialVersionUID = 5553341885L;
    
    private static final int MILLI_PER_SEC = 1000;
    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;

    /**
     * Method to start stop watch counter.
     */
    public void start()
    {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    /**
     * Method to stop stop watch counter.
     */
    public void stop()
    {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    /**
     * Method to get elapsed time in seconds.
     * 
     * @return elapsed time.
     */
    public int getElapsedTime()
    {
        long elapsed;
        if ( running )
        {
            elapsed = ( ( System.currentTimeMillis() - startTime ) / MILLI_PER_SEC );
        }
        else
        {
            elapsed = ( ( stopTime - startTime ) / MILLI_PER_SEC );
        }
        
        return Long.valueOf( elapsed ).intValue();
    }

    /**
     * Method to get elapsed time in milliseconds.
     * 
     * @return elapsed time.
     */
    public long getElapsedTimeMillis()
    {
        long elapsed;
        if ( running )
        {
            elapsed = ( System.currentTimeMillis() - startTime );
        }
        else
        {
            elapsed = ( stopTime - startTime );
        }
        return elapsed;
    }

}

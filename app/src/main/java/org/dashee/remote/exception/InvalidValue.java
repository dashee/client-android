package org.dashee.remote.exception;

/**
 * Exception class indicating Invalid Values
 *
 * @author Shahmir Javaid
 */
@SuppressWarnings("serial")
public class InvalidValue 
    extends RuntimeException
{
    /**
     * Call extended version
     *
     * @param message
     */
    public InvalidValue(String message)
    {
        super(message);
    }

    /**
     * Call extended version
     *
     * @param message
     * @param throwable
     */
    public InvalidValue(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}

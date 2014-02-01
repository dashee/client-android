package org.dashee.remote.model;

import org.dashee.remote.exception.OutOfRange;

/**
 * This class holds all the values used for all vehicles. A nice way to 
 * encapsulate the properties of pitch, roll, yaw and throttle. With an
 * added functionality to provide Trim, Min, Max and Invert behavior to
 * all of the flight controls.
 */
public abstract class Vehicle
{
    /**
     * Main flight control sets.
     */
    private int pitch = 0;
    private int roll = 0;
    private int yaw = 0;
    private int throttle = 0;
 
    /**
     * The trim values for the vehicle
     */    
    private int pitchTrim = 0;
    private int rollTrim = 0;
    private int yawTrim = 0;
    private int throttleTrim = 0;

    /**
     * The max value for flight controls
     */
    private int pitchMin = 0;
    private int rollMin = 0;
    private int yawMin = 0;
    private int throttleMin = 0;

    /**
     * The max value for flight controls
     */
    private int pitchMax = 255;
    private int rollMax = 255;
    private int yawMax = 255;
    private int throttleMax = 255;

    /**
     * Flag to determine the inversion of the flight controls.
     */
    private boolean pitchInverted = false;
    private boolean rollInverted = false;
    private boolean yawInverted = false;
    private boolean throttleInverted = false;
 
    /**
     * Set the value of pitch.
     *
     * @param value The pitch value to set
     *
     * @throws exception.OutOfRange
     */ 
    public void setPitch(int pitch)
    {
        if (pitch < 0 || pitch > 255)
            throw new OutOfRange("Invalid range of Pitch");

        this.pitch = pitch;
    }

    /**
     * Return the value of Pitch
     *
     * @return the value of pitch
     */
    public int getPitch()
    {
        int trimmed = this.pitch + this.pitchTrim;

        // Return the min/max values if the actual value is out of range
        if (trimmed < this.pitchMin)
           trimmed = this.pitchMin;
        else if (trimmed > this.pitchMax)
            trimmed = this.pitchMax;

        // Reverse the value when in invert
        if (pitchInverted)
            return 255 - trimmed;
        
        return trimmed;
    }

    /**
     * Set the pitch trim value.
     *
     * @param trim The value to set
     *
     * @throws OutOfRange if the trim value is not valid
     */
    public void setPitchTrim(int trim)
    {
        if (trim < -128 || trim > 128)
            throw new OutOfRange("Invalid range value for Pitch trim");

        this.pitchTrim = trim;
    }

    /**
     * Set the invert flag for pitch.
     *
     * @param inverted The value to set
     */
    public void setPitchInverted(boolean inverted)
    {
        this.pitchInverted = inverted;
    }

    /**
     * Get the pitch invert value.
     *
     * @return the value of the inverted flag 
     */
    public boolean getPitchInverted()
    {
        return this.pitchInverted;
    }

    /**
     * Set the min value of pitch.
     *
     * @param min The value to set it to
     * 
     * @throws OutOfRange if the min value is not valid
     */
    public void setPitchMin(int min)
    {
        if (min >= this.pitchMax)
            throw new OutOfRange("Min value must be less than max");

        this.pitchMin = min;
    }

    /**
     * Set the max value of pitch.
     *
     * @param max The value to set it to
     * 
     * @throws OutOfRange if the max value is not valid
     */
    public void setPitchMax(int max)
    {
        if (max <= this.pitchMin)
            throw new OutOfRange("Max value must be less than min");

        this.pitchMax = max;
    }

    /**
     * Set the value of roll.
     *
     * @param value The roll value to set
     *
     * @throws OutOfRange
     */ 
    public void setRoll(int roll)
    {
        if (roll < 0 || roll > 255)
            throw new OutOfRange("Invalid range of Roll");

        this.roll = roll;
    }

    /**
     * Return the value of Roll
     *
     * @return the value of roll
     */
    public int getRoll()
    {
        int trimmed = this.roll + this.rollTrim;

        // Return the min/max values if the actual value is out of range
        if (trimmed < this.rollMin)
           trimmed = this.rollMin;
        else if (trimmed > this.rollMax)
            trimmed = this.rollMax;

        // Reverse the value when in invert
        if (rollInverted)
            return 255 - trimmed;
        
        return trimmed;
    }

    /**
     * Set the roll trim value.
     *
     * @param trim The value to set
     *
     * @throws OutOfRange if the trim value is not valid
     */
    public void setRollTrim(int trim)
    {
        if (trim < -128 || trim > 128)
            throw new OutOfRange("Invalid range value for Roll trim");

        this.rollTrim = trim;
    }

    /**
     * Set the invert flag for roll.
     *
     * @param inverted The value to set
     */
    public void setRollInverted(boolean inverted)
    {
        this.rollInverted = inverted;
    }

    /**
     * Get the roll invert value.
     *
     * @return the value of the inverted flag 
     */
    public boolean getRollInverted()
    {
        return this.rollInverted;
    }

    /**
     * Set the min value of roll.
     *
     * @param min The value to set it to
     * 
     * @throws OutOfRange if the min value is not valid
     */
    public void setRollMin(int min)
    {
        if (min >= this.rollMax)
            throw new OutOfRange("Min value must be less than max");

        this.rollMin = min;
    }

    /**
     * Set the max value of roll.
     *
     * @param max The value to set it to
     * 
     * @throws OutOfRange if the max value is not valid
     */
    public void setRollMax(int max)
    {
        if (max <= this.rollMin)
            throw new OutOfRange("Max value must be less than min");

        this.rollMax = max;
    }
 
    /**
     * Set the value of yaw.
     *
     * @param value The pitch value to set
     *
     * @throws exception.OutOfRange
     */ 
    public void setYaw(int yaw)
    {
        if (yaw < 0 || yaw > 255)
            throw new OutOfRange("Invalid range of Yaw");

        this.yaw = yaw;
    }

    /**
     * Return the value of Yaw
     *
     * @return the value of yaw
     */
    public int getYaw()
    {
        int trimmed = this.yaw + this.yawTrim;

        // Return the min/max values if the actual value is out of range
        if (trimmed < this.yawMin)
            trimmed = this.yawMin;
        else if (trimmed > this.yawMax)
            trimmed = this.yawMax;
        
        // Reverse the value when in invert
        if (yawInverted)
            return 255 - trimmed;
        
        return trimmed;
    }

    /**
     * Set the yaw trim value.
     *
     * @param trim The value to set
     *
     * @throws OutOfRange if the trim value is not valid
     */
    public void setYawTrim(int trim)
    {
        if (trim < -128 || trim > 128)
            throw new OutOfRange("Invalid range value for Yaw trim");

        this.yawTrim = trim;
    }
    
    /**
     * Set the invert flag for yaw.
     *
     * @param inverted The value to set
     */
    public void setYawInverted(boolean inverted)
    {
        this.yawInverted = inverted;
    }

    /**
     * Get the yaw invert value.
     *
     * @return the value of the inverted flag 
     */
    public boolean getYawInverted()
    {
        return this.yawInverted;
    }

    /**
     * Set the min value of yaw.
     *
     * @param min The value to set it to
     *
     * @throws OutOfRange if the min value is not valid
     */
    public void setYawMin(int min)
    {
        if (min >= this.yawMax)
            throw new OutOfRange("Min value must be less than max");

        this.yawMin = min;
    }

    /**
     * Set the max value of yaw.
     *
     * @param max The value to set it to
     *
     * @throws OutOfRange if the max value is not valid
     */
    public void setYawMax(int max)
    {
        if (max <= this.yawMin)
            throw new OutOfRange("Max value must be less than min");

        this.yawMax = max;
    }
 
    /**
     * Set the value of throttle.
     *
     * @param value The throttle value to set
     *
     * @throws exception.OutOfRange
     */ 
    public void setThrottle(int throttle)
    {
        if (throttle < 0 || throttle > 255)
            throw new OutOfRange("Invalid range of Throttle");

        this.throttle = throttle;
    }

    /**
     * Return the value of Throttle
     *
     * @return the value of throttle
     */
    public int getThrottle()
    {
        int trimmed = this.throttle + this.throttleTrim;

        // Return the min/max values if the actual value is out of range
        if (trimmed < this.throttleMin)
            trimmed = this.throttleMin;
        else if (trimmed > this.throttleMax)
            trimmed = this.throttleMax;

        // Reverse the value when in invert
        if (throttleInverted)
            return 255 - trimmed;
        
        return trimmed;
    }

    /**
     * Set the throttle trim value.
     *
     * @param trim The value to set
     *
     * @throws OutOfRange if the trim value is not valid
     */
    public void setThrottleTrim(int trim)
    {
        if (trim < -128 || trim > 128)
            throw new OutOfRange("Invalid range value for Throttle trim");

        this.throttleTrim = trim;
    }
    
    /**
     * Set the invert flag for throttle.
     *
     * @param inverted The value to set
     */
    public void setThrottleInverted(boolean inverted)
    {
        this.throttleInverted = inverted;
    }

    /**
     * Get the throttle invert value.
     *
     * @return the value of the inverted flag 
     */
    public boolean getThrottleInverted()
    {
        return this.throttleInverted;
    }

    /**
     * Set the min value of throttle.
     *
     * @param min The value to set it to
     *
     * @throws OutOfRange if the min value is not valid
     */
    public void setThrottleMin(int min)
    {
        if (min >= this.throttleMax)
            throw new OutOfRange("Min value must be less than max");

        this.throttleMin = min;
    }

    /**
     * Set the max value of throttle.
     *
     * @param max The value to set it to
     *
     * @throws OutOfRange if the max value is not valid
     */
    public void setThrottleMax(int max)
    {
        if (max <= this.throttleMin)
            throw new OutOfRange("Max value must be less than min");

        this.throttleMax = max;
    }
}
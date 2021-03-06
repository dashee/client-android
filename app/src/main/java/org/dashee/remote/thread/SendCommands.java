package org.dashee.remote.thread;

import java.net.*;

import org.dashee.remote.fragment.Hud;
import org.dashee.remote.model.Config;
import org.dashee.remote.model.Vehicle;

/**
 * Thread to communicate to the server.
 * This will send position when the position is changed and 
 * communicate with the server when the position is the same, so
 * the server can know that we are still alive
 *
 * @author David Buttar
 * @author Shahmir Javaid
 */
public class SendCommands extends Thread 
{
    /**
     * Send commands every nth millisecond
     */
    static final int TICK_PER_BYTE = 30;

    /**
     * DataGram object to send commands over UDP
     */
    private DatagramSocket sockHandler;

    /**
     * Time when last value was sent.
     */
    private long resetTime = 0;
    private long currentTime = 0;

    private boolean pause = false;
    private Object lockPause = new Object();
    
    /**
     * The config which holds configuration of our application. We use the 
     * IP address and the port Number
     */
    private Config config;
    
    /**
     * Current vehicle in use.
     */
    private Vehicle vehicle;

    private Hud hud;

    /**
     * Initiate our thread. Set the variables from the parameters, and set our 
     * IP Address object. Also create a new instance of socket
     *
     * @param config Set our pointer to the config var
     * @param vehicle  Set our variable reference to the vehicle var
     */
    public SendCommands(Config config, Vehicle vehicle, Hud hud)
    {
        super();
        try
        {
            this.vehicle = vehicle;
            this.config = config;
            this.hud = hud;
            this.sockHandler = new DatagramSocket();
            this.sockHandler.setSoTimeout(500);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    }

    /**
     * Set the position. If a position presented is different than
     * the previous position, notify our server over UDP. 
     */
    public void run()
    {   
        while(true)
        {
            try
            {
                // Skip if we haven't ticked by sleeping and continuing
                if (!haveWeTicked())
                {
                    Thread.sleep(TICK_PER_BYTE);
                    continue;
                }

                // Create a new set of bytes
                byte[] ar = new byte[5];
                ar[0] = 0;
                ar[1] = 0;
                ar[2] = 0;
                ar[3] = (byte)vehicle.getRoll();
                ar[4] = (byte)vehicle.getThrottle();

                // Send the commands to th server
                this.sendCommandBytes(ar);
                //this.sendPing();

                synchronized (lockPause)
                {
                    while (pause)
                    {
                        try
                        {
                            lockPause.wait();
                        }
                        catch(InterruptedException e)
                        {
                        }
                    }
                }
            }
            catch (InterruptedException e) 
            {
                android.util.Log.e("dashee", "sendCommand threw an exception");
                android.util.Log.e("dashee", "sendCommand " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Variable reprsenting weathre or not we have ticked. If we have, reset the
     * times and return true
     *
     * @return boolean representing weather we have ticked or not
     */
    private boolean haveWeTicked()    
    {
        // Only send a packet every Nth second
        this.currentTime = System.currentTimeMillis();
        if(this.currentTime - this.resetTime < TICK_PER_BYTE)
            return false;
        
        this.resetTime = System.currentTimeMillis();
        return true;
    }

    /**
     * Send a ping to the server and wait for a response.
     */
    private void sendPing(){
        try
        {
            byte[] ar = new byte[1];
            ar[0] = 1;
            DatagramPacket packet = new DatagramPacket(
                    ar,
                    ar.length,
                    this.config.getIp(),
                    this.config.getPort()
            );

            this.sockHandler.send(packet);

            try {
                byte[] lMsg = new byte[1000];
                DatagramPacket dp = new DatagramPacket(lMsg, lMsg.length);
                this.sockHandler.receive(dp);
                String stringData = new String(lMsg, 0, dp.getLength());
                android.util.Log.i("dashee", "Received: " + stringData);
                this.hud.setConnection(Hud.CONNECTION_STATUS.CONNECTED);
            } catch (SocketTimeoutException e) {
                // resend
                android.util.Log.e("dashee", "Received Timeout");
                this.hud.setConnection(Hud.CONNECTION_STATUS.FAIL);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Send the bytes to the server. Each packet is sent after Nth milisecond 
     * controlled by our static TICK_PER_BYTE
     *
     * @param command the set of commands to send
     */
    private void sendCommandBytes(byte[] command)
    {
        try
        {
            DatagramPacket packet = new DatagramPacket(
                command,
                command.length,
                this.config.getIp(),
                this.config.getPort()
            );

            this.sockHandler.send(packet);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Make sure when the phone is in background we stop sending the data.
     */
    public void onPause()
    {
        synchronized (lockPause)
        {
            this.pause = true;
        }
    }

    public void onResume()
    {
        synchronized (lockPause)
        {
            this.pause = false;
            lockPause.notifyAll();
        }
    }
}

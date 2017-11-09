package com.redmancometh.orgbot.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class TorUtil
{
    public static void torNewIP(int portUsed)
    {
        Socket socket = null;
        Scanner in = null;
        OutputStream out = null;
        try
        {
            System.out.println("Requesting new IP for port:" + portUsed + " on port: " + (portUsed + 1));
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", (portUsed + 1)));
            out = socket.getOutputStream();
            in = new Scanner(socket.getInputStream());
            out.write(new String("AUTHENTICATE\r\n").getBytes());
            System.out.println(in.nextLine());
            out.flush();
            out.write(new String("SIGNAL NEWNYM\r\n").getBytes());
            System.out.println(in.nextLine());
            socket.close();
        }
        catch (IOException e)
        {
            torNewIP(portUsed);
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                in.close();
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (socket != null && !socket.isClosed())
            {
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void torNewIP(String address, int portUsed)
    {
        Socket socket = null;
        Scanner in = null;
        OutputStream out = null;
        try
        {
            System.out.println("Requesting new IP for port:" + portUsed + " on port: " + (portUsed + 1));
            socket = new Socket();
            socket.connect(new InetSocketAddress(address, (portUsed + 1)));
            out = socket.getOutputStream();
            in = new Scanner(socket.getInputStream());
            out.write(new String("AUTHENTICATE\r\n").getBytes());
            System.out.println(in.nextLine());
            out.flush();
            out.write(new String("SIGNAL NEWNYM\r\n").getBytes());
            System.out.println(in.nextLine());
            socket.close();
        }
        catch (IOException e)
        {
            torNewIP(portUsed);
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                in.close();
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (socket != null && !socket.isClosed())
            {
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}

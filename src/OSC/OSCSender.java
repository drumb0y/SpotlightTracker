package OSC;

import com.illposed.osc.*;
import com.illposed.osc.transport.udp.OSCPortOut;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class OSCSender {

    public static void main(String[] args) throws UnknownHostException, IOException, OSCSerializeException, InterruptedException {
//169.254.249.205 - wired
        //192.168.0.113 - wifi
        Scanner stdin = new Scanner(System.in);
        System.out.println("Print Receivers ip address");
        int count = 1;

        OSCSender sender = new OSCSender(stdin.nextLine(), null);

        while (true) {
            Thread.sleep(100);
            sender.sendMessage(new OSCMessage("/cue/" + count++));
        }
        }

    String ipaddress;
    OSCPortOut senderPort;

    public String getIpaddress() {
        return ipaddress;
    }

    Queue<OSCMessage> messageQueue = new LinkedList<>();

    public Queue<OSCMessage> getMessageQueue() {
        return messageQueue;
    }

    public void setIpAddress(String ip, int port) {
        ipaddress = ip;

        if (ipaddress == "") ipaddress = "192.168.0.113";

        try {
            senderPort = new OSCPortOut(InetAddress.getByName(ipaddress), port);
        }
        catch (Exception e) {
            System.err.println("THIS DID NOT WORK");
            System.err.println(e);
        }

    }

    OSCGUI gui;
    public OSCSender(String IP, OSCGUI GUI) {
        ipaddress = IP;
        gui = GUI;

       setIpAddress(ipaddress, 9998);


    }

    public void sendMessage(String address, ArrayList<Double> args) {
        OSCMessage msg;
        if (args != null) msg = new OSCMessage(address,args);
        else msg = new OSCMessage(address);



        sendMessage(msg);
        //System.out.println("Sending Message: " + address + " - args: " + args + " - on IPAddress: " + ipaddress);

    }

    public void sendMessage(OSCMessage msg) {

        messageQueue.add(msg);
        if(gui != null) gui.update(OSCGUI.type.Sending);


        try {
            senderPort.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

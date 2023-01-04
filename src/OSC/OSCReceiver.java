package OSC;

import com.illposed.osc.*;
import com.illposed.osc.transport.udp.OSCPort;
import com.illposed.osc.transport.udp.OSCPortIn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class OSCReceiver{

    private int port = 9999;
    boolean running = true;
    Queue<OSCMessage> messageQueue = new LinkedList<>();
//    protected OSCReceiver(SocketAddress local, SocketAddress remote) throws IOException {
//        super(local, remote);
//    }
OSCPortIn receiver;

    public OSCReceiver() throws IOException {

        receiver = new OSCPortIn(port);
        receiver.startListening();

        receiver.addPacketListener(new packetListener( messageQueue));

    }

    OSCGUI gui;
    public OSCReceiver(OSCGUI oscgui) throws IOException {

        gui = oscgui;

        receiver = new OSCPortIn(port);
        receiver.startListening();

        packetListener packetListener = new packetListener( messageQueue);
        packetListener.addGUI(gui);

        receiver.addPacketListener(packetListener);

    }

public void stop() {
        running = false;
}

    public Queue<OSCMessage> getMessageQueue() {
        return messageQueue;
    }

    public static void main(String[] args) throws IOException, InterruptedException {


      /*  System.out.println(InetAddress.getLocalHost());
        String fullAddress = InetAddress.getLocalHost().toString();
        String outsideIPAddress = fullAddress.substring(fullAddress.indexOf("/")+1);
        System.out.println(outsideIPAddress);
        System.out.println();
       */

//        Scanner stdin = new Scanner(System.in);
//        System.out.println("Print Receivers ip address");
//
//        String RemoteIpaddress = stdin.nextLine();

       //SocketAddress localSocket = new InetSocketAddress(outsideIPAddress, port);
       //SocketAddress remoteSocket = new InetSocketAddress(RemoteIpaddress, 53000);

        OSCReceiver oscReceiver = new OSCReceiver();

        while (oscReceiver.running) {

        }


    }



}

//class messageListener implements OSCMessageListener {
//
//
//    @Override
//    public void acceptMessage(OSCMessageEvent oscMessageEvent) {
//        OSCMessage message = oscMessageEvent.getMessage();
//
//        System.out.println("Message recived: " + message.toString());
//    }
//}
class packetListener implements OSCPacketListener {
    Queue<OSCMessage> messageQueue;
    OSCGUI gui;

    public packetListener(Queue<OSCMessage> messageQ) {
        this.messageQueue = messageQ;
    }

    public void addGUI(OSCGUI GUI) {
        gui = GUI;
    }
    @Override
    public void handlePacket(OSCPacketEvent oscPacketEvent) {
        OSCMessage msg = (OSCMessage) oscPacketEvent.getPacket();

        System.out.printf("Message: %s - Arguments: %s - Info: %s\n", msg.getAddress(), msg.getArguments(),msg.getInfo().getArgumentTypeTags());

        messageQueue.add(msg);

        if (gui != null) gui.update(OSCGUI.type.Reciving);


    }

    @Override
    public void handleBadData(OSCBadDataEvent oscBadDataEvent) {
        System.err.println(oscBadDataEvent);
    }
}

//TODO Add visual gui's, make both reciver and sender objects that can accept commands from others

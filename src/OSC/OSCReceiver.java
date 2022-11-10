package OSC;

import com.illposed.osc.OSCBadDataEvent;
import com.illposed.osc.OSCPacketEvent;
import com.illposed.osc.OSCPacketListener;
import com.illposed.osc.transport.udp.OSCPort;
import com.illposed.osc.transport.udp.OSCPortIn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class OSCReceiver extends OSCPort{
    protected OSCReceiver(SocketAddress local, SocketAddress remote) throws IOException {
        super(local, remote);
    }

    public static void main(String[] args) throws IOException {
        int port = 53000;
        System.out.println(InetAddress.getLocalHost());
        String fullAddress = InetAddress.getLocalHost().toString();
        String IPAddress = fullAddress.substring(fullAddress.indexOf("/")+1);
        System.out.println(IPAddress);
        System.out.println();

        //Scanner stdin = new Scanner(System.in);
        //System.out.println("Print Receivers ip address");

        //String RemoteIpaddress = stdin.nextLine();

        SocketAddress localSocket = new InetSocketAddress(IPAddress, 53001);
        //SocketAddress remoteSocket = new InetSocketAddress(RemoteIpaddress, 53000);

        OSCPortIn receiver = new OSCPortIn(53001);

        receiver.addPacketListener(new messageListener());

        while (true) {

            System.out.println(receiver.getPacketListeners());


        }


    }



}
class messageListener implements OSCPacketListener {

    @Override
    public void handlePacket(OSCPacketEvent oscPacketEvent) {
        System.out.println("IT WORKED " + oscPacketEvent);
    }

    @Override
    public void handleBadData(OSCBadDataEvent oscBadDataEvent) {
        System.err.println(oscBadDataEvent);
    }
}
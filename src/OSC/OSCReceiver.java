package OSC;

import com.illposed.osc.*;
import com.illposed.osc.transport.udp.OSCPort;
import com.illposed.osc.transport.udp.OSCPortIn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class OSCReceiver extends OSCPort{
    protected OSCReceiver(SocketAddress local, SocketAddress remote) throws IOException {
        super(local, remote);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 9999;
        System.out.println(InetAddress.getLocalHost());
        String fullAddress = InetAddress.getLocalHost().toString();
        String outsideIPAddress = fullAddress.substring(fullAddress.indexOf("/")+1);
        System.out.println(outsideIPAddress);
        System.out.println();

        //Scanner stdin = new Scanner(System.in);
        //System.out.println("Print Receivers ip address");

        //String RemoteIpaddress = stdin.nextLine();

        SocketAddress localSocket = new InetSocketAddress(outsideIPAddress, port);
        //SocketAddress remoteSocket = new InetSocketAddress(RemoteIpaddress, 53000);

        OSCPortIn receiver = new OSCPortIn(port);
        receiver.startListening();

        receiver.addPacketListener(new packetListener());

        while (true) {
            Thread.sleep(100);

            //System.out.println(receiver.getPacketListeners());


        }


    }



}

class messageListener implements OSCMessageListener {

    @Override
    public void acceptMessage(OSCMessageEvent oscMessageEvent) {
        OSCMessage message = oscMessageEvent.getMessage();

        System.out.println("Message recived: " + message.toString());
    }
}
class packetListener implements OSCPacketListener {

    @Override
    public void handlePacket(OSCPacketEvent oscPacketEvent) {
        OSCMessage msg = (OSCMessage) oscPacketEvent.getPacket();

        System.out.printf("%s%s\n","IT WORKED ", msg.getAddress());

    }

    @Override
    public void handleBadData(OSCBadDataEvent oscBadDataEvent) {
        System.err.println(oscBadDataEvent);
    }
}
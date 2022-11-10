package OSC;

import com.illposed.osc.*;
import com.illposed.osc.transport.udp.OSCPortOut;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class OSCSender {
    public static void main(String[] args) throws UnknownHostException, IOException, OSCSerializeException {

        Scanner stdin = new Scanner(System.in);
        System.out.println("Print Receivers ip address");

        String ipaddress = stdin.nextLine();
        OSCPortOut sender = new OSCPortOut(InetAddress.getByName(ipaddress), 53001);
        OSCMessage msg = new OSCMessage("/cue/1");
        try {
            sender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            sender.send(msg);
        }
    }
}

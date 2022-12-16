package OSC;

import com.illposed.osc.*;
import com.illposed.osc.transport.udp.OSCPortOut;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class OSCSender {
    public static void main(String[] args) throws UnknownHostException, IOException, OSCSerializeException, InterruptedException {
//169.254.249.205 - wired
        //192.168.0.113 - wifi
        Scanner stdin = new Scanner(System.in);
        System.out.println("Print Receivers ip address");
        int count = 1;

        String ipaddress = stdin.nextLine();
        OSCPortOut sender = new OSCPortOut(InetAddress.getByName(ipaddress), 9998);
        OSCMessage msg = new OSCMessage("/cue/" + count);

        try {
            sender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            Thread.sleep(100);
            sender.send(new OSCMessage("/cue/" + count++));
        }
    }
}

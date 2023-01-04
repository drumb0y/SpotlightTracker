package OSC;

import com.illposed.osc.OSCMessage;

import javax.swing.*;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OSCGUI {

    public OSCReceiver receiver;
    public OSCSender sender;

    public static void main(String[] args) {
        OSCGUI gui = new OSCGUI();
    }

    public OSCGUI() {

        try {
            receiver = new OSCReceiver(this);
            sender = new OSCSender(ipAddressField.getText(), this);
        }
        catch (Exception e) {
            System.err.println(e);
        }

        setupFrame();
    }


    public OSCSender getSender() {
        return sender;
    }

    public OSCReceiver getReceiver() {
        return receiver;
    }

    public JColorChooser getColorChooser() {
        return colorChooser;
    }

    private JPanel SendingPanel;
    private JPanel RecevingPanel;
    private JPanel AddressPanel;
    private JPanel ArgsPanel;
    private JPanel InfoPanel;
    private JPanel RootPane;
    private JTextField MessageField;
    private JTextField ipAddressField;
    private JButton sendButton;
    private JTextField a9998TextField;
    private JTextField argsField;
    private JTabbedPane tabbedPane1;
    private JPanel defaultOSC;
    private JPanel colors;
    private JTextField channelNumber;
    private JPanel sliders;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;


    private void setupFrame() {
        setupBasicPanels();
        setupPresetOSC();





        JFrame frame = new JFrame("OSC GUI");
        frame.setContentPane(RootPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    JColorChooser colorChooser;

    private void setupPresetOSC() {


        colorChooser = new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener(new colorSelectorListener(this));



        colors.add(colorChooser);

    }
    private void setupBasicPanels() {
        sendButton.addActionListener(new sendButtonListener(sender, MessageField, argsField));
        ipAddressField.setText(sender.getIpaddress());
        ipAddressField.addActionListener(new IPtextBoxListener(sender));


        AddressPanel.setLayout(new GridLayout(20,1));
        ArgsPanel.setLayout(new GridLayout(20,1));
        InfoPanel.setLayout(new GridLayout(20,1));
    }

    public static ArrayList<Double> stringToList(String s) {

        if (s.equals("")) return null;
       String[] argsAsStrings = s.split(",");

       ArrayList<Double> args = new ArrayList<>();

        for (String arg :
                argsAsStrings) {
            args.add(Double.parseDouble(arg));
        }

        return args;

    }

    public JTextField getChannelNumber() {
        return channelNumber;
    }

    public void sendMessage(OSCMessage m) {
        sender.sendMessage(m);
    }

    public void sendMessage(String address, ArrayList<Double> args) {
        sender.sendMessage(address,args);
    }

    public enum type {
        Sending, Reciving
    }
    public void update(type t) {

        OSCMessage m;
        Color c;

        if (t.equals(type.Reciving) && receiver.getMessageQueue().size() > 0) {
            m = receiver.getMessageQueue().peek();
            c = new Color(92, 0, 126);
            if (m != null) receiver.getMessageQueue().remove();

        }
        else {
            m = sender.getMessageQueue().peek();
            c = new Color(0, 255, 0);
            if (m != null) sender.getMessageQueue().remove();

        }
        System.out.println("made it to the OSC GUI update()");


        if (AddressPanel.getComponents().length > 19) {
            AddressPanel.remove(0);
            ArgsPanel.remove(0);
            InfoPanel.remove(0);
        }
        String infoString = "";
        if (m != null) {

            if (m.getInfo() != null) {
                infoString = m.getInfo().getArgumentTypeTags().toString();
            }


            JLabel args = new JLabel(m.getArguments().toString());
            JLabel address = new JLabel(m.getAddress());
            JLabel info = new JLabel(infoString);

            address.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            args.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            info.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            address.setForeground(c);
            args.setForeground(c);
            info.setForeground(c);

            AddressPanel.add(address);
            ArgsPanel.add(args);
            InfoPanel.add(info);

            AddressPanel.validate();
            ArgsPanel.validate();
            InfoPanel.validate();
        }

        System.out.println("Tried to add");


    }

    private static OSCGUI instance = null;


    public static OSCGUI getInstance() {

        if (instance == null ) {
            instance = new OSCGUI();
        }

        return instance;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

class sendButtonListener implements ActionListener {

    OSCSender SENDER;
    JTextField Messeger;
    JTextField argsfield;
    public sendButtonListener(OSCSender sender, JTextField msger, JTextField args) {
        SENDER = sender;
        Messeger = msger;
        argsfield = args;

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();


        SENDER.sendMessage(Messeger.getText(), OSCGUI.stringToList(argsfield.getText()));

        System.out.println("tried to send: " + Messeger.getText() + OSCGUI.stringToList(argsfield.getText()));

    }


}

class colorSelectorListener implements ChangeListener {

    OSCGUI gui;
    OSCSender sender;

    JTextField chanNum;


    public colorSelectorListener(OSCGUI g) {
        gui = g;

        sender = g.sender;

        chanNum = g.getChannelNumber();


    }



    @Override
    public void stateChanged(ChangeEvent e) {
        Color color = ((DefaultColorSelectionModel) e.getSource()).getSelectedColor();
        System.out.println(color);

        String CyanAddress = String.format("/eos/chan/%d/param/cyan", Integer.parseInt(chanNum.getText()));
        String MagentaAddress = String.format("/eos/chan/%d/param/magenta", Integer.parseInt(chanNum.getText()));
        String YellowAddress = String.format("/eos/chan/%d/param/yellow", Integer.parseInt(chanNum.getText()));

        ArrayList<Double> CMY = rgbToCmy(color);

        ArrayList<Double> cyan = new ArrayList<>();
        ArrayList<Double> magenta = new ArrayList<>();
        ArrayList<Double> yellow = new ArrayList<>();

        cyan.add(CMY.get(0));
        magenta.add(CMY.get(1));
        yellow.add(CMY.get(2));

        sender.sendMessage(CyanAddress, cyan);
        sender.sendMessage(MagentaAddress, magenta);
        sender.sendMessage(YellowAddress, yellow);


//        sender.sendMessage(address, rgbToCmy(color));


    }

    public ArrayList<Double> rgbToCmy(Color color) {
        ArrayList<Double> CMY = new ArrayList<>();

        double r = color.getRed()/255.0;
        double g = color.getGreen()/255.0;
        double b = color.getBlue()/255.0;

        double k = 1-Math.max(Math.max(r,g), b);

        double c = (1-r-k)/(1-k);
        double m = (1-g-k)/(1-k);
        double y = (1-b-k)/(1-k);

        CMY.add(c*100);
        CMY.add(m*100);
        CMY.add(y*100);

        return CMY;


    }
}

class IPtextBoxListener implements ActionListener {
    OSCSender sender;

    public IPtextBoxListener(OSCSender sender) {
        this.sender = sender;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sender.setIpAddress((((JTextField) e.getSource()).getText()), 9998);
    }
}


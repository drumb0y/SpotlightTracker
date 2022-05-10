import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsTab {




    private JTabbedPane tabbedPane1;
    private JPanel RootPane;
    private JSlider BlueThresholder;
    private JSlider RedThresholder;
    private JSlider GreenThresholder;
    private JPanel Sliders;
    //todo https://examples.javacodegeeks.com/desktop-java/ide/intellij-gui-designer-example/
    private JCheckBox showBlueStreamCheckBox;
    private JCheckBox showGreenStreamCheckBox;
    private JCheckBox showRedStreamCheckBox;
    private JCheckBox showUnfilterdVideoStreamCheckBox;
    private JPanel CameraSelection;
    private JPanel CameraPosition;
    private JPanel LightPosition;
    private JPanel OSC;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private CameraDropDown cameraDropDown1;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;


    public OptionsTab() {


        setupThresholders();


        setupFrame();
    }

    private void setupThresholders() {
        GreenThresholder.addChangeListener(new sliderMoved(Color.GREEN));
        GreenThresholder.setMajorTickSpacing(10);
        GreenThresholder.setMinorTickSpacing(1);
        GreenThresholder.setPaintTicks(true);
        GreenThresholder.setPaintLabels(true);
        GreenThresholder.setFont(new Font("Serif", Font.ITALIC, 12));
        GreenThresholder.setMaximum(255);

        RedThresholder.addChangeListener(new sliderMoved(Color.RED));
        RedThresholder.setMajorTickSpacing(10);
        RedThresholder.setMinorTickSpacing(1);
        RedThresholder.setPaintTicks(true);
        RedThresholder.setPaintLabels(true);
        RedThresholder.setFont(new Font("Serif", Font.ITALIC, 12));
        RedThresholder.setMaximum(255);


        BlueThresholder.addChangeListener(new sliderMoved(Color.BLUE));
        BlueThresholder.setMajorTickSpacing(10);
        BlueThresholder.setMinorTickSpacing(1);
        BlueThresholder.setPaintTicks(true);
        BlueThresholder.setPaintLabels(true);
        BlueThresholder.setFont(new Font("Serif", Font.ITALIC, 12));
        BlueThresholder.setMaximum(255);

    }

    //action listeners

    private class sliderMoved implements ChangeListener {

        Color color;

        public sliderMoved(Color c) {
            this.color = c;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();

            switch(color) {
                    case RED:
                        Options.redThresholder.threshold = (int) (source.getValue() * 2.55);
                        break;
                    case BLUE:
                        Options.blueThresholder.threshold = (int) (source.getValue() * 2.55);
                        break;
                    case GREEN:
                        Options.greenThresholder.threshold = (int) (source.getValue() * 2.55);
                        break;
                }
        }
    }

    private void setupFrame() {
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(RootPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public enum Color {
        RED, GREEN, BLUE
    }
    private JLabel CordinateRed;
    private JLabel CordinateGreen;
    private JLabel CordinateBlue;

    public void pointToLabel(Color c, AidansPoint p) {
        int x = p.x;
        int y = p.y;

        switch (c) {
            case RED:
                CordinateRed.setText("(" + x + ", " + y + ")");
                break;
            case BLUE:
                CordinateBlue.setText("(" + x + ", " + y + ")");
                break;
            case GREEN:
                CordinateGreen.setText("(" + x + ", " + y + ")");
                break;
        }
    }



    public static void main(String[] args) {

    }
    private JTextField eosCue2FireTextField;


    private void createUIComponents() {

        String path = "Media/How the Endocrine System Works.mp4";

        VideoCapture[] cameras = {(new VideoCapture(path)),(new VideoCapture(0, Videoio.CAP_DSHOW))};
        cameraDropDown1 = new CameraDropDown(cameras);

    }

    private static OptionsTab instance = null;
    public static OptionsTab getInstance() {

        if (instance == null ) {
            instance = new OptionsTab();
        }

        return instance;
    }
}

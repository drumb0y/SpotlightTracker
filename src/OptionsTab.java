import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

        GreenThresholder.addChangeListener(new sliderMoved(Color.GREEN));


        setupFrame();
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
                        Options.redThresholder.threshold = source.getValue();
                        break;
                    case BLUE:
                        Options.blueThresholder.threshold = source.getValue();
                        break;
                    case GREEN:
                        Options.greenThresholder.threshold = source.getValue();
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

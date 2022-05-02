import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;

public class OptionsTab {
    private JTabbedPane tabbedPane1;
    private JPanel RootPane;
    private JSlider BlueThresholder;
    private JSlider RedThresholder;
    private JSlider GreenThresholder;
    private JPanel Sliders;
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
    private JLabel CordinateRed;
    private JLabel CordinateGreen;
    private JLabel CordinateBlue;
    private JTextField eosCue2FireTextField;


    private void createUIComponents() {

        String path = "Media/How the Endocrine System Works.mp4";

        VideoCapture[] cameras = {(new VideoCapture(path)),(new VideoCapture(0, Videoio.CAP_DSHOW))};
        cameraDropDown1 = new CameraDropDown(cameras);



    }
}

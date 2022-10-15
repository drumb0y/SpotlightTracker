import customOpencvObjects.CustomVideoCapture;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private JTextField CameraPosX;
    private JTextField CameraPosY;
    private JTextField CameraPosZ;
    private JComboBox cameraDropDown;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;


    public OptionsTab() {


        setupThresholders();
        setupCheckboxes();
        //setupSpinner();
        setupDropdown();

        setupFrame();

    }

    private void setupSpinner() {
        Scale.setValue(1);
    }

    private void setupDropdown() {
        String path = "Media/How the Endocrine System Works.mp4";
        CustomVideoCapture[] cameras = {
                (new CustomVideoCapture("How the Endocrine System Works", path)),
                (new CustomVideoCapture("Front notNeeded.Camera",0, Videoio.CAP_DSHOW)),
                (new CustomVideoCapture("ColorTest", "Media/colorTest.mp4" ))};

        cameraDropDown.addItem(cameras[0]);
        cameraDropDown.addItem(cameras[1]);
        cameraDropDown.addItem(cameras[2]);

        cameraDropDown.setSelectedIndex(0);
        cameraDropDown.addActionListener(new cameraDropboxDropped());

        possibleResolutionOptions.addActionListener(new resolution());



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

    private void setupCheckboxes() {
        showBlueStreamCheckBox.addActionListener(new checkboxToggled(Color.BLUE));
        showGreenStreamCheckBox.addActionListener(new checkboxToggled(Color.GREEN));
        showRedStreamCheckBox.addActionListener(new checkboxToggled(Color.RED));
        showUnfilterdVideoStreamCheckBox.addActionListener(new checkboxToggled(Color.NONE));
    }

    //action listeners

   // private class
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
                        Options.redThresholder.threshold = (int) (source.getValue());
                        break;
                    case BLUE:
                        Options.blueThresholder.threshold = (int) (source.getValue());
                        break;
                    case GREEN:
                        Options.greenThresholder.threshold = (int) (source.getValue());
                        break;
                }
        }
    }

    private class checkboxToggled implements ActionListener {
        Color color;

        public checkboxToggled(Color c) {
            this.color = c;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.out.println(color);
                Display.getInstance().toggleStream(color);

        }
    }

    private class cameraDropboxDropped implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox feed = (JComboBox) e.getSource();
            VideoCapture cam = (VideoCapture) feed.getSelectedItem();
            Mat testImage = new Mat();
            cam.read(testImage);

            /** makes sure that the program does not crash when a camera is not plugged in **/
            if (feed == null || cam == null || testImage.cols() < 1) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                return;
            }
            ColorSelector.getInstance().setCamera(cam);

        }
    }

    private JComboBox possibleResolutionOptions;

    private class resolution implements ActionListener {
        ArrayList<String> storedRes = new ArrayList<>();

        public resolution() {
            storedRes.add("1920 x 1080");
            storedRes.add("1280 x 720");
            storedRes.add("640 x 480");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox box = (JComboBox) e.getSource();

            String activeResolution = (String) box.getSelectedItem();

            System.out.println(box.getSelectedItem());

            if (!storedRes.contains(activeResolution)) {
                storedRes.add(activeResolution);
                box.addItem(activeResolution);
            }


            changeResolution(activeResolution);
        }

        public boolean changeResolution(String res) {
            System.out.println(res);
            String[] temp = res.split(" ");
           // System.out.println(temp.toString());
           // System.out.println(temp[0] +" " + temp[1] + " " + temp[2]);



            if (temp.length != 3
            ||  !temp[1].equalsIgnoreCase("x")) {
                return false;
            }

            else {



                int width = Integer.parseInt(temp[0]);
                int height = Integer.parseInt(temp[2]);

                Mat testImg = new Mat(width,height,Core.);


                VideoCapture cam = ColorSelector.getInstance().camera;


                cam.set(Videoio.CAP_PROP_FRAME_WIDTH, width);
                cam.set(Videoio.CAP_PROP_FRAME_HEIGHT, height);

                System.out.printf("%d, %d, %f, %f \n", width, height, cam.get(Videoio.CAP_PROP_FRAME_WIDTH), cam.get(Videoio.CAP_PROP_FRAME_HEIGHT));
            }

            return false;
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
        RED, GREEN, BLUE, NONE
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
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JSpinner Scale;



    private void createUIComponents() {

        String path = "Media/How the Endocrine System Works.mp4";

        VideoCapture[] cameras = {(new VideoCapture(path)),(new VideoCapture(0, Videoio.CAP_DSHOW))};
        //cameraDropDown1 = new CameraDropDown(cameras);

    }

    private static OptionsTab instance = null;
    public static OptionsTab getInstance() {

        if (instance == null ) {
            instance = new OptionsTab();
        }

        return instance;
    }
}

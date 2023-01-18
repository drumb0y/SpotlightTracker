import OSC.OSCGUI;
import customOpencvObjects.CustomVideoCapture;
import org.opencv.core.CvType;
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
    private JCheckBox showUnfilteredVideoStreamCheckBox;
    private JPanel CameraSelection;
    private JPanel CameraPosition;
    private JPanel LightPosition;
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
        setupSpinner();
        setupDropdown();
        setupRadioButtons();
        setupGUI();

        setupFrame();

    }

    ColorSelector colorSelector;
    public void setColorselector(ColorSelector c) {
        colorSelector = c;
    }

    private void setupSpinner() {
        resolutionScalingFactor.setValue(100);
        resolutionScalingFactor.addChangeListener(new resolutionScalingListener());
    }

    private void setupDropdown() {
        String path = "Media/How the Endocrine System Works.mp4";
        CustomVideoCapture[] cameras = {
                (new CustomVideoCapture("How the Endocrine System Works", path, 60)),
                (new CustomVideoCapture("Front Camera",0, Videoio.CAP_DSHOW, 84)),
                (new CustomVideoCapture("ColorTest", "Media/colorTest.mp4", 40 )),
                (new CustomVideoCapture("external camera", 1, Videoio.CAP_DSHOW, 40)),
                (new CustomVideoCapture("Thespian at work", "Media/2023-01-02 15-40-24.mp4", 40)),
                (new CustomVideoCapture("test 1-14", "Media/20230115_070059_0003.MP4", 20)),
                (new CustomVideoCapture("Camera 4", 3, Videoio.CAP_DSHOW, 100))};

        cameraDropDown.addItem(cameras[0]);
        cameraDropDown.addItem(cameras[1]);
        cameraDropDown.addItem(cameras[2]);
        cameraDropDown.addItem(cameras[3]);
        cameraDropDown.addItem(cameras[4]);
        cameraDropDown.addItem(cameras[5]);

        cameraDropDown.setSelectedIndex(1);
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
        showUnfilteredVideoStreamCheckBox.addActionListener(new checkboxToggled(Color.NONE));
    }

    OSCGUI oscgui;
    private void setupGUI() {
        oscgui = new OSCGUI();
    }

    public OSCGUI getOscgui() {
        return oscgui;
    }

    ButtonGroup coordinateUseButtons;
    private void setupRadioButtons() {
        coordinateUseButtons = new ButtonGroup();

        coordinateUseButtons.add(useBlueCoordinatesRadioButton);
        coordinateUseButtons.add(useGreenCoordinatesRadioButton);
        coordinateUseButtons.add(useRedCoordinatesRadioButton);
        coordinateUseButtons.add(useNothing);

        useBlueCoordinatesRadioButton.addActionListener(new coordinatesSwitched(Color.BLUE, oscgui, this));
        useGreenCoordinatesRadioButton.addActionListener(new coordinatesSwitched(Color.GREEN, oscgui, this));
        useRedCoordinatesRadioButton.addActionListener(new coordinatesSwitched(Color.RED, oscgui, this));
        useNothing.addActionListener(new coordinatesSwitched(Color.NONE, oscgui, this));

    }


    //todo make the radio buttons actually do something

    //action listeners



    private class coordinatesSwitched implements ActionListener {
        Color colortype;

        OSCGUI GUI;
        OptionsTab options;

        public coordinatesSwitched(Color color, OSCGUI g, OptionsTab tab) {
            //c = ColorSelector.getInstance();
            colortype = color;
            GUI = g;
            options = tab;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton button = (JRadioButton) e.getSource();

            System.out.println("changed colorType to " + colortype);

            options.setColor(colortype);
        }
    }


    Color currentColor = Color.RED;

    public void setColor(Color c) {
currentColor = c;

    }

    public double[] getPolarCordinates() {
        AidansPoint CurrentPoint;

        switch (currentColor) {
            case RED -> {CurrentPoint = colorSelector.redPoint;}
            case BLUE -> {CurrentPoint = colorSelector.bluePoint;}
            case GREEN -> {CurrentPoint = colorSelector.greenPoint;}
            default -> CurrentPoint = new AidansPoint(0,0);
        }

        int x = CurrentPoint.x;
        int y = CurrentPoint.y;

        return CartesianToPolar.convert(x,y);
    }

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
            CustomVideoCapture cam = (CustomVideoCapture) feed.getSelectedItem();
            Mat testImage = new Mat();
            cam.read(testImage);

            /** makes sure that the program does not crash when a camera is not plugged in **/
            if (feed == null || cam == null || testImage.cols() < 1) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                return;
            }
            ColorSelector.getInstance().setCamera(cam);

            int factor = cam.getZoom();
            int width = (int) cam.get(Videoio.CAP_PROP_FRAME_WIDTH);
            int height = (int) cam.get(Videoio.CAP_PROP_FRAME_HEIGHT);


            ColorSelector.getInstance().setSize(width*factor/100,height*factor/100);

        }
    }

    private JComboBox possibleResolutionOptions;

    private class resolutionScalingListener implements ChangeListener {



        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner source = (JSpinner) e.getSource();

            int factor = (int) source.getValue();
            int width = (int) ColorSelector.getInstance().getCamera().get(Videoio.CAP_PROP_FRAME_WIDTH);
            int height = (int) ColorSelector.getInstance().getCamera().get(Videoio.CAP_PROP_FRAME_HEIGHT);

            ColorSelector.getInstance().setSize(width*factor/100,height*factor/100);


        }
    }
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

                Mat testImg = new Mat(width, height, CvType.CV_8UC1);


                CustomVideoCapture cam = (CustomVideoCapture) cameraDropDown.getSelectedItem();

                ColorSelector.getInstance().setSize(width,height);

                cam.read(testImg);

                ColorSelector.getInstance().setCamera(cam);

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
    private JLabel coordinateRed;
    private JLabel coordinateGreen;
    private JLabel coordinateBlue;

    public void pointToLabel(Color c, AidansPoint p) {
        int x = p.x;
        int y = p.y;

        switch (c) {
            case RED:
                coordinateRed.setText("(" + x + ", " + y + ")");
                break;
            case BLUE:
                coordinateBlue.setText("(" + x + ", " + y + ")");
                break;
            case GREEN:
                coordinateGreen.setText("(" + x + ", " + y + ")");
                break;
        }
    }



    public static void main(String[] args) {

    }
    private JTextField eosCue2FireTextField;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JSpinner resolutionScalingFactor;
    private JRadioButton useRedCoordinatesRadioButton;
    private JRadioButton useBlueCoordinatesRadioButton;
    private JRadioButton useGreenCoordinatesRadioButton;
    private JPanel OSC;
    private JTextField IPADDRESS;
    private JTextField PORTNUM;
    private JRadioButton useNothing;
    private JSpinner Scale;



    private void createUIComponents() {

        String path = "Media/How the Endocrine System Works.mp4";

        VideoCapture[] cameras = {(new VideoCapture(path)),(new VideoCapture(0, Videoio.CAP_DSHOW))};
        //cameraDropDown1 = new notNeeded.CameraDropDown(cameras);

    }

    private static OptionsTab instance = null;
    public static OptionsTab getInstance() {

        if (instance == null ) {
            instance = new OptionsTab();
        }

        return instance;
    }

    //add main classes for lots of classes to do individual tests of features.
}

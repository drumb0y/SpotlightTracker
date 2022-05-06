import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Options implements ChangeListener {

    static String greenPath = "Media/unnamed.jpg";
    static String bluePath = "Media/Capture.PNG";
    static String redPath = "Media/unnamed (2).jpg";
    static String originalPath = "Media/unnamed (1).jpg";

    static String nameOfApp = "Spotlight Tracking thing";

    public static Image greenImage = Mat2BufferedImage(Imgcodecs.imread(greenPath));
    public static Image blueImage = Mat2BufferedImage(Imgcodecs.imread(bluePath));
    public static Image redImage = Mat2BufferedImage(Imgcodecs.imread(redPath));
    public static Image originalImage = Mat2BufferedImage(Imgcodecs.imread(originalPath));

    public static ThresholdingSlider greenThresholder = ColorSelector.greenThresholder;
    public static ThresholdingSlider blueThresholder = ColorSelector.blueThresholder;
    public static ThresholdingSlider redThresholder = ColorSelector.redThresholder;



    public static String path = "Media/How the Endocrine System Works.mp4";

    public static VideoCapture[] cameras = {(new VideoCapture(path)),(new VideoCapture(0, Videoio.CAP_DSHOW))};

    //camera = new VideoCapture(0,Videoio.CAP_DSHOW);

    //JFRAME stuff
    JFrame frame;

    JPanel mainPanel;

    JPanel colorSliders;
    JPanel dropdowns;
    CameraDropDown cameraDropdownBox;

    int rowsForLayout = 2;

    public static void main(String[] args) {

    }


    public Options() {
        frame = new JFrame("hello");
        colorSliders = new JPanel();
        dropdowns = new JPanel();
        mainPanel = new JPanel();

        mainPanel.setLayout(new GridLayout(rowsForLayout,2,20,20));
        colorSliders.setLayout(new GridLayout(6,1,10,10));
        dropdowns.setLayout(new GridLayout(2,1,10,10));



        cameraDropdownBox = new CameraDropDown(cameras);



        greenThresholder.addChangeListener(this);
        blueThresholder.addChangeListener(this);
        redThresholder.addChangeListener(this);

        colorSliders.add(new JLabel("green"));
        colorSliders.add(greenThresholder);
        colorSliders.add(new JLabel("blue"));
        colorSliders.add(blueThresholder);
        colorSliders.add(new JLabel("red"));
        colorSliders.add(redThresholder);

        dropdowns.add(cameraDropdownBox);
        //dropdowns.add(new CameraDropDown(cameras));


        mainPanel.add(dropdowns);
        mainPanel.add(colorSliders);
        System.out.println("1");

        frame.add(mainPanel);
        //frame.add(OptionsTab.getInstance().getRootPane());


        if (false) {
            Display.getInstance().addPanelToTab(mainPanel);
        }

        createFrame();
    }

    public void createFrame() {


//        ImageIcon icon = new ImageIcon(img2);
//        JLabel lbl = new JLabel(icon);

        frame.pack();

        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

    }

    public static Options instance = null;

    public static Options getInstance() {

        if (instance == null ) {
            instance = new Options();
        }

        return instance;
    }


    public Image getGreenImage() {
        return greenImage;
    }

    public Image getBlueImage() {
        return blueImage;
    }

    public Image getRedImage() {
        return redImage;
    }

    public Image getOriginalImage() {
        return originalImage;
    }

    public static BufferedImage Mat2BufferedImage(Mat m) {
        // Fastest code
        // output can be assigned either to a BufferedImage or to an Image

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }


    public void stateChanged(ChangeEvent e) {
        ThresholdingSlider source = (ThresholdingSlider) e.getSource();

        if (source.sameColorType(ThresholdingSlider.Colors.GREEN)) {
            greenThresholder.threshold = source.getValue();
        }

        if (source.sameColorType(ThresholdingSlider.Colors.BLUE)) {
            blueThresholder.threshold = source.getValue();
        }

        if (source.sameColorType(ThresholdingSlider.Colors.RED)) {
            redThresholder.threshold = source.getValue();
        }

        //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA         changed state: " + source.getValue());

    }
}

//todo label screens
//change name of app
//put names for cameras (make it customizable)

/** put images, different dropboxes, etc on options frame
  */



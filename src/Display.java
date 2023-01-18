import notNeeded.Collector;
import notNeeded.Id;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import javax.swing.*;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.*;
import java.util.ArrayList;


public class Display implements Id, ChangeListener {


    int numbers;
    int id;

    static ThresholdingSlider greenTolerance;
    static ThresholdingSlider blueTolerance;
    static ThresholdingSlider redTolerance;


    Image greenImage, blueImage, redImage, originalImage;


    //points for each panel
    JLabel greenPoint;
    JLabel redPoint;
    JLabel bluePoint;

    //jframe stuffs
    JFrame frame = new JFrame(Options.nameOfApp);
    int scale = 1;

    JPanel greenImagePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            greenImagePanel.setBackground(Color.RED);

            g.setColor(Color.BLACK);

            if(scale == 1) {
                g.fillRect(0, 0, greenImagePanel.getWidth()/scale, greenImagePanel.getHeight()/scale);
                g.drawImage(greenImage, 0, 0, greenImage.getWidth(this), greenImage.getHeight(this), this);

            }
            else {

                Image tempImage = greenImage.getScaledInstance(greenImage.getWidth(this) / scale, greenImage.getHeight(this) / scale, Image.SCALE_FAST);

                g.fillRect(0, 0, greenImagePanel.getWidth() / scale, greenImagePanel.getHeight() / scale);
                g.drawImage(tempImage, 0, 0, tempImage.getWidth(this), tempImage.getHeight(this), this);

            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(greenImage.getWidth(this), greenImage.getHeight(this));
        }
    };
    JPanel blueImagePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            blueImagePanel.setBackground(Color.RED);

            g.setColor(Color.BLACK);

            if(scale == 1) {
                g.fillRect(0, 0, blueImagePanel.getWidth(), blueImagePanel.getHeight());
                g.drawImage(blueImage, 0, 0, blueImage.getWidth(this), blueImage.getHeight(this), this);
            }
            else {
                Image tempImage = blueImage.getScaledInstance(blueImage.getWidth(this) / scale, blueImage.getHeight(this) / scale, Image.SCALE_FAST);

                g.fillRect(0, 0, blueImagePanel.getWidth(), blueImagePanel.getHeight());
                g.drawImage(tempImage, 0, 0, tempImage.getWidth(this), tempImage.getHeight(this), this);

            }

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(greenImage.getWidth(this), greenImage.getHeight(this));
        }
    };
    JPanel redImagePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            redImagePanel.setBackground(Color.RED);

            g.setColor(Color.BLACK);

            if (scale == 1) {
                g.fillRect(0, 0, redImagePanel.getWidth(), redImagePanel.getHeight());
                g.drawImage(redImage, 0, 0, redImage.getWidth(this), redImage.getHeight(this), this);
            }
            else{
                Image tempImage = redImage.getScaledInstance(redImage.getWidth(this) / scale, redImage.getHeight(this) / scale, Image.SCALE_FAST);

                g.fillRect(0, 0, redImagePanel.getWidth(), redImagePanel.getHeight());
                g.drawImage(tempImage, 0, 0, tempImage.getWidth(this), tempImage.getHeight(this), this);
            }

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(greenImage.getWidth(this), greenImage.getHeight(this));
        }
    };

    JPanel imagePanels = new JPanel();

    JTabbedPane tabbedPane = new JTabbedPane();

    JPanel originalImagePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if(scale == 1) {
                g.clearRect(0, 0, originalImage.getWidth(this), originalImage.getHeight(this));
                g.drawImage(originalImage, 0, 0, originalImage.getWidth(this), originalImage.getHeight(this), this);
            }
            else{
                Image tempImage = originalImage.getScaledInstance(originalImage.getWidth(this) / scale, originalImage.getHeight(this) / scale, Image.SCALE_FAST);

                g.clearRect(0, 0, originalImage.getWidth(this), originalImage.getHeight(this));
                g.drawImage(tempImage, 0, 0, tempImage.getWidth(this), tempImage.getHeight(this), this);

            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(originalImage.getWidth(this), originalImage.getHeight(this));
        }
    };

    //JSlider greenTolerance = new JSlider(JSlider.HORIZONTAL, 0,255, greenThresholder.greenThreshold);
    //ImageIcon icon;

    public Display() {
        numbers = 0;
        id = (int) (Math.random() * 100000000);

        initImages();


        createFrame(greenImage);

    }

    public void initImages() {
        greenImage = Options.greenImage;
        blueImage = Options.blueImage;
        redImage = Options.redImage;
        originalImage = Options.originalImage;
    }

    public static void main(String[] args) {

        Collector collector = testing.getCollector();


        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());

        //frame


        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }


    public void createFrame(Image img2) {
//        ImageIcon icon = new ImageIcon(img2);
//        JLabel lbl = new JLabel(icon);

        //frame.setLayout(new GridLayout(2, 2));
        frame.setSize(img2.getWidth(null) + 50, img2.getHeight(null) + 50);

        greenTolerance.addChangeListener(this);
        blueTolerance.addChangeListener(this);
        blueTolerance.addChangeListener(this);

        greenPoint = new JLabel();
        bluePoint = new JLabel();
        redPoint = new JLabel();

        greenImagePanel.add(greenPoint);
        blueImagePanel.add(bluePoint);
        redImagePanel.add(redPoint);


       // greenImagePanel.add(greenTolerance);
       // blueImagePanel.add(blueTolerance);
       // redImagePanel.add(redTolerance);

        greenTolerance.setPreferredSize(new Dimension(640, 50));
        blueTolerance.setPreferredSize(new Dimension(640, 50));
        blueTolerance.setPreferredSize(new Dimension(640, 50));


        imagePanels.setLayout(new GridLayout(2,2));
        imagePanels.setName("video streams");

        imagePanels.add(redImagePanel);
        imagePanels.add(greenImagePanel);
        imagePanels.add(blueImagePanel);
        imagePanels.add(originalImagePanel);

        tabbedPane.add(imagePanels);

        frame.add(tabbedPane);

        frame.pack();
        //greenTolerance.setSize(100, 100);

        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void displayImage(Image img2) {

        //BufferedImage img=ImageIO.read(new File("/HelloOpenCV/lena.png"));
        ImageIcon icon = new ImageIcon(img2);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img2.getWidth(null) + 50, img2.getHeight(null) + 50);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    boolean greenStreamVisible = true;
    boolean redStreamVisible = true;
    boolean blueStreamVisible = true;
    boolean noneStreamVisible = true;

    public void toggleStream(OptionsTab.Color c) {
        switch (c) {
            case RED:
                redStreamVisible = !redStreamVisible;
                break;
            case GREEN:
                greenStreamVisible = !greenStreamVisible;
                break;
            case BLUE:
                blueStreamVisible = !blueStreamVisible;
                break;
            case NONE:
                noneStreamVisible = !noneStreamVisible;
                break;

        }

    }

    public void updateFrame() {


        if (greenStreamVisible) greenImagePanel.repaint();
        if (redStreamVisible) redImagePanel.repaint();
        if (blueStreamVisible) blueImagePanel.repaint();


        if (noneStreamVisible) originalImagePanel.repaint();
        //System.out.println(greenImagePanel.getSize());


        int width = greenImagePanel.getWidth();


        //System.out.println(greenImagePanel.getSize() + ",  " +greenImagePanel.getWidth() + ", " + greenTollerence.getSize());

        double[] arr = OptionsTab.getInstance().getPolarCordinates();
        OptionsTab.getInstance().getOscgui().sendMessage("/eos/chan/250/param/tilt/pan", arrayToArraylist(arr));

        //frame.pack();

        //images.clear();
    }

    public ArrayList<Double> arrayToArraylist(double[] array) {
        ArrayList<Double> arraylist = new ArrayList<>();

        for (double a :
                array) {
            arraylist.add(a);
        }

        return arraylist;
    }

    public int getId() {
        return id;
    }

    public void addNum(int i) {
        numbers += i;
    }

    public static void setGreenTolerence(ThresholdingSlider greenTolerence1) {
        greenTolerance = greenTolerence1;
    }

    public static void setBlueTolerence(ThresholdingSlider blueTolerence1) { blueTolerance = blueTolerence1;
    }

    public static void setRedTolerence(ThresholdingSlider redTolerence1) {
        redTolerance = redTolerence1;
    }

    private static Display instance = null;

    public static Display getInstance() {

        if (instance == null) {
            instance = new Display();
        }

        return instance;
    }

    public JFrame getFrame() {
        return frame;
    }

    public boolean setGreenImage(Mat mat, AidansPoint p) {
        if (mat == null) return false;

        greenImage = Options.Mat2BufferedImage(mat);

        if (p != null) {
            greenPoint.setText("(" + p.x + "," + p.y + ")");
        }
        return true;

    }

    public boolean setBlueImage(Mat mat, AidansPoint p) {
        if (mat == null) return false;

        blueImage = Options.Mat2BufferedImage(mat);
        if (p != null) {
            bluePoint.setText("(" + p.x + "," + p.y + ")");
        }
        return true;
    }

    public boolean setRedImage(Mat mat, AidansPoint p) {
        if (mat == null) return false;

        redImage = Options.Mat2BufferedImage(mat);
        if (p != null) {
            redPoint.setText("(" + p.x + "," + p.y + ")");
        }
        return true;
    }

    public boolean setCustomImage(Mat mat) {
        //todo aaaaaaaaaaaaaaaaaaaa
        return false;
    }


    public boolean setOriginalImage(Mat mat) {
        if (mat == null) return false;

        originalImage = Options.Mat2BufferedImage(mat);
        return true;

    }

    public void addPanelToTab(JPanel p) {
        tabbedPane.add(p);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        ThresholdingSlider source = (ThresholdingSlider) e.getSource();

        if (source.sameColorType(ThresholdingSlider.Colors.GREEN)) {
            greenTolerance.threshold = source.getValue();
        }

        if (source.sameColorType(ThresholdingSlider.Colors.BLUE)) {
            blueTolerance.threshold = source.getValue();
        }

        if (source.sameColorType(ThresholdingSlider.Colors.RED)) {
            redTolerance.threshold = source.getValue();
        }

        //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA         changed state: " + source.getValue());

    }
}

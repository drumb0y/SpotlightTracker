import notNeeded.Collector;
import notNeeded.Id;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import javax.swing.*;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.*;


public class Display implements Id, ChangeListener {


    int numbers;
    int id;

    static ThresholdingSlider greenTollerence;
    static ThresholdingSlider blueTollerence;
    static ThresholdingSlider redTollerence;


    Image greenImage, blueImage, redImage, originalImage;


    //points for each panel
    JLabel greenPoint;
    JLabel redPoint;
    JLabel bluePoint;

    //jframe stuffs
    JFrame frame = new JFrame(Options.nameOfApp);
    JPanel greenImagePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            greenImagePanel.setBackground(Color.RED);

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, greenImagePanel.getWidth(), greenImagePanel.getHeight());
            g.drawImage(greenImage, 0, 0, greenImage.getWidth(this), greenImage.getHeight(this), this);

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
            g.fillRect(0, 0, blueImagePanel.getWidth(), blueImagePanel.getHeight());
            g.drawImage(blueImage, 0, 0, blueImage.getWidth(this), blueImage.getHeight(this), this);

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
            g.fillRect(0, 0, redImagePanel.getWidth(), redImagePanel.getHeight());
            g.drawImage(redImage, 0, 0, redImage.getWidth(this), redImage.getHeight(this), this);

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

            g.clearRect(0, 0, originalImage.getWidth(this), originalImage.getHeight(this));
            g.drawImage(originalImage, 0, 0, originalImage.getWidth(this), originalImage.getHeight(this), this);

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(originalImage.getWidth(this), originalImage.getHeight(this));
        }
    };

    //JSlider greenTollerence = new JSlider(JSlider.HORIZONTAL, 0,255, greenThresholder.greenThreshold);
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

        greenTollerence.addChangeListener(this);
        blueTollerence.addChangeListener(this);
        redTollerence.addChangeListener(this);

        greenPoint = new JLabel();
        bluePoint = new JLabel();
        redPoint = new JLabel();

        greenImagePanel.add(greenPoint);
        blueImagePanel.add(bluePoint);
        redImagePanel.add(redPoint);


        greenImagePanel.add(greenTollerence);
        blueImagePanel.add(blueTollerence);
        redImagePanel.add(redTollerence);

        greenTollerence.setPreferredSize(new Dimension(640, 50));
        redTollerence.setPreferredSize(new Dimension(640, 50));
        blueTollerence.setPreferredSize(new Dimension(640, 50));


        imagePanels.setLayout(new GridLayout(2,2));
        imagePanels.setName("video streams");

        imagePanels.add(redImagePanel);
        imagePanels.add(greenImagePanel);
        imagePanels.add(blueImagePanel);
        imagePanels.add(originalImagePanel);

        tabbedPane.add(imagePanels);

        frame.add(tabbedPane);

        frame.pack();
        //greenTollerence.setSize(100, 100);

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


        //frame.pack();

        //images.clear();
    }

    public int getId() {
        return id;
    }

    public void addNum(int i) {
        numbers += i;
    }

    public static void setGreenTollerence(ThresholdingSlider greenTollerence1) {
        greenTollerence = greenTollerence1;
    }

    public static void setBlueTollerence(ThresholdingSlider blueTollerence) {
        Display.blueTollerence = blueTollerence;
    }

    public static void setRedTollerence(ThresholdingSlider redTollerence) {
        Display.redTollerence = redTollerence;
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
            greenTollerence.threshold = source.getValue();
        }

        if (source.sameColorType(ThresholdingSlider.Colors.BLUE)) {
            blueTollerence.threshold = source.getValue();
        }

        if (source.sameColorType(ThresholdingSlider.Colors.RED)) {
            redTollerence.threshold = source.getValue();
        }

        //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA         changed state: " + source.getValue());

    }
}

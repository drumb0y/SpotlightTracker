import notNeeded.Id;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.util.ArrayList;
import java.util.List;

//run this class

public class ColorSelector implements Id {
    int id;


    Display m;

    VideoCapture camera;

    Mat onlyGreenimage = new Mat();
    Mat onlyRedimage = new Mat();
    Mat onlyBlueimage = new Mat();

    AidansPoint redPoint, bluePoint, greenPoint;

    public static ThresholdingSlider greenThresholder = new ThresholdingSlider(ThresholdingSlider.Colors.GREEN);
    public static ThresholdingSlider blueThresholder = new ThresholdingSlider(ThresholdingSlider.Colors.BLUE);
    public static ThresholdingSlider redThresholder = new ThresholdingSlider(ThresholdingSlider.Colors.RED);

   // ColorSelector colorSelector = (ColorSelector) notNeeded.Collector.getInstance().getFromMap(notNeeded.Collector.TYPES.COLORSELECTOR);

    private Mat originalImage = new Mat();;
//    private Mat blueImage = new Mat();
//    private Mat greenImage = new Mat();
//    private Mat redImage = new Mat();

    boolean copyOver = true;

    public enum IMAGES {
        GREEN,
        RED,
        BLUE,
        ORIGINAL
    }


    public ColorSelector() {
       // main(null);

        id = (int) (Math.random()*100000000);

        redPoint = new AidansPoint(0,0);
        bluePoint = new AidansPoint(0,0);
        greenPoint = new AidansPoint(0,0);


    }


    public static void main(String[] args) throws InterruptedException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //System.out.println(mat.dump());



        ColorSelector colorSelector = ColorSelector.getInstance();




        colorSelector.scan_1();

    }

    void scan_1() throws InterruptedException {



        Display.setGreenTollerence(greenThresholder);
        Display.setBlueTollerence(blueThresholder);
        Display.setRedTollerence(redThresholder);

        m = Display.getInstance();

        Options o = Options.getInstance();

        //m.createFrame(m.Mat2BufferedImage(Imgcodecs.imread("C:/aidan/1Capture.PNG")));


        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String path = "Media/How the Endocrine System Works.mp4";

        camera = new VideoCapture(path);

        //camera = new VideoCapture(0,Videoio.CAP_DSHOW);

        startVideo();

    }


    public void startVideo() {

        long count = (long) camera.get(Videoio.CAP_PROP_FRAME_COUNT);
        while (camera.read(originalImage)) {
            //Thread.sleep((long) (((double) 1/6)*100));
            //m.setGreenImage((new Mat(originalImage.rows(),originalImage.cols(), CvType.CV_8UC3, new Scalar(0,0,0))));

            ArrayList<Mat> colors = new ArrayList<Mat>(3);

            Core.split(originalImage, colors);

            Mat blueImage = colors.get(0);
            Mat redImage = colors.get(2);
            Mat greenImage = colors.get(1);

            m.setGreenImage(scan_green(greenImage, blueImage),greenPoint);
            m.setBlueImage(scan_blue(blueImage, redImage),bluePoint);
            m.setRedImage(scan_red(redImage,greenImage),redPoint);
            m.setCustomImage(scan_custom());


            m.setOriginalImage(originalImage);

            m.updateFrame();




            //if(count%100 == 10) System.out.println(count);

            if(count == 0) {
                camera.set(Videoio.CAP_PROP_POS_FRAMES, 0);
                count = (long) camera.get(Videoio.CAP_PROP_FRAME_COUNT);

                //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            }

            count--;

            //notNeeded.Collector.getInstance().saveImage(notNeeded.Collector.IMAGES.GREEN, onlyGreenCup);
            //System.out.println("width, height, and value of slider: " + greenThresholder.getWidth() + ", " + greenThresholder.getHeight() + ", " + greenThresholder.getValue() + "\t count:" + count + "\ttotal frames: " + camera.get(Videoio.CAP_PROP_FRAME_COUNT) + "\t current frame " + camera.get(Videoio.CAP_PROP_POS_FRAMES) + "\t  ");

            originalImage = new Mat();
        }

        System.out.println("this is the end count: " + count);
    }

    private Mat scan_red(Mat coloredImage, Mat subtractingImage) {
        ///red STUFF


        //subtract all the green pixels to make the red pixels easier to find
        Mat greenSubtractedRed = new Mat();
        Core.subtract(coloredImage, subtractingImage, greenSubtractedRed);

        //set the red pixels that have less than the threshold value to "0" and above to "1"
        Mat thresh = new Mat();
        Imgproc.threshold(greenSubtractedRed, thresh, redThresholder.threshold, 255, Imgproc.THRESH_BINARY);

        //create a list of all the clumps of pixels, calling them contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(thresh, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        double MaxSize = 0; //size of largest pixels
        int MaxIndex = 0; //where is the group of largest pixels

        for (int i = 0; i < contours.size(); i++) { //got througth each group of pixels and find the largest one (probably the wanted object)
            if (Imgproc.contourArea(contours.get(i)) > MaxSize) {
                MaxSize = Imgproc.contourArea(contours.get(i));
                MaxIndex = i;

            }
        }

        //set the original image's pixels to 0
        Mat zeroColor = Mat.zeros(originalImage.rows(), originalImage.cols(), CvType.CV_8UC1);
        Mat negativeColorSpace = Mat.ones(originalImage.rows(), originalImage.cols(), CvType.CV_8UC1);


        //where the largest contour is, make those pixels at their normal value
        Imgproc.drawContours(zeroColor, contours, MaxIndex, Scalar.all(255), 8, Imgproc.LINE_8, hierarchy);
        Imgproc.fillPoly(zeroColor,contours,Scalar.all(255),Imgproc.LINE_4);
        Imgproc.fillPoly(negativeColorSpace, contours, Scalar.all(0),Imgproc.LINE_4);


        //Mat maskedblue = new Mat();

        if (copyOver) {
            onlyRedimage = new Mat();

            originalImage.copyTo(onlyRedimage, zeroColor);

            //m.setRedImage(onlyRedimage); //sets a colored image (seems to leave artifacts
        }
        else {
            onlyRedimage = zeroColor;
            //m.setRedImage(zeroColor); //sets a binary mask
        }

        //drawing box
        //https://docs.opencv.org/3.4/da/d0c/tutorial_bounding_rects_circles.html

        MatOfPoint2f mainContour;
        Rect boundRect;
        Point center = new Point();
        float[] radius = new float[1];


        mainContour = new MatOfPoint2f();

        //draw a rectangle over the largest contour
        if(contours.size() > 0) {

            Point[] biggestContour = contours.get(MaxIndex).toArray();
            Imgproc.approxPolyDP(new MatOfPoint2f(biggestContour), mainContour, 3, true);
            boundRect = Imgproc.boundingRect(new MatOfPoint((mainContour.toArray())));
            center = new Point();
            Imgproc.minEnclosingCircle(mainContour, center, radius);
            Imgproc.rectangle(onlyRedimage, boundRect.tl(), boundRect.br(), new Scalar(32,246,0));

        }

        redPoint = new AidansPoint(center.x,center.y);


        return onlyRedimage;
    }

    private Mat scan_blue(Mat coloredImage, Mat subtractingImage) {
        ///Blue STUFF


        Mat redSubtractedBlue = new Mat();
        Core.subtract(coloredImage, subtractingImage, redSubtractedBlue);

        Mat thresh = new Mat();
        Imgproc.threshold(redSubtractedBlue, thresh, blueThresholder.threshold, 255, Imgproc.THRESH_BINARY);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(thresh, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        double MaxSize = 0; //size of largest pixels
        int MaxIndex = 0; //where is the group of largest pixels

        for (int i = 0; i < contours.size(); i++) { //got througth each group of pixels and find the largest one (probely the cup/wanted object)
            if (Imgproc.contourArea(contours.get(i)) > MaxSize) {
                MaxSize = Imgproc.contourArea(contours.get(i));
                MaxIndex = i;

            }
        }

        Mat zeroColor = Mat.zeros(originalImage.rows(), originalImage.cols(), CvType.CV_8UC1);
        Mat negativeColorSpace = Mat.ones(originalImage.rows(), originalImage.cols(), CvType.CV_8UC1);


        Imgproc.drawContours(zeroColor, contours, MaxIndex, Scalar.all(255), 8, Imgproc.LINE_8, hierarchy);
        Imgproc.fillPoly(zeroColor,contours,Scalar.all(255),Imgproc.LINE_4);
        Imgproc.fillPoly(negativeColorSpace, contours, Scalar.all(0),Imgproc.LINE_4);


        if (copyOver) {

            onlyBlueimage = new Mat();

            originalImage.copyTo(onlyBlueimage, zeroColor);

            //m.setBlueImage(onlyBlueimage); //sets a colored image (seems to leave artifacts
        }
        else {
            onlyBlueimage = zeroColor;
            //m.setBlueImage(zeroColor); //sets a binary mask
        }

        //drawing box
        //https://docs.opencv.org/3.4/da/d0c/tutorial_bounding_rects_circles.html

        MatOfPoint2f mainContour;
        Rect boundRect;
        Point center = new Point();
        float[] radius = new float[1];


        mainContour = new MatOfPoint2f();
        if(contours.size() > 0) {

            Point[] biggestContour = contours.get(MaxIndex).toArray();
            Imgproc.approxPolyDP(new MatOfPoint2f(biggestContour), mainContour, 3, true);
            boundRect = Imgproc.boundingRect(new MatOfPoint((mainContour.toArray())));
            center = new Point();
            Imgproc.minEnclosingCircle(mainContour, center, radius);
            Imgproc.rectangle(onlyBlueimage, boundRect.tl(), boundRect.br(), new Scalar(32,246,0));

        }

        bluePoint = new AidansPoint(center.x,center.y);

        return onlyBlueimage;

    }

    private Mat scan_green(Mat coloredImage, Mat subtractingImage) {
        ///GREEN STUFF


        Mat blueSubtractedGreen = new Mat();
        Core.subtract(coloredImage, subtractingImage, blueSubtractedGreen);

        Mat thresh = new Mat();
        Imgproc.threshold(blueSubtractedGreen, thresh, greenThresholder.threshold, 255, Imgproc.THRESH_BINARY);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        //get contours and draw a box around them
        Imgproc.findContours(thresh, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        double MaxSize = 0; //size of largest pixels
        int MaxIndex = 0; //where is the group of largest pixels

        for (int i = 0; i < contours.size(); i++) { //got througth each group of pixels and find the largest one (probely the cup/wanted object)
            if (Imgproc.contourArea(contours.get(i)) > MaxSize) {
                MaxSize = Imgproc.contourArea(contours.get(i));
                MaxIndex = i;

            }
        }

        Mat zeroColor = Mat.zeros(originalImage.rows(), originalImage.cols(), CvType.CV_8UC1);
        //Mat negativeColorSpace = Mat.ones(originalImage.rows(), originalImage.cols(), CvType.CV_8UC1);


        Imgproc.drawContours(zeroColor, contours, MaxIndex, Scalar.all(255), 8, Imgproc.LINE_8, hierarchy);
        Imgproc.fillPoly(zeroColor,contours,Scalar.all(255),Imgproc.LINE_4);
        //Imgproc.fillPoly(negativeColorSpace, contours, Scalar.all(0),Imgproc.LINE_4);




        if (copyOver) {

            onlyGreenimage = new Mat();

            originalImage.copyTo(onlyGreenimage, zeroColor);

            //m.setGreenImage(onlyGreenimage); //sets a colored image (seems to leave artifacts
        }
        else {
            onlyGreenimage = zeroColor;
           // m.setGreenImage(zeroColor); //sets a binary mask
        }

        //drawing box
        //https://docs.opencv.org/3.4/da/d0c/tutorial_bounding_rects_circles.html

        MatOfPoint2f mainContour;
        Rect boundRect;
        Point center = new Point();
        float[] radius = new float[1];


        mainContour = new MatOfPoint2f();
        if(contours.size() > 0) {

            Point[] biggestContour = contours.get(MaxIndex).toArray();
            Imgproc.approxPolyDP(new MatOfPoint2f(biggestContour), mainContour, 3, true);
            boundRect = Imgproc.boundingRect(new MatOfPoint((mainContour.toArray())));
            center = new Point();
            Imgproc.minEnclosingCircle(mainContour, center, radius);
            Imgproc.rectangle(onlyGreenimage, boundRect.tl(), boundRect.br(), new Scalar(32,246,0));

        }

        greenPoint = new AidansPoint(center.x,center.y);


        return onlyGreenimage;
    }

    private Mat scan_custom() {
    Mat image = new Mat();

    onlyRedimage.copyTo(image);
    onlyBlueimage.copyTo(image);
    onlyGreenimage.copyTo(image);

    return image;

    }

    public void setCamera(VideoCapture camera) {
        this.camera = camera;
    }

    /*
    private void scan() throws InterruptedException {

        scan_1();

    Display m = Display.getInstance();

    //m.createFrame(m.Mat2BufferedImage(Imgcodecs.imread("C:/aidan/1Capture.PNG")));


        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String path = "C:\\Users\\s612902\\eclipse-workspace\\spotlight\\src\\How the Endocrine System Works.mp4";

        //VideoCapture camera = new VideoCapture(path);
        VideoCapture camera = new VideoCapture(0); ///actual camera
     //   camera = new VideoCapture(0,Videoio.CAP_DSHOW);
        //System.out.println(countFrames(camera));
        //System.out.println(camera.get(Videoio.CAP_PROP_FRAME_COUNT));

        //camera.open(1);


        long count = (long) camera.get(Videoio.CAP_PROP_FRAME_COUNT);

        while (camera.read(originalImage)) {
            //Thread.sleep((long) (((double) 1/6)*100));
            //m.setGreenImage((new Mat(originalImage.rows(),originalImage.cols(), CvType.CV_8UC3, new Scalar(0,0,0))));

            ArrayList<Mat> colors = new ArrayList<Mat>(3);

            Core.split(originalImage, colors);

            Mat blueImage = colors.get(0);
            Mat redImage = colors.get(2);
            Mat greenImage = colors.get(1);


///GREEN STUFF


            Mat blueSubtractedGreen = new Mat();
            Core.subtract(greenImage, blueImage, blueSubtractedGreen);

            Mat greenThresh = new Mat();
            Imgproc.threshold(blueSubtractedGreen, greenThresh, greenThresholder.threshold, 255, Imgproc.THRESH_BINARY);

            List<MatOfPoint> greenContours = new ArrayList<>();
            Mat greenHierarchy = new Mat();

            Imgproc.findContours(greenThresh, greenContours, greenHierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

            double greenMaxSize = 0; //size of largest pixels
            int greenMaxIndex = -1; //where is the group of largest pixels

            for (int i = 0; i < greenContours.size(); i++) { //got througth each group of pixels and find the largest one (probely the cup/wanted object)
                if (Imgproc.contourArea(greenContours.get(i)) > greenMaxSize) {
                    greenMaxSize = Imgproc.contourArea(greenContours.get(i));
                    greenMaxIndex = i;

                }
            }


/////blue STUFF
//
//            Mat blueSubtractedGreen = new Mat();
//            Core.subtract(greenImage,blueImage,blueSubtractedGreen);
//
//            Mat greenThresh = new Mat();
//            Imgproc.threshold(blueSubtractedGreen, greenThresh, 80, 255, Imgproc.THRESH_BINARY);
//
//            List<MatOfPoint> greenContours = new ArrayList<MatOfPoint>();
//            Mat greenHierarchy = new Mat();
//
//            Imgproc.findContours(greenThresh,greenContours, greenHierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
//
//            double greenMaxSize = 0; //size of largest pixels
//            int greenMaxIndex = -1; //where is the group of largest pixels
//
//            for(int i = 0; i < greenContours.size(); i++) { //got througth each group of pixels and find the largest one (probely the cup/wanted object)
//                if(Imgproc.contourArea(greenContours.get(i)) > greenMaxSize) {
//                    greenMaxSize = Imgproc.contourArea(greenContours.get(i));
//                    greenMaxIndex = i;
//
//                }
//            }


            Mat zeroGreen = Mat.zeros(originalImage.rows(), originalImage.cols(), CvType.CV_8UC1);
            Mat negativeGreenSpace = Mat.ones(originalImage.rows(), originalImage.cols(), CvType.CV_8UC1);



            Imgproc.drawContours(zeroGreen, greenContours, greenMaxIndex, Scalar.all(255), 8, Imgproc.LINE_8, greenHierarchy);
            Imgproc.fillPoly(zeroGreen,greenContours,Scalar.all(255),Imgproc.LINE_4);
            Imgproc.fillPoly(negativeGreenSpace, greenContours, Scalar.all(0),Imgproc.LINE_4);


            //Mat maskedGreen = new Mat();

            originalImage.copyTo(onlyGreenimage, zeroGreen);








            //m.setGreenImage(onlyGreenimage); //sets a colored image (seems to leave artifacts
            m.setGreenImage(zeroGreen,null); //sets a binary mask



            m.setOriginalImage(originalImage);

            m.updateFrame();




            if(count%100 == 10) System.out.println(count);

            if(count == 0) {
                camera.set(Videoio.CAP_PROP_POS_FRAMES, 0);
                count = (long) camera.get(Videoio.CAP_PROP_FRAME_COUNT);

                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            }

            count--;

            //notNeeded.Collector.getInstance().saveImage(notNeeded.Collector.IMAGES.GREEN, onlyGreenCup);
            //System.out.println("width, height, and value of slider: " + greenThresholder.getWidth() + ", " + greenThresholder.getHeight() + ", " + greenThresholder.getValue() + "\t count:" + count + "\ttotal frames: " + camera.get(Videoio.CAP_PROP_FRAME_COUNT) + "\t current frame " + camera.get(Videoio.CAP_PROP_POS_FRAMES) + "\t  ");

            originalImage = new Mat();
        }

        System.out.println("this is the end count: " + count);


    }
*/
    public Mat getOnlyGreenimage() {
        return onlyGreenimage;
    }

    private static ColorSelector instance = null;

    public static ColorSelector getInstance() {

        if (instance == null ) {
            instance = new ColorSelector();
        }

        return instance;
    }

    @Override
    public int getId() {
        return id;
    }

    public int countFrames(VideoCapture v) {

        int frames = 0;
        while (v.grab()) {
            frames++;
        }
        v.set(Videoio.CAP_PROP_POS_FRAMES, 0);

        return frames;
    }

    public static void checkCameras() {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        System.out.println("Hi");

        VideoCapture camera = new VideoCapture(0);
        System.out.println("done");
        VideoCapture camera2 = new VideoCapture(1);
        VideoCapture camera3 = new VideoCapture(2);
        VideoCapture camera4 = new VideoCapture(3); ///actual camera
        VideoCapture camera5 = new VideoCapture(4);

        System.out.println(camera.isOpened());
        System.out.println(camera2.isOpened());
        System.out.println(camera3.isOpened());
        System.out.println(camera4.isOpened());
        System.out.println(camera5.isOpened());


    }


}

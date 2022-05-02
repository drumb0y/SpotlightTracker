import notNeeded.Collector;
import notNeeded.Id;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Scanner;

public class testing  implements Id {

    static Collector collector = Collector.getInstance();

    public testing() {
        id = (int) (Math.random()*100000000);

    }

    public static void main(String[] args) throws InterruptedException {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Collector collector = Collector.getInstance();
        Display m;

        System.out.println("Stage 1");

        collector.create();

        System.out.println("stage 2");

        m = Display.getInstance();
        System.out.println(m.getId());



        Mat imageOne = Imgcodecs.imread("Capture.PNG");
        Mat imageTwo = Imgcodecs.imread("Capture.PNG");

        Scanner keyboard = new Scanner(System.in);

        int swatch = 1;

        m.createFrame(Options.Mat2BufferedImage(imageTwo));

        while (true) {
            Thread.sleep(500);

            switch (swatch%2) {
                case 0 :
                    m.setGreenImage(imageOne,null);
                    m.updateFrame();
                    break;

                case 1:
                    m.setGreenImage(imageTwo,null);
                    m.updateFrame();
                    break;
            }

            swatch++;


        }





//        while (true) {
//            m.addNum(keyboard.nextInt());
//            System.out.println("mainID is " + m.getId() + " at " + m);
//            System.out.println("collectorID is " + notNeeded.Collector.getInstance().getId());
//            System.out.println("TestingID is: " + testing.getInstance().getId());
//            System.out.println("Display from collector ID is: " + notNeeded.Collector.getInstance().getFromMap(notNeeded.Collector.TYPES.DISPLAY) + ", " + collector.getFromMap(notNeeded.Collector.TYPES.DISPLAY));
//        }


    }

    private static testing instance = null;

    public static testing getInstance() {

        if (instance == null ) {
            instance = new testing();
        }

        return instance;
    }

    @Override
    public int getId() {
        return id;
    }

    public static Collector getCollector() {
        return collector;
    }

    private int id;
}





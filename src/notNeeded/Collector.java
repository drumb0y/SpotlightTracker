package notNeeded;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.HashMap;
import java.util.Map;


public class Collector implements Id {


    @Override
    public int getId() {
        return id;
    }

    public enum TYPES {
        DISPLAY,
        TESTING,
        COLORSELECTOR


        
    }
    public enum IMAGES {
        GREEN,
        RED,
        BLUE
    }

    int id;

    public Collector() {
//        objectMap.put(TYPES.MAIN, (Display.getInstance()));
//        objectMap.put(TYPES.TESTING, testing.getInstance());
//        objectMap.put(TYPES.COLORSELECTOR,  ColorSelector.getInstance());


        id = (int) (Math.random()*100000000);

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void create() {
//        objectMap.put(TYPES.DISPLAY, (Display.getInstance()));
//        objectMap.put(TYPES.TESTING, testing.getInstance());
//        objectMap.put(TYPES.COLORSELECTOR,  ColorSelector.getInstance());
    }



    Map <TYPES, Object> objectMap = new HashMap<>();
    Map <IMAGES, Mat> imageMap = new HashMap<>();
     // Mat onlyGreenCup = null;

    public void saveImage(IMAGES i, Mat m) {
        imageMap.put(i, m);
    }

    public Mat receiveImage(IMAGES i) {
        return imageMap.get(i);
    }

    public boolean hasImage(IMAGES i) {
        return (i != null);
    }

    public void addToMap(TYPES key, Object obj) {
        objectMap.put(key, obj);
    }

    public Object getFromMap(TYPES key)  {

        if (objectMap.containsKey(key)) {
            return objectMap.get(key);
        }

        System.err.println("that item does not exist");
        return null;
    }

    private static Collector instance = null;

    public static Collector getInstance() {

        if (instance == null ) {
            instance = new Collector();
        }

        return instance;
    }






}

package customOpencvObjects;

import org.opencv.videoio.VideoCapture;

public class CustomVideoCapture extends VideoCapture {
    String NAME;


    public CustomVideoCapture(String name, String fileName) {
        super(fileName);
        NAME = name;
    }
    public CustomVideoCapture(String name, int index, int apiPreference) {
        super(index, apiPreference);
        NAME = name;
    }


    @Override
    public String toString() {
        return NAME;
    }
}

package customOpencvObjects;

import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class CustomVideoCapture extends VideoCapture {
    String NAME;
    String filename;

    int zoom;


    public CustomVideoCapture(String name, String fileName, int zoomFactor) {
        super(fileName);
        filename = fileName;
        NAME = name;
        zoom = zoomFactor;

    }
    public CustomVideoCapture(String name, int index, int apiPreference, int zoomFactor) {
        super(index, apiPreference);
        NAME = name;

        zoom = zoomFactor;

    }

    public int getZoom() {
        return zoom;
    }

    public void reset() {
        if (filename == null) {

        }
        else {
            set(Videoio.CAP_PROP_POS_FRAMES, 1);
        }

    }

    Position position;
    public void setPosition(int newX, int newY,int newZ) {
        position = new Position(newX, newY, newZ);

    }

    public void changeResolution(int width, int height) {

        set(Videoio.CAP_PROP_FRAME_WIDTH, width);
        System.out.println("changed width to " + width);
        set(Videoio.CAP_PROP_FRAME_HEIGHT, height);
        System.out.println("changed height to " + height);
        reset();
        System.out.println("reset");
    }


    @Override
    public String toString() {
        return NAME;
    }


}

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CameraDropDown extends JComboBox<VideoCapture> {

    String path = "Media/How the Endocrine System Works.mp4";
    VideoCapture[] cameras = {(new VideoCapture(path)),(new VideoCapture(0, Videoio.CAP_DSHOW))};

    public CameraDropDown() {
        super(Options.cameras);

    }

    public CameraDropDown(VideoCapture[] cameras) {
        super(cameras);

        setSelectedIndex(0);
        addActionListener(this);
    }

    /** Listens to the combo box. */
    public void actionPerformed(ActionEvent e) {
        JComboBox feed = (JComboBox)e.getSource();
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

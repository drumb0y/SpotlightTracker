import notNeeded.Id;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OptionsDropDown extends JComboBox<VideoCapture> {

    public OptionsDropDown(VideoCapture[] cameras) {
        super(cameras);

        setSelectedIndex(1);
        addActionListener(this);
    }

    /** Listens to the combo box. */
    public void actionPerformed(ActionEvent e) {
        JComboBox feed = (JComboBox)e.getSource();

        ColorSelector.getInstance().setCamera((VideoCapture) feed.getSelectedItem());


    }
}

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ThresholdingSlider extends JSlider {

    public int threshold = 80;

    public final Colors colorType;

    public enum Colors{
        GREEN,
        BLUE,
        RED
    }

    public ThresholdingSlider(Colors c) {
        super(JSlider.HORIZONTAL, 0, 255, 80);

        colorType = c;

        setMajorTickSpacing(10);
        setMinorTickSpacing(1);
       setPaintTicks(true);
        setPaintLabels(true);
        setFont(new Font("Serif", Font.ITALIC, 12));
    }

    public Colors getColorType() {
        return colorType;
    }

    public boolean sameColorType(Colors c) {
        return (colorType.equals(c));
    }
}

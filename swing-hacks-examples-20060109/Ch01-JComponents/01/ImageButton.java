import java.awt.*;
import javax.swing.*;

public class ImageButton extends JButton {

    public ImageButton(String img) {
        this(new ImageIcon(img));
    }

    public ImageButton(ImageIcon icon) {
        setIcon(icon);
        setMargin(new Insets(0,0,0,0));
        setIconTextGap(0);
        setBorderPainted(false);
        setBorder(null);
        setText(null);
        setSize(icon.getImage().getWidth(null),
                icon.getImage().getHeight(null));
    }

}

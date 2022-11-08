import javax.swing.*;
import java.awt.*;

public interface C {
    void move();

    void update();

    boolean collides(JButton circle, JButton rectangle);
}

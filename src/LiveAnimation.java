import javax.swing.*;

public interface LiveAnimation {
    void move();

    void update();

    boolean collides(JButton circle, JButton rectangle);
}

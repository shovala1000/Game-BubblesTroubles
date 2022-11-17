import javax.swing.*;
import java.awt.*;

public class Bullet extends AbstractLiveAnimation {
    private final int moveSpeedY;
    private final int x;
    public boolean isHit;
    public int bubbleHittenIndex;
    public static final int WIDTH = 3;
    public static final int HEIGHT = 10;

    public Bullet(int x, int y) {
        super();
        bubbleHittenIndex = -1;
        this.x = x;
        moveSpeedY = 15;
        isHit = false;
        jb = new JButton();
        jb.setBounds(x, y, WIDTH, HEIGHT);
        jb.setBackground(Color.BLACK);

        animationTimer = new Timer(REFRESH_TIME, e -> update());
        animationTimer.start();
    }

    @Override
    public void move() {
        if (!isHit) {
            jb.setSize(jb.getWidth(), jb.getHeight() + moveSpeedY);
            jb.setLocation(new Point(
                    x,
                    (int) jb.getLocation().getY() - moveSpeedY));
        }
    }

    @Override
    public void update() {
        Thread t = new Thread(()->{
                for (int i = 0; i < Game.bubblesListLen; ++i) {
                if (Game.bubblesList[i] != null) {
                    if (collides(Game.bubblesList[i].getJb(), jb)) {
                        bubbleHittenIndex = i;
                        isHit = true;
                        return;
                    }
                }
            }
            move();
            repaint();
        });
        t.start();
    }

    public JButton getJb() {
        return jb;
    }
}

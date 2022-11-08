import javax.swing.*;
import java.awt.*;

public class Bullet extends AbstractLiveAnimation {
    //    private static final int REFRESH_TIME = 150;
//    private final JButton jb;
    private final int moveSpeedY;
    private final int x;
    public boolean isHit;
    public int bubbleHittenIndex;
    public static final int WIDTH = 3;
    public static final int HEIGHT = 10;
//    public Timer animationTimer;

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
    }

    public JButton getJb() {
        return jb;
    }

//    private boolean collides(JButton circle, JButton rectangle) {
//        double temp, val1, val2;
//        // (x-a)^2 +(y-b)^2 = r^2
//        int r = circle.getWidth() / 2;
//        int a = circle.getLocation().x + circle.getWidth() / 2;
//        int b = circle.getLocation().y + circle.getWidth() / 2;
//        //rectangle ABCD: A(x1,y1) B(x2,y1) C(x2,y2) D(x1,y2)
//        int x1 = rectangle.getX(), y1 = rectangle.getY();
//        int x2 = x1 + rectangle.getWidth(), y2 = y1 + rectangle.getHeight();
//        //x = x1
//        temp = Math.sqrt(r * r - (x1 - a) * (x1 - a));
//        val1 = temp + b;
//        val2 = -temp + b;
//        if ((y1 <= val1 && val1 <= y2) || (y1 <= val2 && val2 <= y2)) {
//            return true;
//        }
//        //x = x2
//        temp = Math.sqrt(r * r - (x2 - a) * (x2 - a));
//        val1 = temp + b;
//        val2 = -temp + b;
//        if ((y1 <= val1 && val1 <= y2) || (y1 <= val2 && val2 <= y2)) {
//            return true;
//        }
//        // y=y1
//        temp = Math.sqrt(r * r - (y1 - b) * (y1 - b));
//        val1 = temp + a;
//        val2 = -temp + a;
//        return (x1 <= val1 && val1 <= x2) || (x1 <= val2 && val2 <= x2);
//    }
}

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.Point;

public class Bubble extends AbstractLiveAnimation {
//    private static final int REFRESH_TIME = 150;
//    private final JButton jb;
    private int moveSpeedX, moveSpeedY;
//    private final Timer animationTimer;
    public boolean isHit;
    public boolean isDead;



    public Bubble(int x, int y, int size, int dir, Color c) {
        super();
        isDead = false;
        moveSpeedX = 5 * dir;
        moveSpeedY = 5;
        isHit = false;
        super.jb = new JButton();
        jb.setBorder(new RoundedBorder());
        jb.setBounds(x, y, size, size);
        jb.setForeground(c);
        jb.setBackground(null);

        animationTimer = new Timer(REFRESH_TIME, e -> update());
        animationTimer.start();
    }

    @Override
    public void move() {
        if (!isHit) {
            if (jb.getLocation().getY() >= Game.HEIGHT - jb.getHeight() || jb.getLocation().getY() <= 0) {
                moveSpeedY = -moveSpeedY;
            }
            if (jb.getLocation().getX() >= Game.WIDTH - jb.getWidth() || jb.getLocation().getX() <= 0) {
                moveSpeedX = -moveSpeedX;
            }
            jb.setLocation(new Point(
                    (int) jb.getLocation().getX() + moveSpeedX,
                    (int) jb.getLocation().getY() + moveSpeedY));
        }

    }
    @Override
    public void update() {
        if (!isDead) {
            if (collides(jb, Game.shooter)) {
                isHit = true;
                Game.isLost = true;
            }
            move();
            repaint();
        } else animationTimer.stop();

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

    public JButton getJb() {
        return jb;
    }

    private static class RoundedBorder implements Border {


        public Insets getBorderInsets(Component c) {
            return new Insets(0, 0, 0, 0);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.fillOval(x, y, width - 1, height - 1);
        }
    }

}
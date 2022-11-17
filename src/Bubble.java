import javax.swing.*;
import java.awt.*;
import java.awt.Point;

public class Bubble extends AbstractLiveAnimation {
    private int moveSpeedX, moveSpeedY;
    public boolean isHit;
    public boolean isDead;

    public Bubble(int x, int y, int size, int dir, Color c) {
        super();
        isDead = false;
        moveSpeedX = 5 * dir;
        moveSpeedY = 5;
        isHit = false;
        super.jb = new CircleButton(x, y, size / 2, c);

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
        Thread t = new Thread(()->{
            if (!isDead) {
                int shooterWidth = Game.shooter.getWidth(), shooterHeight = Game.shooter.getHeight() / 2;
                JButton upGun = new JButton();
                JButton downGun = new JButton();
                upGun.setSize(new Dimension(shooterWidth / 2, shooterHeight));
                upGun.setLocation(Game.shooter.getLocation());
                downGun.setSize(new Dimension(shooterWidth, shooterHeight));
                downGun.setLocation((int) Game.shooter.getLocation().getX(),
                        (int) Game.shooter.getLocation().getY() + shooterHeight);
                if (collides(jb,upGun )||collides(jb,downGun)) {
                    isHit = true;
                    Game.isLost = true;
                }
                move();
                repaint();
            } else animationTimer.stop();
        });
        t.start();

    }

    public JButton getJb() {
        return jb;
    }

    private static class CircleButton extends JButton {
        private final int radius;
        private final Color color;

        public CircleButton(int x, int y, int radius, Color c) {
            super();
            this.radius = radius;
            this.color = c;
            int size = radius * 2;
            setBounds(x, y, size, size);
            setOpaque(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        public void paintComponent(Graphics g) {
            short epsi = 1;
            int size = radius * 2 - epsi;
            g.setColor(color);
            g.drawOval(getWidth() / 2 - radius, getHeight() / 2 - radius, size, size);
            g.fillOval(getWidth() / 2 - radius, getHeight() / 2 - radius, size, size);
        }
    }
}
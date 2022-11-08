import javax.swing.*;

public abstract class CC extends JPanel implements C {
    public static final int REFRESH_TIME = 150;
    protected JButton jb;
    protected Timer animationTimer;
    abstract public void move();

    abstract public void update();

    public boolean collides(JButton circle, JButton rectangle) {
        double temp, val1, val2;
        // (x-a)^2 +(y-b)^2 = r^2
        int r = circle.getWidth() / 2;
        int a = circle.getLocation().x + circle.getWidth() / 2;
        int b = circle.getLocation().y + circle.getWidth() / 2;
        //rectangle ABCD: A(x1,y1) B(x2,y1) C(x2,y2) D(x1,y2)
        int x1 = rectangle.getX(), y1 = rectangle.getY();
        int x2 = x1 + rectangle.getWidth(), y2 = y1 + rectangle.getHeight();
        //x = x1
        temp = Math.sqrt(r * r - (x1 - a) * (x1 - a));
        val1 = temp + b;
        val2 = -temp + b;
        if ((y1 <= val1 && val1 <= y2) || (y1 <= val2 && val2 <= y2)) {
            return true;
        }
        //x = x2
        temp = Math.sqrt(r * r - (x2 - a) * (x2 - a));
        val1 = temp + b;
        val2 = -temp + b;
        if ((y1 <= val1 && val1 <= y2) || (y1 <= val2 && val2 <= y2)) {
            return true;
        }
        // y=y1
        temp = Math.sqrt(r * r - (y1 - b) * (y1 - b));
        val1 = temp + a;
        val2 = -temp + a;
        return (x1 <= val1 && val1 <= x2) || (x1 <= val2 && val2 <= x2);
    }
}

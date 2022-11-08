
import javax.swing.*;
import java.awt.*;

public class Shooter extends JPanel {
    private final String shooterImage = "img/shooter.png";
    private Image shooterImg;
    private final JButton jb;

    public Shooter() {
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(shooterImage));
        Icon i = new ImageIcon(img);
        jb = new JButton(i);
        jb.setSize(20, 70);
        jb.setForeground(Color.orange);
        jb.setBackground(Color.orange);
        setImages();
    }

    //draw
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(shooterImg, 0, Game.HEIGHT - 100, null);

    }
    private void setImages() {
        shooterImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource(shooterImage));
        while (shooterImg.getHeight(null) == -1)
            System.out.println("class Canon: Waiting for image height...");

    }

    public JButton getJb() {
        return jb;
    }
}

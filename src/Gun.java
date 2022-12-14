
import javax.swing.*;
import java.awt.*;

public class Gun extends JPanel {
    private final String shooterImage = "img/shooter.png";
    private Image shooterImg;
    private final JButton jb;

    public Gun() {
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(shooterImage));
        Icon i = new ImageIcon(img);
        jb = new JButton(i);
        jb.setSize(20, 70);
        setImages();

        jb.setOpaque(false);
        jb.setFocusPainted(false);
        jb.setBorderPainted(false);
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
            System.out.println("class Gun: Waiting for image height...");

    }

    public JButton getJb() {
        return jb;
    }
}

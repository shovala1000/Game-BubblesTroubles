import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame implements KeyListener {
    private static final int MAX_BUBBLES = 16; //max bubbles that can stay alive when the first bubble is size 160
    private JLabel lblScore;
    private Bullet bullet;
    private int bubbleSize;
    private int score;
    private int level;
    private Color[] bubblesColors;
    public static final int HEIGHT = 400;
    public static final int WIDTH = 650;
    public static final String TITLE = "Bubbles Troubles";
    public static JButton shooter;
    public static Bubble[] bubblesList;
    public static int bubblesListLen;
    public static boolean isGameOver;

    public static boolean isLost;


    public Game() {
        super();
        bubblesColors = new Color[]{Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA};
        level = 3;
        score = 0;
        isGameOver = false;
        bubbleSize = 20;
        setTitle(TITLE);
        setVisible(true);
        addKeyListener(this);
        initGame(bubblesColors[level]);
    }


    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_RIGHT:
                if (shooter.getLocation().getX() + shooter.getWidth() < WIDTH)
                    shooter.setLocation(shooter.getX() + 5, shooter.getY());
                break;
            case KeyEvent.VK_LEFT:
                if (shooter.getLocation().getX() > 0)
                    shooter.setLocation(shooter.getX() - 5, shooter.getY());
                break;
            case KeyEvent.VK_UP:
                if (bullet != null) {
                    getContentPane().remove(bullet.getJb());
                }
                bullet = new Bullet(shooter.getX(), shooter.getY());
                getContentPane().add(bullet.getJb());
                break;
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    private class SurfaceScore extends JPanel {

        private static int score;

        public SurfaceScore(int s) {
            score = s;
            setBounds(0, 0, Game.WIDTH, 20);
            setLayout(null);
            setHeader();

        }

        private void doDrawing(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            rh.put(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            g2d.setRenderingHints(rh);
        }

        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            doDrawing(g);
        }

        private void setHeader() {
            JLabel scoreMsg = new JLabel("Your Score: ");
            scoreMsg.setForeground(Color.BLACK);


            lblScore = new JLabel(Integer.toString(score));
            lblScore.setForeground(Color.BLACK);


            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            headerPanel.setBounds(0, 0, Game.WIDTH, 20);
            headerPanel.setOpaque(true);
            headerPanel.setBackground(new Color(0, 0, 0, 0));

            headerPanel.add(scoreMsg);
            headerPanel.add(lblScore);
            add(headerPanel);
            headerPanel.setVisible(true);
        }

    }

    public void startGame() {
        Thread t = new Thread(() -> {
            while (!isGameOver) {
                if (bubblesListLen <= 0 || isLost) {
                    gameOver();
                } else if (bullet != null) {
                    if (bullet.getJb().getLocation().getY() < 0) {
                        getContentPane().remove(bullet.getJb());
                        bullet.animationTimer.stop();
                        bullet = null;
                    } else if (bullet.isHit && bubblesListLen < MAX_BUBBLES) {
                        hitBubbleHandler();
                    }
                }
                repaint();
            }


            dispose();
            System.exit(0);
        });
        t.start();
    }

    private void gameOver() {
        getContentPane().removeAll();
        int selectedOption;
        if (isLost) {
            selectedOption = JOptionPane.showConfirmDialog(this,
                    ("Your Score: " + score + "\nDid you enjoy the game?"),
                    "GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (selectedOption == JOptionPane.YES_OPTION) {
                System.out.println("User enjoy the game!");
            } else if (selectedOption == JOptionPane.NO_OPTION) {
                System.out.println("User did not enjoy the game!");
            }
            isGameOver = true;
        } else if (level >= 4) {
            selectedOption = JOptionPane.showConfirmDialog(this,
                    ("Congratulations you won all the levels!!!\nYour Score: " + score + "\nDid you enjoy the game?"),
                    "FINISHED ALL LEVEL", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (selectedOption == JOptionPane.YES_OPTION) {
                System.out.println("User enjoy the game!");
            } else if (selectedOption == JOptionPane.NO_OPTION) {
                System.out.println("User did not enjoy the game!");
            }
            isGameOver = true;
        } else {
            selectedOption = JOptionPane.showConfirmDialog(this,
                    ("Congratulations you won level " + level + " !!!\nYour Score: " + score + "\nDo you want to play Level " + (level + 1) + "?"),
                    "WINNING", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (selectedOption == JOptionPane.YES_OPTION) {
                bubbleSize = bubbleSize * 2;
                ++level;
                initGame(bubblesColors[level % (bubblesColors.length - 1)]);

            } else if (selectedOption == JOptionPane.NO_OPTION) {
                isGameOver = true;
            }
        }
    }

    private void initGame(Color bubbleColor) {
        isLost = false;
        SurfaceScore gamePanel = new SurfaceScore(score);
        add(gamePanel);
        gamePanel.setLayout(null);
        setContentPane(gamePanel);
        getContentPane().setBackground(Color.orange);
        getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));


        Shooter temp = new Shooter();
        shooter = temp.getJb();
        shooter.setLocation(WIDTH / 2, HEIGHT - 70);
        shooter.setBackground(Color.orange);

        getContentPane().add(shooter);


        bubblesListLen = 1;
        bubblesList = new Bubble[MAX_BUBBLES];
        for (int i = 0; i < bubblesListLen; ++i) {
            bubblesList[i] = new Bubble((WIDTH + bubbleSize) / 2, 10, bubbleSize, 1, bubbleColor);
            getContentPane().add(bubblesList[i].getJb());
        }

        bullet = null;
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

    }

    private void hitBubbleHandler() {
        score += 10;
        lblScore.setText(Integer.toString(score));
        int bubbleIndex = bullet.bubbleHittenIndex;
        int width = bubblesList[bubbleIndex].getJb().getWidth();
        if (width > 10) {
            int x = (int) bubblesList[bubbleIndex].getJb().getLocation().getX(), y = (int) bubblesList[bubbleIndex].getJb().getLocation().getY();


            Color color = bubblesColors[level % (bubblesColors.length - 1)];
            Bubble b1 = new Bubble(x + width / 2, y, width / 2, 1, color);
            Bubble b2 = new Bubble(x, y, width / 2, -1, color);


            getContentPane().add(b1.getJb());
            getContentPane().add(b2.getJb());


            ++bubblesListLen;

            getContentPane().remove(bubblesList[bubbleIndex].getJb());
            getContentPane().remove(bullet.getJb());
            bullet = null;

            bubblesList[bubbleIndex].isDead = true;
            bubblesList[bubbleIndex] = b1;
            bubblesList[bubblesListLen - 1] = b2;
        } else {
            getContentPane().remove(bubblesList[bubbleIndex].getJb());
            bubblesList[bubbleIndex].isDead = true;
            bubblesList[bubbleIndex] = bubblesList[bubblesListLen - 1];
            bubblesList[bubblesListLen - 1] = null;
            --bubblesListLen;
            getContentPane().remove(bullet.getJb());
            bullet = null;
        }

    }

}

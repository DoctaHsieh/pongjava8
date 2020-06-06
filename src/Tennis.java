import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Tennis extends Applet implements Runnable, KeyListener {
    final int WIDTH = 700, HEIGHT = 500;
    Thread thread;
    HumanPaddle p1;
    Ball b1;
    ComputerPaddle p2;
    boolean easy, normal, hard, gameStarted;
    Image img;
    Graphics gfx;

    public void init() {
        this.resize(WIDTH, HEIGHT);
        easy = false;
        normal = false;
        hard = false;
        gameStarted = false;
        this.addKeyListener(this);
        b1 = new Ball();
        p1 = new HumanPaddle(1);
        p2 = new ComputerPaddle(2, b1);
        img = createImage(WIDTH, HEIGHT);
        gfx = img.getGraphics();
        thread = new Thread(this);
        thread.start();
    }

    public void paint(Graphics g) {
        gfx.setColor(Color.black);
        gfx.fillRect(0, 0, WIDTH, HEIGHT);
        if(b1.getX() < -10 || b1.getX() > 710){
            gfx.setColor(Color.red);
            gfx.drawString("Game Over",300,250);
        }
        else {
            p1.draw(gfx);
            b1.draw(gfx);
            p2.draw(gfx);
        }

        if(!easy && !normal && !hard){
            gfx.setColor(Color.white);
            gfx.drawString("Ping Pong Ching chong", 340, 100);
            gfx.drawString("Press 1 for easy, 2 for normal, and 3 for hard.", 310, 130);
        }
        g.drawImage(img, 0, 0, this);
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void run() {
        for (;;) {
            if (gameStarted) {
                p1.move();
                if(easy){
                    p2.moveEasy();
                }
                if(normal){
                    p2.move();
                }
                if(hard) {
                    p2.moveHard();
                }
                b1.move();
                b1.checkPaddleCollision(p1, p2);
            }
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            p1.setUpAccel(true);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            p1.setDownAccel(true);
        }else if(e.getKeyCode() == KeyEvent.VK_1){
            easy = true;
            gameStarted = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_2){
            normal = true;
            gameStarted = true;
        }
        else if( e.getKeyCode() == KeyEvent.VK_3){
            hard = true;
            gameStarted = true;
        }
    }


    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            p1.setUpAccel(false);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            p1.setDownAccel(false);
        }
    }

    public void keyTyped(KeyEvent arg0) {

    }
}



import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;


public class Tennis extends Applet implements Runnable, KeyListener {
    final int WIDTH = 700, HEIGHT = 500;
    Thread thread;
    HumanPaddle p1;
    Ball b1;
    ComputerPaddle p2;
    boolean easy, normal, hard, gameStarted;
    Image img;
    Graphics gfx;
    private long startTime = 0;
    private long gameTime = 0;
    private boolean running = false;
    long elapsed;



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
        elapsed = gameTime  - startTime ;
        // Out of bound - Game will end
        if(b1.getX() < -10 ){
            gfx.setColor(Color.red);
            gfx.setFont(new Font("Arial", 1, 25));
            gameEnded();
            gfx.drawString("Game Over. You survived for " +elapsed +" seconds." ,300,250);
            System.out.println(gameTime);
            System.out.println(elapsed);
        }
        else {
            gameTime = System.currentTimeMillis() / 1000;
            gfx.drawString("", 300, 100);
            p1.draw(gfx);
            b1.draw(gfx);
            p2.draw(gfx);
        }

        if(!easy && !normal && !hard){
            gfx.setColor(Color.white);
            gfx.drawString("Ping Pong Ching Chong", 300, 100);
            gfx.drawString("Press 1 to start easy. After 30 seconds: normal. 60 seconds: hard, where you will try to survive for as long as possible.", 25, 130);
        }
        if(easy && !normal && !hard){
            gfx.setColor(Color.white);
            gfx.drawString("Level: Easy.", 10, 20);
        }
        if(normal && !easy && !hard){
            gfx.setColor(Color.white);
            gfx.drawString("Level: Normal.", 10, 20);
        }
        if(hard && !normal && !easy){
            gfx.setColor(Color.white);
            gfx.drawString("Level: Hard.", 10, 20);
        }
        if(b1.getX() < -10 ){
            gfx.setColor(Color.red);
            gfx.setFont(new Font("Arial", 1, 25));
            gameEnded();
            gfx.drawString("Game Over. You survived for " +elapsed +" seconds." ,300,250);
            System.out.println(gameTime);
            System.out.println(elapsed);
        }
        g.drawImage(img, 0, 0, this);
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void run() {
        for (;;) {
            if (gameStarted) {
                if ((System.currentTimeMillis()/1000)-startTime > 30&& (System.currentTimeMillis()/1000)-startTime < 60){
                    changeToNormal();
                }
                if ((System.currentTimeMillis()/1000)-startTime > 60){
                   changeToHard();

                }
                p1.move();
                p2.move();
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
            this.startTime = System.currentTimeMillis() / 1000;
            this.running = true;
            b1.SetEasySpeed();
            System.out.println(startTime);
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

    public long getElapsedTimeSecs() {
        if (running) {
            try{
                thread.sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return elapsed;
    }
    public void normal(){
        normal = true;
        gameStarted = true;
        b1.SetNormalSpeed();
    }

    public void changeToNormal(){
        normal = true;
        easy = false;
        b1.SetNormalSpeed();

    }
    public void changeToHard(){
        hard = true;
        easy = false;
        normal = false;
        b1.SetHardSpeed();

    }
    public void gameEnded(){
        gameStarted = false;
        this.running = false;
    }
}





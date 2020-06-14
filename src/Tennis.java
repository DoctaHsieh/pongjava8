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

    boolean easy, normal, hard;
    boolean gameStarted, gameEnded;
    private long gameTime = 0;

    // Control the time of each level
    static final int EASY_TIME_SEC = 30;
    static final int NORMAL_TIME_SEC = 60;

    public int easyscore, normalscore, hardscore, totalscore;
    Image img;
    Graphics gfx;
    private long startTime = 0;
    //gametime = how much time in seconds the player spend in the current game.

    private boolean running = false;
    long elapsed;



    public void init() {
        this.resize(WIDTH, HEIGHT);
        easy = false;
        normal = false;
        hard = false;
        gameStarted = false;
        gameEnded = false;
        this.addKeyListener(this);
        b1 = new Ball();
        p1 = new HumanPaddle(1);
        p2 = new ComputerPaddle(2, b1);
        img = createImage(WIDTH, HEIGHT);
        gfx = img.getGraphics();
        thread = new Thread(this);
        thread.start();

    }

    // logic to display the game
    public void paint(Graphics g) {
        gfx.setColor(Color.black);
        gfx.fillRect(0, 0, WIDTH, HEIGHT);

        elapsed = gameTime  - startTime ;
        easyscore = (int) elapsed;
        // Out of bound - Game will end
        if(gameEnded){
            gfx.setColor(Color.red);

            gfx.drawString("Game Over. Your score was " +totalscore +"." ,300,250);

        }
        else {
            gameTime = System.currentTimeMillis()/1000;
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
        g.drawImage(img, 0, 0, this);
    }

    public void update(Graphics g) {
        paint(g);
    }

    // Logic to control the game
    public void run() {
        for (;;) {
            if (gameStarted) {
                // check if ball goes out of bound
                if(b1.getX() < -10 ){
                    //easyEnded();
                    gameEnded();
                    if(easy){
                        totalscore = easyscore;
                    }
                    if(normal ){
                        normalscore = (int) ((elapsed - EASY_TIME_SEC) * 2);
                        totalscore = easyscore + normalscore;
                        easyscore = EASY_TIME_SEC;
                    }
                    if(hard){
                        hardscore = (int)((elapsed - (NORMAL_TIME_SEC)) * 3);
                        easyscore = EASY_TIME_SEC;
                        normalscore = NORMAL_TIME_SEC * 2;
                    }
                    totalscore = easyscore+ normalscore + hardscore;
                }
                else{
                    gameTime = System.currentTimeMillis() / 1000;
                }
                // Calculate which level we are in
                if ((System.currentTimeMillis()/1000)-startTime > EASY_TIME_SEC && (System.currentTimeMillis()/1000)-startTime < NORMAL_TIME_SEC){
                    changeToNormal();
                }
                if ((System.currentTimeMillis()/1000)-startTime > NORMAL_TIME_SEC){
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
        gameEnded = true;
    }
    //Checks if easy mode has ended and punches a time if it has.

}





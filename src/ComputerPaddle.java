import java.awt.*;
import java.awt.Color;

public class ComputerPaddle implements Paddle {
    double y, yVel;
    boolean upAccel, downAccel;
    final double GRAVITY = 0.94;
    int player, x;
    Ball b1;

    public ComputerPaddle(int player, Ball b) {
        upAccel = false; downAccel = false;
        b1 = b;
        y = 210; yVel = 0;
        if (player == 1) {
            x = 20;
        }
        else {
            x = 660;
        }
    }


    public void draw(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(x, (int) y, 20, 80);

    }



    public void move() {
        y = b1.getY() - 40;

        if (y < 0)
            y = 0;

        if (y > 420)
            y = 420;

    }


    public int getY() {
        return (int)y;
    }
}
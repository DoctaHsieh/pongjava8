import com.sun.deploy.security.SelectableSecurityManager;

import java.awt.*;
import java.util.Random;

public class Ball {

    double  x, y;
    private double yVel, xVel;
    double Speed, normalSpeed, hardSpeed;





    public Ball(){
        xVel = 0;
        yVel = 0;
        x = 350;
        y = 250;
        Speed = 0;


    }



    public void SetSpeed(){
        Speed++;
        int xDir = 1;

        int yDir = 1;
        if (xVel!= 0){
            xDir = (xVel > 0) ? 1 : -1;
        }
        if (yVel!= 0){
            yDir = (yVel > 0) ? 1 : -1;
        }
        xVel = Speed * xDir;
        yVel = Speed * yDir;


    }
    private Color randomColor(){
        Random random = new Random();
        int R = random.nextInt(256);
        int G = random.nextInt(256);
        int B = random.nextInt(256);
        return new Color(R,B,G);

    }





    public void draw(Graphics g) {
        g.setColor(randomColor());
        g.fillOval((int) x - 10, (int) y - 10, 20, 20);
    }

    public void checkPaddleCollision(Paddle p1, Paddle p2){
        if(x < 50) {
            if(y > p1.getY() && y < p1.getY() +80)
                xVel = -xVel;
        }
        else if(x > 650){
            if(y > p2.getY() && y < p2.getY() + 80)
                xVel = -xVel;
        }
    }

    public void move() {
        x += xVel;
        y += yVel;

        if(y < 10) {
            yVel = -yVel;


        }
        if (y > 490) {
            yVel = -yVel;

        }
    }

    public int getX(){
        return(int) x;
    }
    public int getY(){
        return(int)y;
    }
    public int getXVel() {
        return (int) xVel;
    }
    public int getYVel() {
        return (int) yVel;
    }
    public void setXVel(int xV) {
        xVel = xV;
    }
    public void setYVel(int yV) {
        yVel = yV;
    }

    public int getXDir(){
        if (xVel > 0) {
            return 1;
        } else {
            return -1;
        }
    }
    public int getYDir(){
        if (yVel > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}


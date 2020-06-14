import com.sun.deploy.security.SelectableSecurityManager;

import java.awt.*;

public class Ball {
    double  x, y;
    private double yVel, xVel;
    double easySpeed, normalSpeed, hardSpeed;

    public Ball(){
        xVel = 0;
        yVel = 0;
        x = 350;
        y = 250;
        easySpeed = 1;
        normalSpeed = 2;
        hardSpeed = 3;
    }



    public void SetEasySpeed(){
        int xDir = 1;
        int yDir = 1;
        if (xVel!= 0){
            xDir = (xVel > 0) ? 1 : -1;
        }
        if (yVel!= 0){
            yDir = (yVel > 0) ? 1 : -1;
        }
        xVel = easySpeed * xDir; //* getRandomDirection();
        yVel = easySpeed * yDir;// * getRandomDirection();
    }
    public void SetNormalSpeed(){
        int xDir = 1;
        int yDir = 1;
        if (xVel!= 0){
            xDir = (xVel > 0) ? 1 : -1;
        }
        if (yVel!= 0){
            yDir = (yVel > 0) ? 1 : -1;
        }
        xVel = normalSpeed * xDir; //* getRandomDirection();
        yVel = normalSpeed * yDir;// * getRandomDirection();
    }
    public void SetHardSpeed(){
        int xDir = 1;
        int yDir = 1;
        if (xVel!= 0){
            xDir = (xVel > 0) ? 1 : -1;
        }
        if (yVel!= 0){
            yDir = (yVel > 0) ? 1 : -1;
        }
        xVel = hardSpeed * xDir;// * getRandomDirection();
        yVel = hardSpeed * yDir;// * getRandomDirection();
    }

   // public int getRandomDirection(){
       // int rand = (int)(Math.random() * 2);
       // if (rand ==1)
            //return 1;
        //else
            //return -1;
    //}


    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval((int) x - 10, (int) y - 10, 20, 20);
    }

    public void checkPaddleCollision(Paddle p1, Paddle p2){
        if(x <= 50) {
            if(y >= p1.getY() && y <= p1.getY() +80)
                xVel = -xVel;
        }
        else if(x >= 650){
            if(y >= p2.getY() && y <= p2.getY() + 80)
                xVel = -xVel;
        }
    }

    public void move() {
        x += xVel;
        y += yVel;

        if(y <= 10) {
            yVel = -yVel;


        }
        if (y >= 490) {
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


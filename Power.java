/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dxball;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author shash
 */
public class Power {
    //private Rectangle r1;
    
    
    public Rectangle big(Rectangle r1){
        r1.setWidth(300);
       // r1.setTranslateX(r1.getTranslateX()-50);
        return r1;
    }
    
    public Rectangle small(Rectangle r1){
        r1.setWidth(100);
        return r1;
    }
    
    public double slowBall(double ballSpeed){
        ballSpeed = ballSpeed/2;
       return ballSpeed;    
    }
    
    public double fastBall(double ballSpeed){
       return ballSpeed*2;    
    }
    
    
}

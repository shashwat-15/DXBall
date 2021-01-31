/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dxball;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author shash
 */
public class Ball extends Components{
    private double radius;

    public Ball(double radius, double x, double y, Color color) {
        super(x, y, color);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
       gc.setFill(super.getColor());
       gc.fillOval(super.getX(), super.getY(), radius*2, radius*2);
    }
    
    
}

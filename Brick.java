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
public class Brick extends Components {
    private double width, height;

    public Brick(double width, double height, double x, double y, Color color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(super.getColor());
        gc.fillRect(super.getX(), super.getY(), width, height);
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s+"\nBrick{" + "width=" + width + ", height=" + height + '}';
    }
    
    
}

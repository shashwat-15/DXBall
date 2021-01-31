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
public class Paddle extends Components {
    private double width,height,arcW,arcH;

    public Paddle(double width, double height, double arcW, double arcH, double x, double y, Color color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
        this.arcW = arcW;
        this.arcH = arcH;
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

    public double getArcW() {
        return arcW;
    }

    public void setArcW(double arcW) {
        this.arcW = arcW;
    }

    public double getArcH() {
        return arcH;
    }

    public void setArcH(double arcH) {
        this.arcH = arcH;
    }
    
    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(super.getColor());
        gc.fillRoundRect(super.getX(), super.getY(), width, height, arcW, arcH);
    }
}

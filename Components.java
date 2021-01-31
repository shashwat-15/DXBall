/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dxball;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author shash
 */
public abstract class Components {
    private double x, y;
    private Color color;

    public Components(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    
    
    public abstract void draw(GraphicsContext gc);

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Components{" + "x=" + x + ", y=" + y + ", color=" + color + '}';
    }
    
}

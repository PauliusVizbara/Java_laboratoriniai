/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1_B;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import studijosKTU.ScreenKTU;
import java.util.Random;

/**
 *
 * @author Paulius
 */
public class Disk {

    private Color color;
    private int positionX;
    private int positionY;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    private int width;
    private int height;

    public Disk(int positionX, int positionY, int width) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = 3;
        Random random = new Random();
        final float hue = random.nextFloat();
        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
        final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
        color = Color.getHSBColor(hue, saturation, luminance);

    }

    public Disk(int width) {
        this.width = width;
        this.positionX = 0;
        this.positionY = 0;
        this.height = 3;
        this.height = 3;
        Random random = new Random();
        final float hue = random.nextFloat();
        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
        final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
        color = Color.getHSBColor(hue, saturation, luminance);
    }

}

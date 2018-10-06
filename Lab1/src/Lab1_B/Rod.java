/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1_B;

import java.util.ArrayList;

/**
 *
 * @author Paulius
 */
public class Rod {
    
    
    public Rod(int centreX, boolean isCentre) {
        this.centreX = centreX;
        this.disks = new ArrayList<>();
        this.currentDiskHeight = 117;
        this.isCentre = isCentre;
    }
    public boolean isCentre;
    public int centreX;
    public ArrayList<Disk> disks;
    public int currentDiskHeight;
    int topLeftPointX, topLeftPointY, bottomRightPointX, bottomRightPointY;
    

    public void setBounds(int topLeftPointX, int topLeftPointY, int bottomRightPointX, int bottomRightPointY) {
        this.topLeftPointX = topLeftPointX;
        this.topLeftPointY = topLeftPointY;
        this.bottomRightPointX = bottomRightPointX;
        this.bottomRightPointY = bottomRightPointY;
    }
    
    public boolean hasBeenPressed(int mouseX, int mouseY){
        if ( mouseX > topLeftPointX && mouseX < bottomRightPointX && mouseY > topLeftPointY && mouseY < bottomRightPointY)
            return true;
        
        return false;
    }
    
    

    
}

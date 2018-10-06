/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1_A;

import java.util.List;

/**
 *
 * @author Paulius
 */
public class main {

    public static void main(String[] args) {
        
        TaskData taskData = FileHandler.readTaskData("data2");
        List<Defibrillator> defibs = taskData.getDefibrillators();
        float userLatitude = taskData.getUserLatitude();
        float userLongtitude = taskData.getUserLongitude();
        
        Defibrillator shortestDefibrillator = null;
        double minimumDistance = 9999;
        
        for (int i = 0; i < defibs.size(); i++) {
            Defibrillator currentDefibrillator = defibs.get(i);
            double distance = Calculator.distanceBetweenPoints(userLatitude, userLongtitude, 
                    currentDefibrillator.latitude, currentDefibrillator.longitude );
            if ( distance < minimumDistance){
                minimumDistance = distance;
                shortestDefibrillator = currentDefibrillator;
            }
        }
        
        System.out.println( shortestDefibrillator.name);
        
    }
}

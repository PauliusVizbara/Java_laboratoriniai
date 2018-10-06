/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1_A;

/**
 *
 * @author Paulius
 */
public final class Calculator {
    public static double distanceBetweenPoints(float lat1, float long1, float lat2, float long2){
        double x = ( (double)long2 - (double)long1) * Math.cos(((double)lat1+(double)lat2)/2);
        double y = lat2 - lat1;
        double distance = Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2)) * 6371;
        return distance;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1_A;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulius
 */
public class TaskData {


    
    private float userLongitude;
    private float userLatitude;
    private int defibCount;
    private List<Defibrillator> defibrillators;
    
    public TaskData(float userLongitude, float userLatitude, int defibCount) {
        this.userLongitude = userLongitude;
        this.userLatitude = userLatitude;
        this.defibCount = defibCount;
        this.defibrillators = new ArrayList<>();
    }
    
        public float getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(float userLongitude) {
        this.userLongitude = userLongitude;
    }

    public float getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(float userLatitude) {
        this.userLatitude = userLatitude;
    }

    public int getDefibCount() {
        return defibCount;
    }

    public void setDefibCount(int defibCount) {
        this.defibCount = defibCount;
    }

    public List<Defibrillator> getDefibrillators() {
        return defibrillators;
    }

    public void setDefibrillators(List<Defibrillator> defibrillators) {
        this.defibrillators = defibrillators;
    }
    
}

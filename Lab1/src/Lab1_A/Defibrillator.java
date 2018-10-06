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
public class Defibrillator {
    
    int identifyingNumber;
    String name;
    String address;
    String phoneNumber;
    float longitude;
    float latitude;

    
    public Defibrillator(int identifyingNumber, String name, String address, String phoneNumber, float longitude, float latitude) {
        this.identifyingNumber = identifyingNumber;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
}

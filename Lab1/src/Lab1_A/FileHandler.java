/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1_A;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulius
 */
public final class FileHandler {

    public static TaskData readTaskData(String filename) {

        float userLongitude;
        float userLatitude;
        int defibCount;

        FileReader fr = null;
        try {
            fr = new FileReader("duomenys/" + filename + ".txt");

        } catch (FileNotFoundException e) {
            System.err.println(filename + ".txt not found");
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(fr);
        String line;

        try {

            userLongitude = Float.parseFloat(reader.readLine());
            userLatitude = Float.parseFloat(reader.readLine());
            defibCount = Integer.parseInt(reader.readLine());
            TaskData taskData = new TaskData(userLongitude, userLatitude, defibCount);
            List<Defibrillator> tempDefibList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] currentLine = line.split(";");
                int identifyingNumber;
                String name;
                String address;
                String phoneNumber;
                float longitude;
                float latitude;

                identifyingNumber = Integer.parseInt(currentLine[0]);
                name = currentLine[1];
                address = currentLine[2];
                phoneNumber = currentLine[3];
                longitude = Float.parseFloat(currentLine[4]);
                latitude = Float.parseFloat(currentLine[5]);

                Defibrillator tempDefib = new Defibrillator(identifyingNumber, name, address, phoneNumber, longitude, latitude);
                tempDefibList.add(tempDefib);
                    
            }
            taskData.setDefibrillators(tempDefibList);
            return taskData;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

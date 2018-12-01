/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mano;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import laborai.gui.MyException;

/**
 *
 * @author Paulius
 */
public class NonPlayAbleCharacterGenerator {
    private static final String ID_CODE = "NPC";      //  ***** nauja
    private static int serNr = 10000;               //  ***** nauja
    private int currID ;   
    private NonPlayableCharacter[] npcs;
    private String[] raktai;
    private int kiekis = 0, idKiekis = 0;
    
    
    public static NonPlayableCharacter[] genereateNPCS(int kiekis){
        NonPlayableCharacter[] npcs = new NonPlayableCharacter[kiekis];
        for (int i = 0; i < kiekis; i++) {
            npcs[i] = new NonPlayableCharacter("Knight", 5, 10, 10, true);
        }
        return npcs;
    }
     public static String[] generateNPCIds(int kiekis) { 
        
        String[] raktai = IntStream.range(0, kiekis)
                .mapToObj(i -> ID_CODE + (serNr++))
                .toArray(String[]::new);
        Collections.shuffle(Arrays.asList(raktai));
        return raktai;
    }
     
     public void setNPCS(int kiekis){
         raktai = generateNPCIds(kiekis);
         npcs = genereateNPCS(kiekis);
     }
    public String getNpcId() {
        
        if (idKiekis >= raktai.length) {
            idKiekis = 0;
        }
        return raktai[idKiekis++];
    }
}

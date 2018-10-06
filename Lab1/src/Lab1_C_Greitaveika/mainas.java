/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1_C_Greitaveika;

import studijosKTU.Timekeeper;

/**
 *
 * @author Paulius
 */
public class mainas {

    public static void main(String[] args) {
        int[] tiriamiKiekiai = {2_000, 4_000, 8_000, 16_000};
        Timekeeper tk = new Timekeeper(tiriamiKiekiai);
        double bandymuKiekis = 100000;
        tk.start();
        
        //System.out.println ( Math.sin(1));
        
        
         for (double i = 0; i < bandymuKiekis; i++) {
            Math.sqrt(i);
        }
        tk.finish("Saknis");
        
         for (double i = 0; i < bandymuKiekis; i++) {
            Math.sin(i);
        }
        
        tk.finish("Sinusas");
        
        tk.seriesFinish();
    }
    
        
}

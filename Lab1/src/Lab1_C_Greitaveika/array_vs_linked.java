/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1_C_Greitaveika;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import studijosKTU.Timekeeper;

/**
 *
 * @author Paulius
 */
public class array_vs_linked {

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList();
        LinkedList<Integer> linkedList = new LinkedList();

        int[] tiriamiKiekiai = {2_000, 4_000, 8_000, 16_000};
        Timekeeper tk = new Timekeeper(tiriamiKiekiai);
        double bandymuKiekis = 1000;

        ArrayList<Integer> randomInts = new ArrayList();

        Random random = new Random();
        for (int kiekis : tiriamiKiekiai) {

            for (int i = 0; i < bandymuKiekis; i++) {

                int randomInt = random.nextInt(1000) + 1;
                randomInts.add(randomInt);
            }

            tk.start();

            for (int i = 0; i < bandymuKiekis; i++) {
                arrayList.add(arrayList.size() / 2, randomInts.get(i));
            }

            tk.finish("Array");

            for (int i = 0; i < bandymuKiekis; i++) {
                linkedList.add(linkedList.size() / 2, randomInts.get(i));
            }

            tk.finish("Linked");

            tk.seriesFinish();
        }

    }

}

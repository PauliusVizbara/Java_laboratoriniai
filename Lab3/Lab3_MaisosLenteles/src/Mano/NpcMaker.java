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
public class NpcMaker {
        private static final String ID_CODE = "TA";      //  ***** nauja
    private static int serNr = 10000;               //  ***** nauja

    private NonPlayableCharacter[] npcs;
    private String[] raktai;
    private int kiekis = 0, idKiekis = 0;

    public static NonPlayableCharacter[] makeNpcs(int kiekis) {
        NonPlayableCharacter[] automobiliai = IntStream.range(0, kiekis)
                .mapToObj(i -> new NonPlayableCharacter.Builder().buildRandom())
                .toArray(NonPlayableCharacter[]::new);
        Collections.shuffle(Arrays.asList(automobiliai));
        return automobiliai;
    }

    public static String[] makeNpcIds(int kiekis) {
        String[] raktai = IntStream.range(0, kiekis)
                .mapToObj(i -> ID_CODE + (serNr++))
                .toArray(String[]::new);
        Collections.shuffle(Arrays.asList(raktai));
        return raktai;
    }

    public NonPlayableCharacter[] makeAndSetNpcs(int aibesDydis,
            int aibesImtis) throws MyException {
        if (aibesImtis > aibesDydis) {
            aibesImtis = aibesDydis;
        }
        npcs = makeNpcs(aibesDydis);
        raktai = makeNpcIds(aibesDydis);
        this.kiekis = aibesImtis;
        return Arrays.copyOf(npcs, aibesImtis);
    }

    // Imamas po vienas elementas iš sugeneruoto masyvo. Kai elementai baigiasi sugeneruojama
    // nuosava situacija ir išmetamas pranešimas.
    public NonPlayableCharacter setNpc() {
        if (npcs == null) {
            throw new MyException("carsNotGenerated");
        }
        if (kiekis < npcs.length) {
            return npcs[kiekis++];
        } else {
            throw new MyException("allSetStoredToMap");
        }
    }

    public String getIdFromBase() {
        if (raktai == null) {
            throw new MyException("carsIdsNotGenerated");
        }
        if (idKiekis >= raktai.length) {
            idKiekis = 0;
        }
        return raktai[idKiekis++];
    }
}

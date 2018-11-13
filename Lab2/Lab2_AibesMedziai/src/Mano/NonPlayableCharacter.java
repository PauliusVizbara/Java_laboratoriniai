/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mano;
import laborai.studijosktu.KTUable;
import laborai.studijosktu.Ks;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Paulius
 */
public class NonPlayableCharacter implements KTUable<NonPlayableCharacter> {

    private String name;
    private double damage;
    private double armor;
    private double hp;
    private boolean isFriendly;

    public NonPlayableCharacter() {

    }
    
    public NonPlayableCharacter(String name, double damage, double armor, double hp, boolean isFriendly) {
        this.name = name;
        this.damage = damage;
        this.armor = armor;
        this.hp = hp;
        this.isFriendly = isFriendly;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isIsFriendly() {
        return isFriendly;
    }

    public void setIsFriendly(boolean isFriendly) {
        this.isFriendly = isFriendly;
    }
    
    @Override
    public int compareTo(NonPlayableCharacter other) {
        if ( hp > other.hp) return 1;
        else if ( hp == other.hp) return 0;
        else return -1;
    }

    @Override
    public KTUable create(String dataString) {
        NonPlayableCharacter a = new NonPlayableCharacter();
        a.parse(dataString);
        return a;
    }

    @Override
    public String toString() {
        return hp + ":" + name;
    }

    @Override
    public final void parse(String dataString) {
        try {   // ed - tai elementarūs duomenys, atskirti tarpais
            Scanner ed = new Scanner(dataString);
            name = ed.next();
            damage = ed.nextDouble();
            armor = ed.nextDouble();
            hp = ed.nextInt();
            isFriendly = ed.nextBoolean();
        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas apie NPC -> " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų apie NPC -> " + dataString);
        }
    }

    @Override
    public String validate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mano;
import laborai.studijosktu.KTUable;
import laborai.studijosktu.Ks;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Paulius
 */
public final class NonPlayableCharacter implements KTUable {

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

    private NonPlayableCharacter(Builder aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public int hashCode() {
        return Objects.hash(name, damage, hp, armor, isFriendly);
    }
    
    
    
@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NonPlayableCharacter other = (NonPlayableCharacter) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.damage, other.damage)) {
            return false;
        }
        if (this.hp != other.hp) {
            return false;
        }
        if (this.armor != other.armor) {
            return false;
        }
        if (this.isFriendly != other.isFriendly) {
            return false;
        }
       

        return true;
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

        // Automobilis klases objektų gamintojas
    public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Atsitiktinių generatorius
        private final static String[][] MODELIAI = { // galimų automobilių markių ir jų modelių masyvas
            {"Knight", "Tank", "Mage", "Assassin", "Summoner","Archer","Builder"},
        };

        private String markė = "";
        private String modelis = "";
        private int gamMetai = -1;
        private int rida = -1;
        private double kaina = -1.0;

        public NonPlayableCharacter build() {
            return new NonPlayableCharacter(this);
        }

        public NonPlayableCharacter buildRandom() {
            int ma = RANDOM.nextInt(MODELIAI.length);        // markės indeksas  0..
            int mo = RANDOM.nextInt(MODELIAI[ma].length - 1) + 1;// modelio indeksas 1..              
            return new NonPlayableCharacter(MODELIAI[ma][0],
                    RANDOM.nextInt(100)+1,// metai tarp 1990 ir 2009
                    RANDOM.nextInt(100)+1,// metai tarp 1990 ir 2009
                    RANDOM.nextInt(100)+1,// rida tarp 6000 ir 228000
                    RANDOM.nextBoolean());// kaina tarp 800 ir 88800
        }

       /* public Builder gamMetai(int gamMetai) {
            this.gamMetai = gamMetai;
            return this;
        }

        public Builder markė(String markė) {
            this.markė = markė;
            return this;
        }

        public Builder modelis(String modelis) {
            this.modelis = modelis;
            return this;
        }

        public Builder rida(int rida) {
            this.rida = rida;
            return this;
        }

        public Builder kaina(double kaina) {
            this.kaina = kaina;
            return this;
        }*/
    }
}
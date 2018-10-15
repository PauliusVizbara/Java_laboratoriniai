/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1_C;
import studijosKTU.*;

/**
 *
 * @author Paulius
 */
public class Mainas {
    
   
    public static void main(String[] args){
        
        CustomList<NonPlayableCharacter> playerFaction = new CustomList<>();
        NonPlayableCharacter a3 = new NonPlayableCharacter("NAUJAS",20,10,150,true);
        NonPlayableCharacter basicTank = new NonPlayableCharacter("Tank1",5,10,300,true);
        NonPlayableCharacter basicArcher = new NonPlayableCharacter("Archer1",10,2,100,true);
        addInfoToList(playerFaction);
        
        
        //playerFaction.add(0, new NPC());
        
        playerFaction.printList();
          System.out.println("=======================");
      
        /*playerFaction.add(6, a3);
        playerFaction.printList();*/
       
        /*playerFaction.remove(1);
        playerFaction.printList();*/
        
        /*playerFaction.set(5, a3);
        playerFaction.printList();*/
        
        //System.out.println(playerFaction.removeFirst().toString() );
        
        //System.out.println(playerFaction.contains(basicTank));
        
         System.out.println( playerFaction.containsAll( basicTank, basicArcher) );
    }
    
    public static void addInfoToList(CustomList faction){
        NonPlayableCharacter a1 = new NonPlayableCharacter("Archer1",10,2,100,true);
        NonPlayableCharacter a2 = new NonPlayableCharacter("Tank1",5,10,300,true);
        NonPlayableCharacter a3 = new NonPlayableCharacter("Knight1",20,10,150,true);
        NonPlayableCharacter a4 = new NonPlayableCharacter();
        NonPlayableCharacter a5 = new NonPlayableCharacter();
        NonPlayableCharacter a6 = new NonPlayableCharacter();
        a4.parse("Maceman1 10 10 150 true");
        a5.parse("Maceman2 10 10 150 true");
        a6.parse("Maceman3 10 10 150 true");

        faction.add(a1);
        faction.add(a2);
        faction.add(a3);
        faction.add(a4);
        faction.add(a5);
        faction.add(a6);
      
    }
    
    
    
}

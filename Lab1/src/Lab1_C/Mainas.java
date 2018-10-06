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
        
        CustomList<NPC> playerFaction = new CustomList<>();
        NPC a3 = new NPC("NAUJAS",20,10,150,true);
        NPC basicTank = new NPC("Tank1",5,10,300,true);
        NPC basicArcher = new NPC("Archer1",10,2,100,true);
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
        
        // System.out.println( playerFaction.containsAll( basicTank, basicArcher) );
    }
    
    public static void addInfoToList(CustomList faction){
        NPC a1 = new NPC("Archer1",10,2,100,true);
        NPC a2 = new NPC("Tank1",5,10,300,true);
        NPC a3 = new NPC("Knight1",20,10,150,true);
        NPC a4 = new NPC();
        NPC a5 = new NPC();
        NPC a6 = new NPC();
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

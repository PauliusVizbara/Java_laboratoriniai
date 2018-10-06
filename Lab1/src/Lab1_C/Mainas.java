/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1_C;

/**
 *
 * @author Paulius
 */
public class Mainas {
    
    int count = 0;
    public static void main(String[] args){
        
        CustomList customList = new CustomList(100);
        customList.add(10);
        customList.add(20);
        customList.add(30);
        customList.add(40);
        customList.add(50);
        customList.remove(1);
        System.out.println(customList.contains(50));
        System.out.println(customList.containsAll(10,20,100) );
        System.out.println(customList.removeFirst());       
        System.out.println(customList.get(0));
        
        
    }
    
    
}

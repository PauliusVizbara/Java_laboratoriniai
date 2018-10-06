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
public class CustomList<T> {

  
    private int count;
    private int size;

    private Object A[];
 
    //Default constructor
    public CustomList(int size) {
        A = new Object[size];
    }

    public T get(int index){
        return (T) A[index];
    }
    public void add(int element) {
        A[count++] = element;
    }

    public void remove(int index) {
        for (int i = index; i < count - 1; i++) {
            A[i] = A[i + 1];
        }
    }

    public void set(int index, int element) {
        A[index] = element;
    }

    public boolean contains(T element) {
        for (int i = 0; i < count; i++) {
            if (A[i] == element) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(T... elements) {

        for (int element : elements) {
            if ( !contains(element)) return false;
        }
        return true;
    }
    
    public int removeFirst(){
        int removedElement = A[0];
        for (int i = 0; i < count-1; i++) {
            A[i] = A[i+1];
        }
        return removedElement;
        
    }

}

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
public class CustomList<E extends KTUable<E>> {

    private Node<E> first;   // rodyklė į pirmą mazgą
    private Node<E> last;    // rodyklė į paskutinį mazgą
    private Node<E> current; // rodyklė į einamąjį mazgą, naudojama getNext
    private int size;        // sąrašo dydis, tuo pačiu elementų skaičius

    public E removeFirst() {
        if (size == 0) {
            return null;
        }
        
        E removedElement = null;
        removedElement = first.element;
        
        if ( size == 1) first = null;
        else first = first.next;
        
        return removedElement;
    }

    public boolean containsAll(E... e){
        for(E temp:e){
            if(!contains(temp) ) return false;
        }
        return true;
    }
    public boolean contains(E e){
        for (start(); !lastElement(); next()) {
            if ( current.element.compareTo(e) == 0) return true;
            
        }
        
        return false;
    }
    public E remove(int index) {
        int counter = 0;
        E removedElement = null;

        if (index == 0) {
            removedElement = first.element;
            first = first.next;
        }

        for (start(); !lastElement(); next()) {
            if (counter + 1 == index) {
                if (index == size) {
                    current.next.element = removedElement;
                    current.next = null;
                } else {
                    removedElement = current.next.element;
                    current.next = current.next.next;

                }
                return removedElement;
            }
            counter++;
        }

        return null;

    }

    public boolean add(E e) {
        if (e == null) {
            return false;   // nuliniai objektai nepriimami
        }
        if (first == null) {
            first = new Node<>(e, first);
            last = first;
        } else {
            Node<E> e1 = new Node(e, null);
            last.next = e1;
            last = e1;
        }
        size++;
        return true;
    }

    private void next() {
        current = current.next;

    }

    private boolean lastElement() {
        return current == null;
    }

    private void start() {
        current = first;
    }

    public void printList() {
        for (start(); !lastElement(); next()) {
            System.out.println(current.element.toString());
        }
    }

    /**
     * Įterpia elementą į k-ąją poziciją
     *
     * @param k pozicija
     * @param e įterpiamasis elementas
     * @return jei k yra blogas, grąžina null
     */
    public E set(int k, E e) {
        int counter = 0;

        for (start(); !lastElement(); next()) {
            if (counter == k) {
                E returnedElement = current.element;
                current.element = e;
                return returnedElement;
            }
            counter++;

        }

        return null;
    }

    public boolean add(int k, E e) {
        if (e == null) {
            return false;
        }
        if (k < 0 || k > size) {
            return false;
        }

        int counter = 0;
        if (k == 0) {

            first = new Node<>(e, first);
            size++;
            return true;
        }

        for (start(); !lastElement(); next()) {
            if (counter + 1 == k) {
                current.next = new Node(e, current.next);
                size++;
                return true;
            }
            counter++;

        }

        return false;

    }

    /**
     * Grąžina elementą pagal jo indeksą (pradinis indeksas 0)
     *
     * @param k indeksas
     * @return k-ojo elemento reikšmę; jei k yra blogas, gąžina null
     */
    public E get(int k) {
        if (k < 0 || k >= size) {
            return null;
        }
        current = first.findNode(k);
        return current.element;
    }

    /**
     * Vidinė mazgo klasė
     *
     * @param <E> mazgo duomenų tipas
     */
    private static class Node<E> {

        private E element;    // ji nematoma už klasės ListKTU ribų
        private Node<E> next; // next - kaip įprasta - nuoroda į kitą mazgą

        Node(E data, Node<E> next) { //mazgo konstruktorius
            this.element = data;
            this.next = next;
        }

        /**
         * Suranda sąraše k-ąjį mazgą
         *
         * @param k ieškomo mazgo indeksas (prasideda nuo 0)
         * @return surastas mazgas
         */
        public Node<E> findNode(int k) {
            Node<E> e = this;
            for (int i = 0; i < k; i++) {
                e = e.next;
            }
            return e;
        }
    } // klasės Node pabaiga
}

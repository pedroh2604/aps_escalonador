/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

/**
 *
 * @author cmlima
 */
public class List {
    
    private int elements[];
    private int size;

    public List(int size) {
        this.size = 0;
        this.elements = new int[size];
    }

    public int getSize() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public boolean isFull() {
        return this.size == this.elements.length;
    }
    
    public boolean add(int data, int index) {
        if (!(index < 0 || this.isFull() || index > this.size)) {
            if (index < this.size) {
                for(int i = this.size; i > index; i--) {
                    this.elements[i] = this.elements[i - 1];
                }
            }
            this.elements[index] = data;
            this.size++;
            return true;            
        }
        return false;
    }
    
    public boolean add(int data) {
        if (this.isFull()) {
            return false;
        }
        this.elements[this.size] = data;
        this.size++;
        return true;            
    }
    
    public int remove(int index) {
        if (!(index < 0 || this.isEmpty() || index >= this.size)) {
            int temp = this.elements[index];
            for (int i = index; i < this.size - 1; i++){
                this.elements[i] = this.elements[i + 1];
            }
            this.size--;
            return temp;
        }
        throw new Error("Índice inválido");
    }

    public boolean removeElement(int data) {
        int index = this.indexOf(data);
        if (index > 0) {
            this.remove(index);
            return true;
        }
        return false;        
    }
    
    public int get(int index) {
        if (this.isEmpty() || index < 0 || index >= this.size) {
            throw new Error("Índice inválido");
        }
        return this.elements[index];
    }
    
    public boolean set(int data, int index) {
        if (!(index < 0 || index >= this.size)) {
            this.elements[index] = data;
            return true;
        }
        return false;        
    }
    
    public int indexOf(int data) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i] == data) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            builder.append(this.elements[i]).append(" ");
        }
        return builder.toString();
    }
}

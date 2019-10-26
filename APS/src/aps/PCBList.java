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
public class PCBList {

    private PCBNode first;
    private PCBNode last;
    private int size;

    public PCBList() {
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public boolean exists(PCB search) {
        PCBNode node = this.first;
        while (node != null) {
            if (node.data.getPID() == null ? search.getPID() == null : node.data.getPID().equals(search.getPID())) {
                return true;
            }
            node = node.next;
        }
        return false;
    }
    
    public boolean add(PCB data, int position) {
        if (position > this.size || position < 0) {
            return false;
        }
        PCBNode newNode = new PCBNode(data);
        if (position == 0 && this.isEmpty()) {
            this.first = this.last = newNode;
        } else {
            if (position == 0) {
                newNode.next = this.first;
                this.first = newNode;
            } else {
                if (position == this.size) {
                    this.last.next = newNode;
                    this.last = newNode;
                } else {
                    int counter = 0;
                    PCBNode temp = this.first;
                    while (counter < position - 1) {
                        temp = temp.next;
                        counter++;
                    }
                    newNode.next = temp.next;
                    temp.next = newNode;
                }
            }
        }
        this.size++;
        return true;
    }

    public void add(PCB data) {
        PCBNode newNode = new PCBNode(data);
        if (this.isEmpty()) {
            this.first = this.last = newNode;
        } else {
            this.last.next = newNode;
            this.last = newNode;
        }
        this.size++;
    }
    
    public PCB removeAt(int position) {
        if (this.isEmpty() || position > this.size || position < 0) {
            throw new Error("Posição inválida");
        }
        PCB temp;
        if (position == 0) {
            temp = this.first.data;
            if (this.size == 1) {
                this.first = this.last = null;
            } else {
                this.first = this.first.next;
            }
        } else {
            int counter = 0;
            PCBNode previous = this.first;
            while (counter < position - 1) {
                previous = previous.next;
                counter++;
            }
            PCBNode removed = previous.next;
            temp = removed.data;
            previous.next = removed.next;
        }
        this.size--;
        return temp;
    }

    public boolean remove(PCB data) {
        int position = this.indexOf(data);
        if (position > 0) {
            this.removeAt(position);
            return true;
        }
        return false;        
    }

    public boolean set(PCB data, int position) {
        if (!(this.isEmpty() || position < 0 || position >= this.size)) {
            int counter = 0;
            PCBNode node = this.first;
            while (counter < position) {
                node = node.next;
                counter++;
            }
            node.data = data;
            return true;
        }
        return false;        
    }
    
    public PCB get(int position) {
        if (this.isEmpty() || position < 0 || position >= this.size) {
            throw new Error("Índice inválido");
        }
        int counter = 0;
        PCBNode node = this.first;
        while (counter < position) {
            node = node.next;
            counter++;
        }
        return node.data;
    }
        
    public int indexOf(PCB search) {
        int counter = 0;
        PCBNode node = this.first;
        while (node != null) {
            if (node.data.getPID() == null ? search.getPID() == null : node.data.getPID().equals(search.getPID())) {
                return counter;
            }
            counter++;
            node = node.next;
        }
        return -1;
    }
    
    private int compareTo(PCB pcb1, PCB pcb2) {
        if (pcb1.getArrival() > pcb2.getArrival()) {
            return 1;
        }
        if (pcb1.getArrival() < pcb2.getArrival()) {
            return -1;
        }
        return pcb1.getPID().compareTo(pcb2.getPID());
    };
    
    public void sort() {
        boolean sorted = false;
        PCB temp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < this.getSize() - 1; i ++) {
                if (this.compareTo(this.get(i), this.get(i + 1)) > 0) {
                    temp = this.get(i);
                    this.set(this.get(i + 1), i);
                    this.set(temp, i + 1);
                    sorted = false;
                }
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        PCBNode node = this.first;
        while (node != null) {
            builder.append(node.data.getPID()).append(" ");
            node = node.next;
        }
        return builder.toString().trim();
    }    
}

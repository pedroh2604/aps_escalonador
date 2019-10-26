/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

/**
 *
 * @author pedro
 */
public class PCBNode {
    
    public PCB data;
    public PCBNode next;
    
    public PCBNode (PCB pcb) {
        this.data = pcb;
        this.next = null;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }
}

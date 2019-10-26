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
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void testarPCB() {
    
        int ioRequests[] = { 3, 5, 7 } ;
        PCB pcb = new PCB(2, 10, ioRequests, 2);
        pcb.executeBurst(3);
        pcb.executeBurst(4);
        pcb.executeBurst(5);
        System.out.println("isCompleted: " + pcb.isCompleted());
        System.out.println("remaining: " + pcb.remaining());
        System.out.println("isIORequested in " + pcb.elapsed() + ": " + pcb.isIORequested());
        pcb.executeBurst(6);
        pcb.executeBurst(7);
        System.out.println("isCompleted: " + pcb.isCompleted());
        System.out.println("remaining: " + pcb.remaining());
        System.out.println("isIORequested in " + pcb.elapsed() + ": " + pcb.isIORequested());
        pcb.executeBurst(10);
        pcb.executeBurst(11);
        pcb.executeBurst(12);
        System.out.println("isCompleted: " + pcb.isCompleted());
        System.out.println("remaining: " + pcb.remaining());
        System.out.println("isIORequested in " + pcb.elapsed() + ": " + pcb.isIORequested());
        System.out.println("turnaround: " + pcb.turnaround());
        System.out.println("waiting: " + pcb.waiting());
        pcb.executeBurst(25);
        pcb.executeBurst(26);
        System.out.println("isCompleted: " + pcb.isCompleted());
        System.out.println("remaining: " + pcb.remaining());
        System.out.println("isIORequested: " + pcb.isIORequested());
        System.out.println("turnaround: " + pcb.turnaround());
        System.out.println("waiting: " + pcb.waiting());
    }
    
    public static void testarFilaDinamica() {
        
        int ioRequests1[] = { 3, 5, 7 } ;
        PCB pcb1 = new PCB(2, 10, ioRequests1, 2);
        int ioRequests2[] = { 4, 6, 8 } ;
        PCB pcb2 = new PCB(1, 20, ioRequests2, 2);
        int ioRequests3[] = { 5, 10, 11 } ;
        PCB pcb3 = new PCB(0, 30, ioRequests3, 2);
        
        Queue fila = new Queue();
        System.out.println("size: " + fila.getSize());
        System.out.println("isEmpty: " + fila.isEmpty());
        fila.enqueue(pcb1);
        System.out.println("front: " + fila.front().toString());
        System.out.println("size: " + fila.getSize());
        System.out.println("isEmpty: " + fila.isEmpty());
        fila.enqueue(pcb2);
        System.out.println("front: " + fila.front().toString());
        System.out.println("size: " + fila.getSize());
        System.out.println("isEmpty: " + fila.isEmpty());
        fila.enqueue(pcb3);
        System.out.println("front: " + fila.front().toString());
        System.out.println("size: " + fila.getSize());
        System.out.println("isEmpty: " + fila.isEmpty());
        System.out.println("dequeue: " + fila.dequeue().getPID());
        System.out.println("dequeue: " + fila.dequeue().getPID());
        System.out.println("dequeue: " + fila.dequeue().getPID());
        System.out.println("size: " + fila.getSize());
        System.out.println("isEmpty: " + fila.isEmpty());
    }
    
    public static void main(String[] args) {
        testarPCB();
        testarFilaDinamica();
    }
    
}

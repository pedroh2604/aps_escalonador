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
        PCB pcb = new PCB("PID-01", 2, 10, ioRequests, 2);
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
    
    public static void main(String[] args) {
        testarPCB();
    }
    
}

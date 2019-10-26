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
        
        PCBQueue fila = new PCBQueue();
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
    
    public static void testarLog() {
        
        String[] queue1 = { } ;
        LogItem item1 = new LogItem(0, "PID-1", queue1);
        String[] queue2 = { "PID-2", "PID-3" } ;
        LogItem item2 = new LogItem(1, "PID-4", queue2);
        String[] queue3 = { "PID-3" } ;
        LogItem item3 = new LogItem(2, "PID-2", queue3);
        String[] queue4 = { } ;
        LogItem item4 = new LogItem(3, "PID-3", queue4);
        Log log = new Log();
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
        log.enqueue(item1);
        log.enqueue(item2);
        System.out.println("front: " + log.front().getPID());
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
        log.enqueue(item3);
        System.out.println("front: " + log.front().getPID());
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
        System.out.println("Queue: " + log.toString());
        System.out.println("Dequeue: " + log.dequeue().getPID());
        log.enqueue(item4);
        System.out.println("Queue: " + log.toString());
        System.out.println("Dequeue: " + log.dequeue().getPID());
        System.out.println("Queue: " + log.toString());
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
    }
    
    public static void testarSort() {
        PCBList lista = new PCBList();

        lista.add(new PCB(1, 10, new int[]{2, 6}, 4));
        lista.add(new PCB(1, 15, new int[]{3}, 3));
        lista.add(new PCB(0, 11, new int[]{5}, 2));
        lista.add(new PCB(1, 13, new int[]{7}, 5));
        lista.add(new PCB(5, 13, new int[]{7}, 5));
        lista.add(new PCB(3, 13, new int[]{7}, 5));
        lista.add(new PCB(6, 13, new int[]{7}, 5));
        lista.add(new PCB(4, 13, new int[]{7}, 5));
        lista.add(new PCB(2, 13, new int[]{7}, 5));
        
        lista.sort();
        
        System.out.println(lista.toString());
    }

    public static void testarRoundRobin() {
        RoundRobin scheduler = new RoundRobin(4);

        scheduler.addProcess(new PCB(1, 10, new int[]{2, 6}, 4));
        scheduler.addProcess(new PCB(1, 15, new int[]{3}, 3));
        scheduler.addProcess(new PCB(0, 11, new int[]{5}, 2));
        scheduler.addProcess(new PCB(1, 13, new int[]{7}, 5));
        scheduler.addProcess(new PCB(5, 13, new int[]{7}, 5));
        scheduler.addProcess(new PCB(3, 13, new int[]{7}, 5));
        scheduler.addProcess(new PCB(6, 13, new int[]{7}, 5));
        scheduler.addProcess(new PCB(4, 13, new int[]{7}, 5));
        scheduler.addProcess(new PCB(2, 13, new int[]{7}, 5));
        
        scheduler.execute();
    }
    
    public static void main(String[] args) {
//        testarPCB();
//        testarFilaDinamica();
//        testarLog();
//        testarSort();
        testarRoundRobin();

    }
    
}

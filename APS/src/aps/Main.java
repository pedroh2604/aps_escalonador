/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import data_structures.List;
import data_structures.Queue;

/**
 *
 * @author pedro
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void testarPCB() {
    
        System.out.println("\n\n****** TESTANDO PCB ******\n");
        
        int ioRequests[] = { 3, 5, 7 } ;
        Process pcb = new Process(2, 10, ioRequests, 2);
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

        System.out.println("\n\n****** TESTANDO FILA DINÂMICA ******\n");
        
        int ioRequests1[] = { 3, 5, 7 } ;
        Process pcb1 = new Process(2, 10, ioRequests1, 2);
        int ioRequests2[] = { 4, 6, 8 } ;
        Process pcb2 = new Process(1, 20, ioRequests2, 2);
        int ioRequests3[] = { 5, 10, 11 } ;
        Process pcb3 = new Process(0, 30, ioRequests3, 2);
        
        Queue<Process> fila = new Queue<>();
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

        System.out.println("\n\n****** TESTANDO LOG ******\n");
        
        String queue1 = "";
        LogItem item1 = new LogItem(0, "PID-1", 4, queue1);
        String queue2 = "PID-2 PID-3";
        LogItem item2 = new LogItem(1, "PID-4", 3, queue2);
        String queue3 = "PID-3";
        LogItem item3 = new LogItem(2, "PID-2", 4, queue3);
        String queue4 = "";
        LogItem item4 = new LogItem(3, "PID-3", 2, queue4);
        List<LogItem> log = new List<>();
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
        log.add(item1);
        log.add(item2);
        System.out.println("front: " + log.get(0).getPID());
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
        log.add(item3);
        System.out.println("front: " + log.get(0).getPID());
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
        System.out.println("List: " + log.toString());
        System.out.println("Remove: " + log.removeAt(0).getPID());
        log.add(item4);
        System.out.println("List: " + log.toString());
        System.out.println("Remove: " + log.removeAt(0).getPID());
        System.out.println("List: " + log.toString());
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
    }
    
    public static void testarSort() {
        
        System.out.println("\n\n****** TESTANDO SORT ******\n");
        
        List<Process> lista = new List<>();

        lista.add(new Process(1, 10, new int[]{2, 6}, 4));
        lista.add(new Process(1, 15, new int[]{3}, 3));
        lista.add(new Process(0, 11, new int[]{5}, 2));
        lista.add(new Process(1, 13, new int[]{7}, 4));
        lista.add(new Process(5, 13, new int[]{7}, 4));
        lista.add(new Process(3, 13, new int[]{7}, 4));
        lista.add(new Process(6, 13, new int[]{7}, 4));
        lista.add(new Process(4, 13, new int[]{7}, 4));
        lista.add(new Process(2, 13, new int[]{7}, 4));
        
        lista.sort();
        
        System.out.println(lista.getSize());
        System.out.println(lista.toString());
    }

    public static void testarRoundRobin() {
        
        System.out.println("\n\n****** TESTANDO ROUND ROBIN ******\n");
        
        Scheduler scheduler = new Scheduler(ALGORITHM.ROUND_ROBIN);
        scheduler.setQuantum(4);

        scheduler.addProcess(new Process(1, 10, new int[]{}, 4));
        scheduler.addProcess(new Process(1, 15, new int[]{3}, 3));
        scheduler.addProcess(new Process(0, 11, new int[]{5}, 2));
        scheduler.addProcess(new Process(1, 13, new int[]{7}, 4));
        scheduler.addProcess(new Process(5, 13, new int[]{7}, 4));
        scheduler.addProcess(new Process(3, 13, new int[]{7}, 4));
        scheduler.addProcess(new Process(6, 13, new int[]{7}, 4));
        scheduler.addProcess(new Process(4, 13, new int[]{7}, 4));
        scheduler.addProcess(new Process(2, 13, new int[]{7}, 4));
        
        scheduler.execute();
        System.out.println("Resultado\n" + scheduler.toString());
    }

    public static void testarPrioridadePreemptivo() {
        
        System.out.println("\n\n****** TESTANDO PRIORIDADE PREEMPTIVO ******\n");

        Scheduler scheduler = new Scheduler(ALGORITHM.PRIORITY_PREEMPTIVE);
        
        scheduler.addProcess(new Process(1, 10, new int[]{}, 4));
        scheduler.addProcess(new Process(1, 15, new int[]{3}, 3));
        scheduler.addProcess(new Process(0, 11, new int[]{5}, 2));
        scheduler.addProcess(new Process(1, 13, new int[]{7}, 4));
        scheduler.addProcess(new Process(5, 13, new int[]{7}, 4));
        scheduler.addProcess(new Process(3, 13, new int[]{7}, 4));
        scheduler.addProcess(new Process(6, 13, new int[]{7}, 4));
        scheduler.addProcess(new Process(4, 13, new int[]{7}, 4));
        scheduler.addProcess(new Process(2, 13, new int[]{7}, 4));
        
        scheduler.execute();
        System.out.println("Resultado\n" + scheduler.toString());
    }
    
    public static void main(String[] args) {
//        testarPCB();
//        testarFilaDinamica();
//        testarLog();
//        testarSort();
//        testarRoundRobin();
        testarPrioridadePreemptivo();

    }
    
}

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
public class RoundRobin {
    private static int elapsedTime = -1;
    private int quantum;
    private PCBList list;
    private PCBQueue queue;
    private Log log;

    public RoundRobin(int quantum) {
        this.quantum = quantum;
        this.list = new PCBList();
        this.queue = new PCBQueue();
        this.log = new Log();
    }
    
    public Log getLog() {
        return log;
    }
    
    public void addProcess(PCB pcb) {
        this.list.add(pcb);
    }
    
    public void execute() {
        this.list.sort();
        while (!this.list.isEmpty()) {
            this.queue.enqueue(this.list.removeAt(0));
        }
        System.out.println(this.queue.toString());
    }

    @Override
    public String toString() {
        return "RoundRobin{" + "quantum=" + quantum + ", queue=" + queue + ", log=" + log + '}';
    }
    
}

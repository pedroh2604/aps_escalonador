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
 * @author cmlima
 */
public class PriorityPreemptive {
    private int elapsedTime;
    private List<PCB> list;
    private Queue<PCB> ready[];
    private Queue<PCB> io;
    private Queue<PCB> completed;
    private List<LogItem> log;

    public PriorityPreemptive() {
        this.list = new List<>();
        this.ready = new Queue[5];
        this.ready[0] = new Queue<>();
        this.ready[1] = new Queue<>();
        this.ready[2] = new Queue<>();
        this.ready[3] = new Queue<>();
        this.ready[4] = new Queue<>();
        this.io = new Queue<>();
        this.completed = new Queue<>();
        this.log = new List<>();
    }
    
    public List getLog() {
        return this.log;
    }
    
    public void addProcess(PCB pcb) {
        this.list.add(pcb);
    }
    
    private Queue<PCB> checkArrivals() {
        while (!this.list.isEmpty() && this.list.get(0).getArrival() <= this.elapsedTime) {
            PCB pcb = this.list.removeAt(0);
            this.ready[pcb.getPriority()].enqueue(pcb);
        }
        while (!this.io.isEmpty()) {
            PCB pcb = this.io.dequeue();
            this.ready[pcb.getPriority()].enqueue(pcb);
        }
        for (int i = 4; i >= 0; i--) { 
            if (!this.ready[i].isEmpty()) {
                return this.ready[i];
            }
        }
        return null;
    }
    
    private boolean isCompleted() {
        return this.list.isEmpty()
        && this.io.isEmpty()
        && this.ready[0].isEmpty()
        && this.ready[1].isEmpty()
        && this.ready[2].isEmpty()
        && this.ready[3].isEmpty()
        && this.ready[4].isEmpty();
    }
    
    public void execute() {
        this.elapsedTime = -1;
        this.list.sort();
        while(!this.isCompleted()) {
            this.elapsedTime++;
            Queue<PCB> queue = this.checkArrivals();
            if (queue != null) {
                PCB running = queue.front();
                running.executeBurst(this.elapsedTime);
                this.log.add(new LogItem(this.elapsedTime, running.getPID(), running.getPriority(), queue.toString()));
                if (running.isCompleted()) {
                    this.completed.enqueue(queue.dequeue());
                }
                if (running.isIORequested()) {
                    this.io.enqueue(queue.dequeue());
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.log.getSize(); i++) {
            LogItem item = this.log.get(i);
            builder.append(item.getTime()).append(" - ");
            builder.append(item.getPID()).append(" (prioridade: ");
            builder.append(item.getPriority()).append(")\nFila: ");
            builder.append(item.getQueue()).append("\n");
        }
        return builder.toString().trim();
    }
}

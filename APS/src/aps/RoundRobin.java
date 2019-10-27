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
public class RoundRobin {
    private int elapsedTime;
    private int quantum;
    private List<PCB> list;
    private Queue<PCB> ready;
    private Queue<PCB> io;
    private Queue<PCB> completed;
    private List<LogItem> log;

    public RoundRobin(int quantum) {
        this.quantum = quantum;
        this.list = new List<>();
        this.ready = new Queue<>();
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

    private void checkArrivals() {
        while (!this.list.isEmpty() && this.list.get(0).getArrival() <= this.elapsedTime) {
            this.ready.enqueue(this.list.removeAt(0));
        }
        while (!this.io.isEmpty()) {
            this.ready.enqueue(this.io.dequeue());
        }
    }

    private boolean isCompleted() {
        return this.list.isEmpty()
        && this.io.isEmpty()
        && this.ready.isEmpty();
    }

    public void execute() {
        this.elapsedTime = -1;
        this.list.sort();
        while (!this.isCompleted()) {
            this.elapsedTime++;
            this.checkArrivals();
            if (!this.ready.isEmpty()) {
                PCB running = this.ready.dequeue();
                for (int i = 0; i < this.quantum; i++) {
                    running.executeBurst(this.elapsedTime);
                    this.log.add(new LogItem(this.elapsedTime, running.getPID(), running.getPriority(), this.ready.toString()));
                    this.elapsedTime++;
                    if (running.isCompleted()) {
                        this.completed.enqueue(running);
                        break;
                    }
                    if (running.isIORequested()) {
                        this.io.enqueue(running);
                        break;
                    }
                }
                if (!running.isCompleted() && !running.isIORequested()) {
                    this.ready.enqueue(running);
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
            builder.append(item.getPID()).append("\nFila: ");
            builder.append(item.getQueue()).append("\n");
        }
        return builder.toString().trim();
    }
}

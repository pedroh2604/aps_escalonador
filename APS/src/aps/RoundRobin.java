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
    private static int elapsedTime = -1;
    private int quantum;
    private List<PCB> list;
    private Queue<PCB> ready;
    private Queue<PCB> completed;
    private List<LogItem> log;

    public RoundRobin(int quantum) {
        this.quantum = quantum;
        this.list = new List<>();
        this.ready = new Queue<>();
        this.completed = new Queue<>();
        this.log = new List<>();
    }
    
    public List getLog() {
        return this.log;
    }
    
    public void addProcess(PCB pcb) {
        this.list.add(pcb);
    }
    
    public void execute() {
        this.list.sort();
        while (!this.list.isEmpty()) {
            this.ready.enqueue(this.list.removeAt(0));
        }
        while (!this.ready.isEmpty()) {
            if (this.ready.front().isStarted() || this.ready.front().getArrival() <= elapsedTime) {
                PCB running = this.ready.dequeue();
                for (int i = 0; i < this.quantum; i++) {
                    running.executeBurst(elapsedTime);
                    this.log.add(new LogItem(elapsedTime, running.getPID(), this.ready.toString()));
                    elapsedTime++;
                    if (running.isCompleted()) {
                        this.completed.enqueue(running);
                        break;
                    }
                    if (running.isIORequested()) {
                        break;
                    }
                }
                if (!running.isCompleted()) {
                    this.ready.enqueue(running);
                }
            } else {
                elapsedTime++;
            }
        }
        System.out.println(this.ready.toString());
        System.out.println(this.completed.toString());
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

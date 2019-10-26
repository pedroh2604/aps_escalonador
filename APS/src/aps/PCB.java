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
public class PCB {
    public static int counter = 0;
    private String PID;
    private int arrival; // momento no tempo absoluto em que entra na fila ready
    private int duration; // número total de bursts necessários para completar a execução
    private int ioRequests[]; // bursts em que haverá chamadas de i/o 
    private int priority;
    private BurstList bursts;

    public PCB(int arrival, int duration, int[] ioRequests, int priority) {
        this.counter++;
        this.PID = "PID-" + this.counter;
        this.arrival = arrival;
        this.duration = duration;
        this.ioRequests = ioRequests;
        this.priority = priority;
        this.bursts = new BurstList(duration);
    }

    public String getPID() {
        return this.PID;
    }

    public int getArrival() {
        return this.arrival;
    }

    public int getDuration() {
        return this.duration;
    }

    public int[] getIoRequests() {
        return this.ioRequests;
    }

    public int getPriority() {
        return this.priority;
    }
    
    public int elapsed() {
        return this.bursts.getSize();
    }
    
    public int remaining() {
        return this.duration - this.elapsed();
    }

    public boolean isStarted() {
        return !this.bursts.isEmpty();
    }

    public boolean isCompleted() {
        return this.bursts.isFull();
    }

    public void executeBurst(int time) {
        if (!this.isCompleted()) {
            this.bursts.add(time);
        }
    }
    
    public boolean isIORequested() {
        if (!this.isCompleted()) {
            for (int burst: this.ioRequests) {
                if (burst == this.bursts.getSize()) {
                    return true;
                }
            }            
        }
        return false;
    }


    public int startTime() {
        if (this.isStarted()) {
            return this.bursts.get(0);            
        }
        return -1;
    }

    public int endTime() {
        if (this.isCompleted()) {
            return this.bursts.get(this.bursts.getSize() - 1);        
        }
        return -1;
    }

    public int turnaround() {
        if (this.isCompleted()) {
            return this.endTime() - this.startTime();
        }
        return -1;
    }

    public int waiting() {
        if (this.isCompleted()) {
            int waitToStart = this.startTime() - this.arrival;
            int waitDuringBursts = this.turnaround() - this.duration;
            return waitToStart + waitDuringBursts;
        }
        return -1;
    }
    
    @Override
    public String toString() {
        return "PCB{" + "PID=" + PID + ", arrival=" + arrival + ", duration=" + duration + ", ioRequests=" + ioRequests + ", priority=" + priority + '}';
    }    
}

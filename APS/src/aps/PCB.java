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
    private String PID;
    private int arrival;
    private int duration;
    private int ioRequests[];
    private int elapsed;
    private int priority;

    public PCB(String PID, int arrival, int duration, int[] ioRequests, int priority) {
        this.elapsed = 0;
        this.PID = PID;
        this.arrival = arrival;
        this.duration = duration;
        this.ioRequests = ioRequests;
        this.priority = priority;
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

    public int getElapsed() {
        return this.elapsed;
    }

    public int getPriority() {
        return this.priority;
    }
    
    public void executeBurst() {
        if (!this.isCompleted()) {
            this.elapsed++;            
        }
    }
    
    public boolean ioRequested() {
        for (int el: this.ioRequests) {
            if (el == this.arrival + this.elapsed) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isCompleted() {
        return this.elapsed - this.arrival >= this.duration;
    }

    @Override
    public String toString() {
        return "PCB{" + "PID=" + PID + ", arrival=" + arrival + ", duration=" + duration + ", ioRequests=" + ioRequests + ", elapsed=" + elapsed + ", priority=" + priority + '}';
    }
}

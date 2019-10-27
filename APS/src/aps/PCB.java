/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import data_structures.IEquatable;
import data_structures.IComparable;
import data_structures.List;

/**
 *
 * @author pedro
 */
public class PCB implements IEquatable<PCB>, IComparable<PCB> {
    public static int counter = 0;
    private final String PID;
    private final int arrival; // momento no tempo absoluto em que entra na fila ready
    private final int duration; // número total de bursts necessários para completar a execução
    private final int ioRequests[]; // bursts em que haverá chamadas de i/o 
    private final int priority;
    private final List<Burst> bursts;

    public PCB(int arrival, int duration, int[] ioRequests, int priority) {
        if (arrival < 0) {
            throw new Error("O parâmetro arrival deve ser positivo");
        }
        if (priority < 0 || priority > 4) {
            throw new Error("O parâmetro priority deve ser um inteiro de 0 a 4");
        }
        counter++;
        this.PID = "PID-" + counter;
        this.arrival = arrival;
        this.duration = duration;
        this.ioRequests = ioRequests;
        this.priority = priority;
        this.bursts = new List<>();
    }

        public PCB(String PID, int arrival, int duration, int[] ioRequests, int priority) {
        if (arrival < 0) {
            throw new Error("O parâmetro arrival deve ser positivo");
        }
        if (priority < 0 || priority > 4) {
            throw new Error("O parâmetro priority deve ser um inteiro de 0 a 4");
        }
        this.PID = PID;
        this.arrival = arrival;
        this.duration = duration;
        this.ioRequests = ioRequests;
        this.priority = priority;
        this.bursts = new List<>();
    }

    public String getPID() {
        return PID;
    }

    public int getArrival() {
        return arrival;
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
        return this.bursts.getSize() == this.duration;
    }

    public void executeBurst(int time) {
        if (!this.isCompleted()) {
            this.bursts.add(new Burst(time));
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
            return this.bursts.get(0).time;            
        }
        return -1;
    }

    public int endTime() {
        if (this.isCompleted()) {
            return this.bursts.get(this.bursts.getSize() - 1).time;        
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
    public int compareTo(PCB other) {
        if (this.getArrival() > other.getArrival()) {
            return 1;
        }
        if (this.getArrival() < other.getArrival()) {
            return -1;
        }
        return this.getPID().compareTo(other.getPID());
    }

    @Override
    public boolean isEqual(PCB other) {
        return this.getPID().equals(other.getPID());
    }

    @Override
    public String toString() {
        return this.getPID();
    }
}

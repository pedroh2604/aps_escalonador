/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import data_structures.IComparable;
import data_structures.IEquatable;

/**
 *
 * @author cmlima
 */
public class LogItem implements IEquatable<LogItem>, IComparable<LogItem> {

    private int time;
    private String PID;
    private int priority;
    private String queue;

    public LogItem(int time, String PID, int priority, String queue) {
        this.time = time;
        this.PID = PID;
        this.priority = priority;
        this.queue = queue;
    }

    public int getTime() {
        return time;
    }

    public String getPID() {
        return PID;
    }

    public int getPriority() {
        return priority;
    }

    public String getQueue() {
        return queue;
    }

    @Override
    public int compareTo(LogItem other) {
        return this.getTime() - other.getTime();
    }

    @Override
    public int compareToByName(LogItem other) {
        return this.PID.compareTo(other.PID);
    }

    @Override
    public boolean isEqual(LogItem other) {
        return this.getTime() == other.getTime();
    }
    
    @Override
    public String toString() {
        return this.getTime() + " " + this.getPID();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import data_structures.IComparable;
import data_structures.IEquatable;
import data_structures.Queue;

/**
 *
 * @author cmlima
 */
public class LogItem implements IEquatable<LogItem>, IComparable<LogItem> {

    private int time;
    private String PID;
    private String queue;

    public LogItem(int time, String PID, String queue) {
        this.time = time;
        this.PID = PID;
        this.queue = queue;
    }

    public int getTime() {
        return time;
    }

    public String getPID() {
        return PID;
    }

    public String getQueue() {
        return queue;
    }

    @Override
    public int compareTo(LogItem other) {
        return this.getTime() - other.getTime();
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

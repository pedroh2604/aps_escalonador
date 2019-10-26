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
public class LogItem {

    private int time;
    private String PID;
    private String[] queue;

    public LogItem(int time, String PID, String[] queue) {
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

    public String[] getQueue() {
        return queue;
    }

    @Override
    public String toString() {
        return "LogItem{" + "time=" + time + ", PID=" + PID + ", queue=" + queue + '}';
    }

}

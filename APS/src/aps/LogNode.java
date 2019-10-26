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
public class LogNode {
    
    public LogItem data;
    public LogNode next;
    
    public LogNode (LogItem logItem) {
        this.data = logItem;
        this.next = null;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }
}

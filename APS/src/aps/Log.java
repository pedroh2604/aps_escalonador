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
public class Log {
    
    private LogNode head, tail;
    private int size;

    public Log() {
        size = 0;
        this.head = this.tail = null;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int getSize() {
        return this.size;
    }

    public int getSize2() {
        if (this.head == null) {
            return 0;
        }
        int counter = 0;
        LogNode next = this.head;
        while (next != null) {
            counter++;
            next = next.next;
        }
        return counter;
    }

    public void enqueue(LogItem logItem) {
        LogNode node = new LogNode(logItem);
        if (this.isEmpty()) {
            this.head = this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
        this.size++;
    }

    public LogItem dequeue() {
        if (this.isEmpty()) {
            throw new Error("A fila está vazia!");
        }
        LogItem temp = this.head.data;
        if (this.size == 1) {
            this.head = this.tail = null;
        } else {
            this.head = this.head.next;
        }
        this.size--;
        return temp;
    }
    
    public LogItem front() {
        if (this.isEmpty()) {
            throw new Error("A fila está vazia!");
        }
        return this.head.data;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        LogNode node = this.head;
        while (node != null) {
            builder.append(node.data.getPID()).append(" ");
            node = node.next;
        }
        return builder.toString().trim();
    }
}

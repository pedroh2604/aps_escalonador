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
public class Queue {
    private Node front;
    private Node rear;
    
    public Queue () {
        this.front = null;
        this.rear = null;
    }
    
    public boolean isEmpty () {return this.front == null;}
    
    public int size () {
        int size = 0;
        Node node = this.front;
        
        while (node != null) {
            size += 1;
            node = node.nextNode;
        }
        
        return size;
    }
    
    public void enqueue (Process process) {
        Node node = new Node(process);
        if (isEmpty()) {
            this.front = node;
            this.rear = node;
            return;
        }
        
        this.rear.nextNode = node;
        this.rear = node;
    }
    
    public Process dequeue () throws Exception{
        if(isEmpty()) {throw new Exception("queue is empty");}
        
        Process dequeuedElement = this.front.process;
        
        this.front = this.front.nextNode;
        if (this.front == null) {this.rear = null;}
        
        return dequeuedElement;
    }
    
    public void print () {
        if (isEmpty()) {System.out.println("queue is empty"); return;}
        
        System.out.println("\n gonna print the queue elements");
        
        Node node = this.front;
        
        while (node != null) {
            System.out.println("element: " + node.process);
            node = node.nextNode;
        }
    }
}

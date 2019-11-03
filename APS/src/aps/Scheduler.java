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
public class Scheduler {
    private ALGORITHM algorithm;
    private int quantum = 1;
    private Dispatcher dispatcher;
    private List<PCB> jobs;         // fila de novos
    private Queue<PCB> ready;       // fila de prontos
    private List<PCB> priority[];   // vetor de filas de prontos por prioridade (somente para prioridade preemptivo)
    private Queue<PCB> wait;        // fila de processos com execução suspensa, esperando para voltar à fila de prontos
    private Queue<PCB> io;          // fila de processos em chamada de I/O
    private Queue<PCB> completed;   // fila de processos encerrados

    public Scheduler(ALGORITHM algorithm) {
        this.algorithm = algorithm;
        this.jobs = new List<>();
        this.ready = new Queue<>();         
        this.wait = new Queue<>();
        this.priority = new List[5];
        this.priority[0] = new List<>();
        this.priority[1] = new List<>();
        this.priority[2] = new List<>();
        this.priority[3] = new List<>();
        this.priority[4] = new List<>();
        this.io = new Queue<>();
        this.completed = new Queue<>();
    }

    public ALGORITHM getAlgorithm() {
        return algorithm;
    }
    
    public void setQuantum(int quantum) {
        // No caso do Prioridade Preemptivo, o quantum é
        // sempre 1, porque a cada passo é necessário checar
        // se há algum outro processo de maior prioridade
        if (this.algorithm == ALGORITHM.ROUND_ROBIN) {
            this.quantum = quantum;
        }
    }

    public int getQuantum() {
        return quantum;
    }

    public void addProcess(PCB pcb) {
        this.jobs.add(pcb);
    }
    
    public void addProcesses(List<PCB> list) {
        this.jobs = list;
    }
    
    private void updatePriorityLists() {
        if (this.algorithm == ALGORITHM.PRIORITY_PREEMPTIVE) {
            Queue<PCB> temp = new Queue<>();
            while (!this.ready.isEmpty()) {
                temp.enqueue(this.ready.dequeue());
            }
            while (!temp.isEmpty()) {
                this.priority[temp.front().getPriority()].add(temp.dequeue());  
            }
        }
    }
    
    private void getNewJobs() {
        while (!this.jobs.isEmpty() && this.jobs.get(0).getArrival() <= this.dispatcher.getTime()) {
            if (this.algorithm == ALGORITHM.ROUND_ROBIN) {
                this.ready.enqueue(this.jobs.removeAt(0));
            } else {
                this.priority[this.jobs.get(0).getPriority()].add(this.jobs.removeAt(0));
            }
        }
    }
    
    private void  rescueWaitingJobs() {
        while (!this.io.isEmpty()) {
            if (this.algorithm == ALGORITHM.ROUND_ROBIN) {
                this.ready.enqueue(this.io.dequeue());
            } else {
                this.priority[this.io.front().getPriority()].add(this.io.dequeue());
            }
        }
        if (this.algorithm == ALGORITHM.ROUND_ROBIN) {
            while (!this.wait.isEmpty()) {
                this.ready.enqueue(this.wait.dequeue());
            }
        } else {
            Queue<PCB> temp = new Queue<>();
            while (!this.wait.isEmpty()) {
                temp.enqueue(this.wait.dequeue());
            }
            // o processo suspenso por outro mais prioritário continua
            // a ocupar o primeiro lugar da lista de processos com a mesma
            // prioridade (por isso é necessário o uso de lista)
            // a fila intermediária (temp) é necessária para assegurar
            // a ordem de execução
            while (!temp.isEmpty()) {
                this.priority[temp.front().getPriority()].add(temp.dequeue(), 0);  
            }
        }
    }
    
    private void updateReadyQueue() {
        this.updatePriorityLists();
        this.getNewJobs();
        this.rescueWaitingJobs();
    }
    
    private Queue<PCB> getReadyQueue() {
        this.updateReadyQueue();
        if (this.algorithm == ALGORITHM.PRIORITY_PREEMPTIVE) {
            this.ready.clear();
            for (int i = 4; i >= 0; i--) { 
                while (!this.priority[i].isEmpty()) {
                    this.ready.enqueue(this.priority[i].removeAt(0));
                }
            }
        }
        return this.ready;
    }

    private boolean isCompleted() {
        return this.jobs.isEmpty()
        && this.io.isEmpty()
        && this.ready.isEmpty()
        && this.wait.isEmpty()
        && this.priority[0].isEmpty()
        && this.priority[1].isEmpty()
        && this.priority[2].isEmpty()
        && this.priority[3].isEmpty()
        && this.priority[4].isEmpty();
    }

    public void execute() {
        this.jobs.sort();
        this.dispatcher = new Dispatcher();
        while (!this.isCompleted()) {
            Queue<PCB> queue = this.getReadyQueue();
            if (queue != null && !queue.isEmpty()) {
                PCB running = queue.dequeue();
                this.dispatcher.dispatch(running, this.quantum, queue.toString());
                if (running.isCompleted()) {
                    this.completed.enqueue(running);
                } else if (running.isIORequested()) {
                    this.io.enqueue(running);
                } else {
                    this.wait.enqueue(running);
                }
            } else {
                this.dispatcher.sleep();
            }
        }
    }
    
    public double avgTurnaround() {
        if (this.isCompleted()) {
            int sum = 0;
            Queue<PCB> temp = new Queue<>();
            while (!this.completed.isEmpty()) {
                PCB pcb = this.completed.dequeue();
                sum += pcb.getTurnaroundTime();
                temp.enqueue(pcb);
            }
            while (!temp.isEmpty()) {
                this.completed.enqueue(temp.dequeue());
            }
            return sum / this.completed.getSize();
        }
        return -1;
    }

    public double avgWaiting() {
        if (this.isCompleted()) {
            int sum = 0;
            Queue<PCB> temp = new Queue<>();
            while (!this.completed.isEmpty()) {
                PCB pcb = this.completed.dequeue();
                sum += pcb.getWaitingTime();
                temp.enqueue(pcb);
            }
            while (!temp.isEmpty()) {
                this.completed.enqueue(temp.dequeue());
            }
            return sum / this.completed.getSize();
        }
        return -1;
    }
    
    public String getTimeLinesAsString() {
        List<PCB> temp = this.getTimeLinesSerialized();
        if (temp == null) {return "";}
        StringBuilder builder = new StringBuilder();
        while (!temp.isEmpty()) {
            PCB pcb = temp.removeAt(0);
            builder.append(pcb.getTimeLine()).append("\n");
        }
        return builder.toString().trim();
    }
    
    public List getTimeLinesSerialized() {        
        if (!this.isCompleted()) { return null; }
        
        List<PCB> list = new List<>();
        Queue<PCB> queue = this.completed.copy();
        while(!queue.isEmpty()) {
            list.add(queue.dequeue());
        }
        list.sortByName();
        return list;
    }
    
    @Override
    public String toString() {
        return this.dispatcher.getLogAsString();
    }
}

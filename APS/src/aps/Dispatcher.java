/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import data_structures.List;

/**
 *
 * @author cmlima
 */
public class Dispatcher {
    
    private CPU cpu;
    private List<LogItem> log;

    public Dispatcher() {
        this.cpu = new CPU();
        this.log = new List<>();
    }

    public int getTime() {
        return this.cpu.getTime();
    }
    
    public List getLog() {
        return this.log;
    }
    
    public String getLogAsString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.log.getSize(); i++) {
            LogItem item = this.log.get(i);
            builder.append(item.getTime()).append(" - ");
            builder.append(item.getPID()).append(" (prioridade: ");
            builder.append(item.getPriority()).append(")\nFila: ");
            builder.append(item.getQueue()).append("\n");
        }
        return builder.toString().trim();
    }
    
    public void sleep() {
        this.cpu.tick();
    }

    public void dispatch(Process process, int bursts, String queue) {
        for (int i = 0; i < bursts; i++) {
            process.executeBurst(this.getTime());
            this.log.add(new LogItem(this.getTime(), process.getPID(), process.getPriority(), queue));
            this.cpu.tick();
            if (process.isCompleted() || process.isIORequested()) {
                break;
            }
        }
    }
}

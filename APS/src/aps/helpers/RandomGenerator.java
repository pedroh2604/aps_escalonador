
package aps.helpers;

import aps.PCB;
import data_structures.List;
import java.util.Random;

public class RandomGenerator {
    
    public static int generateInteger(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }
    
    public static PCB generatePCB(int minTime, int maxTime) {
        if (maxTime - 1 <= minTime) {
            throw new Error("Parâmetros inválidos");
        }
        int arrival = generateInteger(minTime, maxTime - 1);
        int duration = generateInteger(minTime, maxTime - arrival - 1);
        int requests = generateInteger(-2, 5);
        int ioRequests[];
        if (requests <= 0) {
            ioRequests = new int[0];
        } else {
            if (requests > (duration / 2)) {
                requests = duration / 2;
            }
            ioRequests = new int[requests];
            for (int i = 0; i < requests; i++) {
                ioRequests[i] = generateInteger(arrival, arrival + duration);
            }
        }
        int priority = generateInteger(0, 4);
        return new PCB(arrival, duration, ioRequests, priority);
    }
    
    public static List<PCB> generatePCBList(int size, int startTime, int endTime) {
        List<PCB> list = new List<>();
        for (int i = 0; i < size; i++) {
            list.add(generatePCB(startTime, endTime));
        }
        return list;
    }
}

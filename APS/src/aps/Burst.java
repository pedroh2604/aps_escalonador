/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps;

import data_structures.IEquatable;
import data_structures.IComparable;

/**
 *
 * @author pedro
 */
public class Burst implements IEquatable<Burst>, IComparable<Burst> {
    
    public final int time;

    public Burst(int time) {
        this.time = time;
    }

    @Override
    public int compareTo(Burst other) {
        return this.time - other.time;
    }

    @Override
    public boolean isEqual(Burst other) {
        return this.time == other.time;
    }
}

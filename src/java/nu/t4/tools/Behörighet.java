/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.tools;

import nu.t4.beans.program.HandledareManager;

/**
 *
 * @author DanLun2
 */
public enum Behörighet {
    elev(0),lärare(1), Handledare(2);
    
    private final int value;
    private Behörighet(int value){
         this.value = value;
    }

    public int getValue() {
        return value;
    }
}

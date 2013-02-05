/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gotan

import org.gotan.model.server.GotanAttribute

/**
 *
 * @author vinhar
 */
class Thermometer{

    def state = State.ON
    def status = "Ready"
    
    def temperature = new GotanAttribute()
    def min = new GotanAttribute()
    def max = new GotanAttribute()
    
    def properties = [defaultMin:"-275"]

    Thermometer(){
        temperature.value = 21.1
        temperature.unit = "degres"
        temperature.properties = [minAlarm:"19", maxAlarm:"23"]
    
    }
    
    def init(){
        
        this.max.value = Double.MIN_VALUE
        this.max.properties.unit = "degres"

        this.min.value = properties["defaultMin"]
        this.min.properties.unit = "degres"

    }
    
    def reset(){
        
    }
}


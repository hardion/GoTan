/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gotan

import org.gotan.model.State
import org.gotan.model.server.GotanCommand
import org.gotan.model.server.GotanObject
import org.gotan.model.server.GotanAttribute
/**
 *
 * @author vinhar
 */
class Thermometer1 extends GotanObject{
    
    Thermometer1(){
        super()
        
        this.state = State.ON
        this.status = "Ready"
        
        attributes["temperature"] = new GotanAttribute()
        
        attributes["min"] = new GotanAttribute()
        attributes["max"] = new GotanAttribute()
        
        attributes.temperature.value = 21.1
        attributes.temperature.unit = "degres"
        attributes.temperature.gproperties = [minAlarm:"19", maxAlarm:"23"]
        
        gproperties["defaultMin"]="-275"
        
        this.max.value = Double.MIN_VALUE
        this.max.properties.unit = "degres"

        this.min.value = Double.MAX_VALUE
        this.min.properties.unit = "degres"
        
        commands["reset"] = new GotanCommand({this.reset})
        //        commands["reset"].command = 
        
    }
  
    //  Attribute<double> temperature= 21.1
    //  Attribute<double> double min=0
    //  Attribute<double> double max=0
  
    def reset(){
        min.value = attributes.temperature.value
        max.value = attributes.temperature.value
    }
  
    def random(){
        Math.random()*100-50
    }
  
    GotanAttribute getTemperature(){
        attributes.temperature.value = this.random()
    
        if (attributes.temperature.value > max.value){
            attributes.max.value = attributes.temperature.value as double
        }else if (min.value > attributes.temperature.value ){
            attributes.min.value = attributes.temperature.value as double
        }
        attributes.temperature
    }
  
}


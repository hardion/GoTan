/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gotan

import org.gotan.model.server.GotanServer
import org.gotan.model.server.GotanObject
import org.gotan.model.server.GotanAttribute
import org.gotan.model.server.GotanCommand

import org.gotan.model.State

import org.restlet.Component
import org.gotan.restlet.GotanComponent

import groovy.util.logging.Slf4j
import groovy.json.JsonOutput
import groovy.json.JsonBuilder

import org.slf4j.*
import org.gotan.model.server.GotanCommand
import org.gotan.model.GAttribute

/**
 * The internet of Things
 * 
 * @author hardion
 */
@Slf4j
class Gotan {
    
    int port = 8080
    
    GotanServer localServer = new GotanServer()
    
    def servers = [localServer]
    
    def component
    
    public Gotan(){
        
    }
    
    public static void main(String[] args){
        def t = new Thermometer1()
                
        println "${t.temperature} + ${t.toJson()}"
        //        return
        
        def gotan = new Gotan()
        // Register a new Java Class as a GotanObject Class
        
        gotan.registerLocalClass("Thermometer", Thermometer1.class)
        gotan.registerLocalObject("Thermometer", "kitchen/haven/thermometer")

        gotan.startUndetached()
    
    }
    
    def setContext(context){
        localServer.domain.context = context
    }
    def getContext(){
        localServer.domain.context
    }
    
    def registerRemoteServer(server){
        
    }

    def registerLocalClass(String gclazz, def clazzId){
        localServer.addObjectClass(gclazz, clazzId)
    }
    
    def unregisterLocalClass(String){
        localServer.removeObjectClass(gclazz)
    }
    
    def registerLocalObject(String gclazz, def objectName){
        localServer.addObject(gclazz,objectName)
    }
    
    def propertyMissing(String name) {
        localServer.domain.objects[name] 
    }
    
    def getObjects(){
        def result = localServer.domain.objects
        log.debug( "objects = $result" )
        return result

    }
    
    def getClasses(){
        localServer.domain.classes
    }
    
    def startUndetached(){
        // Create a new Component.
        component = new GotanComponent(this);
        component.start();        
        log.debug( "component" )
    }
    
    def start(){
        

        // Start the component.
        Thread.startDaemon(){
            this.startUndetached()
        }
        
        while( !this.component || !this.component.isStarted()){
            sleep(5)
        }
    }
    
    def stop(){
        
        component?.stop();
    }

}

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
        attributes.temperature.gproperties = [minAlarm:19, maxAlarm:23]
        
        gproperties["defaultMin"]="-275"
        
        this.max.value = Double.MIN_VALUE
        this.max.properties.unit = "degres"

        this.min.value = Double.MAX_VALUE
        this.min.properties.unit = "degres"
        
        commands["reset"] = new GotanCommand(this.$reset)
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
  
    GAttribute getTemperature(){
        attributes.temperature.value = this.random()
    
        if (attributes.temperature.value > max.value){
            attributes.max.value = attributes.temperature.value as double
        }else if (min.value > attributes.temperature.value ){
            attributes.min.value = attributes.temperature.value as double
        }
        attributes.temperature
    }
  
}


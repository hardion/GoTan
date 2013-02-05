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
import org.gotan.representation.JsonGRep

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
        
        def gotan = new Gotan()
        // Register a new Groovy Class as a GotanObject Class
        
        gotan.registerLocalClass("Thermometer", Thermometer1.class)
        gotan.registerLocalObject("Thermometer", "kitchen/haven/thermometer")

        gotan.startUndetached()
    
    }
    
    def setContext(context){        
        localServer.domain.context = context.startsWith("/") ?: "/"+context
    }
    def getContext(){
        localServer.domain.context
    }
    
    def registerRemoteServer(server){
        
    }

    def registerLocalClass(String gclazz, def clazzId){
        localServer.addObjectClass(gclazz, clazzId)
    }
    
    def unregisterLocalClass(String gclazz){
        localServer.removeObjectClass(gclazz)
    }
    
    def unregisterLocalObject(String object){
        localServer.removeObject(object)
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




package org.gotan.model.server

import org.gotan.model.GObject
import org.gotan.model.GClass
import org.gotan.model.GAttribute
import org.gotan.model.GCommand
import org.gotan.model.State

import groovy.json.JsonBuilder


/**
 *
 * @author vinhar
 */
class GotanObject implements GObject{
    
    public GotanObject(){
        gclass = new GotanClass(version:"1.0.0", name:this.class.name)
        version = gclass.version
        state = State.INIT
    }
    
    String version  
    GClass gclass 
    
    State state = State.INIT
    String status
    
    Map<String, String> gproperties = [:]

    Map<String, GAttribute> attributes = [:]
    
    Map<String, GCommand> commands = [:]   
    
    
    def propertyMissing(String name) {
        attributes[name] 
    }
    
    def methodMissing(String name, args) {
        commands[name].execute(args)
    }
    
}


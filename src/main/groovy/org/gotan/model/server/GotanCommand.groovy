package org.gotan.model.server

import org.gotan.model.GCommand
import org.gotan.model.Element
import groovy.json.JsonBuilder

import groovy.util.logging.Slf4j
import org.slf4j.*

/**
 * The internet of Things
 * 
 * @author hardion
 */
@Slf4j
class GotanCommand implements GCommand{
    
    GotanCommand(Closure c){
        log.debug("Create a new command with closure $c")
        command = c
    }
    
    private def command = { return null}
    
    Map<String, Element> inputs = [:]
    
    Map<String, Element> outputs = [:]
    
    Map<String, Object> execute(Map<String, Object> args){
        log.debug("Executing this command : $command with $args")
        this.command.call(args)
    }
    
    def toJson(){
        new JsonBuilder(
            "inputs": inputs,
            "outputs": outputs,
        ).toString()
    }      

	
}

class CommandElement implements Element {
    
    String type        
    String description
}


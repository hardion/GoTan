package org.gotan.model.server

import org.gotan.model.GCommand
import org.gotan.model.Element
/**
 *
 * @author hardion
 */
class GotanCommand implements GCommand{
    
    GotanCommand(Closure c){
      //  command = c
    }
    
    //private def command = { return null}
    
    Map<String, Element> inputs = [:]
    
    Map<String, Element> outputs = [:]
    
    Map<String, Object> execute(Map<String, Object> args){
        command(args)
    } 
	
}

class CommandElement implements Element {
    
    String type        
    String description
}


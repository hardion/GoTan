package org.gotan.representation

import org.gotan.model.GAttribute
import org.gotan.model.GCommand
import org.gotan.model.GObject
import groovy.json.JsonBuilder

/**
 *
 * @author vinhar
 */
class JsonGRep {
    
   static def toJSON(def object){
      def result
      
      switch(object){
         
      case GAttribute :
         result = new JsonBuilder(
                    "value": object.value,
                    "unit": object.unit,
                    "properties": object.gproperties,
         ).toString()

         break
      case GCommand:
         result = new JsonBuilder(
                        "inputs": object.inputs,
                        "outputs": object.outputs,
         ).toString()

         break
            
      case GObject:
         result = new JsonBuilder(
                        "class": object.gclass,
                        "state": object.state,
                        "status": object.status,
                        "attributes": object.attributes/*.keySet()*/,
                        "commands": object.commands.keySet(),
                        "properties": object.gproperties.keySet(),
         ).toString()
         break
      }
      return result
   }
   	
}


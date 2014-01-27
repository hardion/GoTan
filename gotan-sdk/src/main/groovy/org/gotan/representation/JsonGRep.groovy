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
      new JsonBuilder(
                    JsonGRep.toStruct(object)
         ).toString()
   }
   
   static def toStruct(def object){
      def result
      
      switch(object){
         
      case GAttribute :
         result = [ "value": object.value,
                    "unit": object.unit,
                    "properties": object.gproperties]
         break
         
      case GCommand:
         result = [ "inputs": object.inputs,
                    "outputs": object.outputs]
         break
            
      case GObject:
         result = [ "class": object.gclass,
                    "state": object.state,
                    "status": object.status,
                    "attributes": object.attributes.keySet(),
                    "commands": object.commands.keySet(),
                    "properties": object.gproperties.keySet()]
         break
      }
      return result
   }
   	
}


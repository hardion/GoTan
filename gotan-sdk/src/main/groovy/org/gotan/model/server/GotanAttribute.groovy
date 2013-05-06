package org.gotan.model.server

import org.gotan.model.GAttribute

import groovy.json.JsonBuilder

/**
 *
 * @author hardion
 */
class GotanAttribute implements GAttribute{
  
    def value
  
    String unit = "NoUnit"
    
    Map<String, String> gproperties = [:]
  
    def propertyMissing(String name) {
        gproperties[name] 
    }
}


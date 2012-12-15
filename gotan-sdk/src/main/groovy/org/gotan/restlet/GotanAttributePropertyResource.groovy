/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gotan.restlet

import org.restlet.resource.ServerResource
import org.restlet.resource.Get
import org.restlet.ext.json.JsonRepresentation

import groovy.util.logging.Slf4j
import org.slf4j.*
import org.json.JSONArray
import groovy.json.JsonOutput


import org.json.JSONObject

/**
 * The internet of Things
 * 
 * @author hardion
 */
@Slf4j
class GotanAttributePropertyResource extends ServerResource{
    
    /** The underlying Item object. */  
    def attribute
    def propertyName
    
    @Override  
    protected void doInit() throws ResourceException {  
        // Get the "itemName" attribute value taken from the URI template  
        // /items/{itemName}.  
        def objectName = request.attributes["object"]  
        
        def attributeName = request.attributes["attribute"]  
        propertyName = request.attributes["property"]  
  
        // Get the item directly from the "persistence layer".  
        def gotan = this.context.attributes["gotan"]
        def object = gotan."$objectName"
        if(object){
            attribute = object.attributes[attributeName]
            log.debug("object = ${object} ; attribute = ${attribute}; property=$propertyName")         
            setExisting( this.attribute!=null && this.attribute.gproperties[propertyName] );  
        }else{
            setExisting( false );  
        }
    }  
    
    @Get
    public JsonRepresentation toJSON() {
        
        String value = this.attribute.gproperties[propertyName]
        return new JsonRepresentation( """{"$value"}""" );
    }

}


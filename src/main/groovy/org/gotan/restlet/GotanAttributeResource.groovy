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
class GotanAttributeResource extends ServerResource{
    
    /** The underlying Item object. */  
    def object
    def attributeName
    
    @Override  
    protected void doInit() throws ResourceException {  
        // Get the "itemName" attribute value taken from the URI template  
        // /items/{itemName}.  
        def objectName = request.attributes["object"]  
        attributeName = request.attributes["attribute"]  
  
        // Get the item directly from the "persistence layer".  
        def gotan = this.context.attributes["gotan"]
        object = gotan."$objectName"
        log.debug("object = ${object}")         

        setExisting( this.object!=null && this.object.attributes[attributeName] );  
    }  
    
    @Get
    public JsonRepresentation toJSON() {
        
        def attribute = this.object."$attributeName"
        // Doesn't work with object.attributes."$attributeName" if the getter is overriden 
        log.debug("object.attribute = ${attribute}")         
        def json = attribute.toJson()
        log.debug("object.attribute = ${json}") 
        return new JsonRepresentation( json );
    }

}


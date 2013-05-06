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

import org.gotan.representation.JsonGRep

/**
 * The internet of Things
 * 
 * @author hardion
 */
@Slf4j
class GotanObjectResource extends ServerResource{
    
    /** The underlying Item object. */  
    def object
    
    @Override  
    protected void doInit() throws ResourceException {  
        // Get the "itemName" attribute value taken from the URI template  
        // /items/{itemName}.  
        def objectName = request.attributes["object"]  
  
        // Get the item directly from the "persistence layer".  
        def gotan = this.context.attributes["gotan"]
        object = gotan."$objectName"
  
        setExisting( this.object!=null );  
    }  
    
    @Get
    public JsonRepresentation toJSON() {
        def json = JsonGRep.toJSON(object)
        log.debug("object = ${json}") 
        return new JsonRepresentation( json );
    }

}


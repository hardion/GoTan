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

import org.json.JSONObject

/**
 * The internet of Things
 * 
 * @author hardion
 */
@Slf4j
class GotanAttributePropertiesResource extends ServerResource{

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
        
  
        setExisting( this.object!=null && this.object.attributes[attributeName] );  
    }  
    
    @Get
    public JsonRepresentation toJSON() {
        return new JsonRepresentation( new JSONObject( this.object.attributes[attributeName].gproperties ) );
    }
}


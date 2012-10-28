/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gotan.restlet

import org.restlet.resource.ServerResource
import org.restlet.resource.Get
import org.restlet.resource.Post
import org.restlet.ext.json.JsonRepresentation

import groovy.util.logging.Slf4j
import org.slf4j.*
import org.json.JSONArray
import groovy.json.JsonOutput
import org.json.JSONObject
import org.restlet.representation.Representation
import org.restlet.data.Status



/**
 * The internet of Things
 * 
 * @author hardion
 */
@Slf4j
class GotanCommandResource extends ServerResource{
    
    /** The underlying Item object. */  
    def object
    def commandName
    
    @Override  
    protected void doInit() throws ResourceException {  
        // Get the "itemName" attribute value taken from the URI template  
        // /items/{itemName}.  
        def objectName = request.attributes["object"]  
        commandName = request.attributes["command"]  
  
        // Get the item directly from the "persistence layer".  
        def gotan = this.context.attributes["gotan"]
        object = gotan."$objectName"
        log.debug("object = ${object}")         

        setExisting( this.object!=null && this.object.commands[commandName] );  
    }  
    
    @Get
    public JsonRepresentation toJSON() {
        
        def command = this.object.commands[commandName]
        // Doesn't work with object.attributes."$attributeName" if the getter is overriden 
        log.debug("object.command = ${command}")         
        def json = command.toJson()
        log.debug("object.command = ${json}") 
        return new JsonRepresentation( json );
    }

    @Post("json")
    public JsonRepresentation executeFromJson(JsonRepresentation jsonInput) {
        
        def result
        def command = this.object.commands[commandName]
        def inputs = [:]
        
        log.debug("Command.executeFromJson $commandName") 

        
        if (jsonInput) {
            // Parse the given representation and retrieve pairs of  
            // "name=value" tokens.  
            def inputsObject = jsonInput.jsonObject
            
            inputsObject.keySet().each{key ->
                inputs[key]= inputsObject.get(key)
            }
        }
        try{
            def outputs = command.execute(inputs)

            setStatus(Status.SUCCESS_CREATED);
        
            if(outputs){
                // Set the response's status and entity  
                Representation rep = new JsonRepresentation( new JSONObject(outputs) )  
                // Indicates where is located the new resource.  
                //rep.setIdentifier(getRequest().getResourceRef().getIdentifier() + "/" + "last");  
                result = new JsonRepresentation( rep )
            }            
        }catch(Exception e){
            
            // Item is already registered.  
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);  
            result = new JsonRepresentation( new JSONObject() )  
        }  
  
        return  result

    }

}


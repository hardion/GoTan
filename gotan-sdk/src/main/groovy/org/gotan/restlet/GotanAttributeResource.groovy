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
class GotanAttributeResource extends ServerResource{
    
    /** The underlying Item object. */  
    def object
    def attribute
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

        attribute = this.object."$attributeName"
        setExisting( this.object!=null && attribute );  
    }  
    
    @Get
    public JsonRepresentation toJSON() {
        
        // Doesn't work with object.attributes."$attributeName" if the getter is overriden 
        log.debug("object.attribute = ${attribute}")         
        def json = attribute.toJson()
        log.debug("object.attribute = ${json}") 
        return new JsonRepresentation( json );
    }
    
    @Post("json")
    public JsonRepresentation fromJson(JsonRepresentation jsonInput) {
        
        log.debug("Setting $attributeName from JSON request") 

        def result

        //log.debug("Setting $attribute from JSON request: $jsonInput") 
        
        if (jsonInput) {
            try{

                // Parse the given representation and retrieve pairs of  
                // "name=value" tokens.  
                def inputsObject = jsonInput.jsonObject
            
                inputsObject.keys().each{key ->
                    def value = inputsObject.get(key)
                    log.debug("Setting $key of $attributeName :$value")
                    
                    //Set
                    attribute."$key" = inputsObject.get(key)
                    
                    //Update the given request to respond 
                    inputsObject.put(key, attribute."$key")
                }

                setStatus(Status.SUCCESS_CREATED);

                // Set the response's status and entity  
                Representation rep = new JsonRepresentation( inputsObject )  
                // Indicates where is located the new resource.  
                //rep.setIdentifier(getRequest().getResourceRef().getIdentifier() + "/" + "last");  
                result = new JsonRepresentation( rep )
            }catch(Exception e){
                log.warn(e.message) 

                // Item is already registered.  
                setStatus(Status.CLIENT_ERROR_NOT_FOUND);  
                result = new JsonRepresentation( new JSONObject() )  
            }  
        }else{
            log.warn("BadRequest : No input")
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);  
            result = new JsonRepresentation( new JSONObject() )  
            
        }
  
        return  result

    }


}


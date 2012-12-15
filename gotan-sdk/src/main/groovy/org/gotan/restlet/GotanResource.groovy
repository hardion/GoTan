/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gotan.restlet

import org.restlet.resource.ServerResource
import org.restlet.resource.Post
import org.restlet.ext.json.JsonRepresentation
import org.restlet.representation.Representation
import org.restlet.data.Status

/**
 *
 * @author vinhar
 */
class GotanResource extends ServerResource {
    
    /** 
     * Handle POST requests: create a new item. 
     */  
    @Post  
    public Representation accept(Representation entity) {  
            setStatus(Status.SUCCESS_ACCEPTED);  
        def gotan = this.context.attributes["gotan"]
        gotan.stop()

        def result = new JsonRepresentation(["stop":"successful"]);  
        return result;  
    }  
  
}


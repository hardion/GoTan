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

/**
 * The internet of Things
 * 
 * @author hardion
 */
@Slf4j
class GotanObjectsResource extends ServerResource{

    @Get
    public JsonRepresentation toJSON() {
        def gotan = this.context.attributes["gotan"]        
        def objectsNames = gotan.objects.keySet()
        log.debug("objects = ${objectsNames}") 
        return new JsonRepresentation( new JSONArray( objectsNames ) );
    }

}


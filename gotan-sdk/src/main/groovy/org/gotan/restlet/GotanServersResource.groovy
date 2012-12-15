/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gotan.restlet

import org.restlet.resource.ServerResource
import org.restlet.resource.Get
import org.restlet.ext.json.JsonRepresentation
import org.json.JSONArray

/**
 *
 * @author vinhar
 */
class GotanServersResource extends ServerResource {
    
    @Get
    public JsonRepresentation toJSON() {
        def gotan = this.context.attributes["gotan"]
        def names = gotan.servers
        return new JsonRepresentation( new JSONArray( names ) );
    }

}


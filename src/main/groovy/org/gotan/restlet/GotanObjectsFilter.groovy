/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gotan.restlet

import org.restlet.Context;

import groovy.util.logging.Slf4j
import org.slf4j.*
import org.json.JSONArray
import org.restlet.routing.Router
import org.restlet.routing.Route
import org.restlet.Restlet
import org.gotan.Gotan
import org.restlet.routing.Filter
import org.restlet.Request
import org.restlet.Response

/**
 * The internet of Things
 * 
 * @author hardion
 */
@Slf4j
class GotanObjectsFilter extends Filter{
    
    Gotan gotan
    
    public GotanObjectsFilter(Gotan gotan, Context context, Restlet next ){
        super(context, next)
        this.gotan = gotan
    }
    
    // We only allow URIs attached to a registered object.
    @Override
    protected int beforeHandle(Request request, Response response) {
        int result = Filter.STOP;
        String objectName = request.getResourceRef().getRemainingPart(true);
        if (gotan.objects.keySet().contains(objectName)) {
            result = Filter.CONTINUE
        }
        return result;
    }
        
}

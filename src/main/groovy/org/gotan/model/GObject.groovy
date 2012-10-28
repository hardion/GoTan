package org.gotan.model

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

/**
 *
 * The GObject
 * 
 * @author hardion
 */
interface GObject {
    
    String getVersion();
    
    GClass getGclass();
    
    Map<String, GAttribute> getAttributes();
    
    Map<String, GCommand> getCommands();
    
    Map<String, String> getGproperties();
    
    State getState();
    
    String getStatus();
}


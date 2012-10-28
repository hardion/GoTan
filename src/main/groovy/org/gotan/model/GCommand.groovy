/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gotan.model

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.Delete;

/**
 *
 * @author hardion
 */
interface GCommand {
    
    Map<String, Element> getInputs() 
    
    Map<String, Element> getOutputs() 
    
    Map<String, Object> execute(Map<String, Object> args);
	
}

interface Element {

    String getType ();        
    String getDescription ();
}


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
interface GAttribute {
  
  public Object getValue();

  public void setValue(Object o);
    
  public String getUnit();

  public void setUnit(String o);

  public Map<String, String> getGproperties();  
    
}


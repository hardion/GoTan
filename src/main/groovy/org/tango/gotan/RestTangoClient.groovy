/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tango.gotan

import org.lpny.groovyrestlet.GroovyRestlet;
import org.lpny.groovyrestlet.builder.RestletBuilder;
import org.restlet.*  
import org.restlet.data.* 

/**
 *
 * @author hardion
 */
RestletBuilder builder = new RestletBuilder()
def springContext = null
builder.setVariable("springContext" , null)
builder.init()

builder.client(Protocol.HTTP).get("http://localhost:8182/devices/sys/tg_test/1/string_scalar").getEntity().write(System.out);
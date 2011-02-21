/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.tango.gotan

import org.lpny.groovyrestlet.GroovyRestlet;
import org.lpny.groovyrestlet.builder.RestletBuilder;
import org.restlet.*  
import org.restlet.data.* 

import fr.soleil.device.utils.AttributeHelper;


/**
 * No import
 * @author hardion
 */
// How 
// SOLUTIONS :
// - Groovlet
// - http://groovy.codehaus.org/GroovyWS
// - GroovyRestlet gr = new GroovyRestlet()
// - def http = new HTTPBuilder('http://www.google.com')
// - http://json-lib.sourceforge.net/groovy.html
//
// A rich solution :
// - Grails
//
// Create a root router
def getContext(){null}

String ROOT_URI = "file:///Home/hardion/Document/";  


RestletBuilder builder = new RestletBuilder()
def springContext = null
builder.setVariable("springContext" , null)
builder.init()

System.setProperty("TANGO_HOST","192.168.0.14:20001")

builder.component{
    current.servers.add(Protocol.HTTP, 8182)
    application(uri:""){
        router{
            restlet(uri:"/devices/{domain}/{family}/{device}", handle:{req,resp->
                    def domain = req.attributes.get('domain')
                    def family = req.attributes.get('family')
                    def device = req.attributes.get('device')
                    def proxyname = "$domain/$family/$device"
                    def d = new DeviceServerProxy(proxyname)
                    def status = d.status()
                    resp.setEntity("Status of Domain/Family/Device \"${proxyname} = ${status}\"",
                        MediaType.TEXT_PLAIN)
                })
            restlet(uri:"/devices/{domain}/{family}/{device}/{element}", handle:{req,resp->
                    
                    def domain = req.attributes.get('domain')
                    def family = req.attributes.get('family')
                    def device = req.attributes.get('device')
                    def element = req.attributes.get('element')
                    def proxyname = "$domain/$family/$device"
                    def d = new DeviceServerProxy(proxyname)
                    def value = AttributeHelper.extract(d."$element")
                    resp.setEntity("Value of \"${proxyname}/$element = ${value}\"",
                        MediaType.TEXT_PLAIN)
                })
        }
    }
}.start()



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
// What I want
    
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

//Router router = new Router(getContext());  
//  
//// Attach a guard to secure access to the directory  
//Guard guard = new Guard(getContext(), ChallengeScheme.HTTP_BASIC,  
//        "Restlet tutorial");  
//guard.getSecrets().put("scott", "tiger".toCharArray());  
//router.attach("/docs/", guard);  
//  
//// Create a directory able to expose a hierarchy of files  
//Directory directory = new Directory(getContext(), ROOT_URI);  
//guard.setNext(directory);  
//  
//// Create the account handler  
//Restlet account = new Restlet() {  
//    
//    public void handle(Request request, Response response) {  
//        // Print the requested URI path  
//        String message = "Account of user \""  
//                + request.getAttributes().get("user") + "\"";  
//        response.setEntity(message, MediaType.TEXT_PLAIN);  
//    }  
//};  
//  
//// Create the orders handler  
//Restlet orders = new Restlet(getContext()) {  
//    @Override  
//    public void handle(Request request, Response response) {  
//        // Print the user name of the requested orders  
//        String message = "Orders of user \""  
//                + request.getAttributes().get("user") + "\"";  
//        response.setEntity(message, MediaType.TEXT_PLAIN);  
//    }  
//};  
//  
//// Create the order handler  
//Restlet order = new Restlet(getContext()) {  
//    @Override  
//    public void handle(Request request, Response response) {  
//        // Print the user name of the requested orders  
//        String message = "Order \""  
//                + request.getAttributes().get("order")  
//                + "\" for user \""  
//                + request.getAttributes().get("user") + "\"";  
//        response.setEntity(message, MediaType.TEXT_PLAIN);  
//    }  
//};  
//  
//// Attach the handlers to the root router  
//router.attach("/users/{user}", account);  
//router.attach("/users/{user}/orders", orders);  
//router.attach("/users/{user}/orders/{order}", order);  
//
//System.exit(0)

GroovyRestlet gr = new GroovyRestlet()
gr.build(new ByteArrayInputStream('''
builder.component{
        current.servers.add(protocol.HTTP, 8182)
        application(uri:""){
            router{
                def guard = guard(uri:"/docs", scheme:challengeScheme.HTTP_BASIC,
                        realm:"Restlet Tutorials")
                guard.secrets.put("scott", "tiger".toCharArray())
                guard.next = directory(root:"file:///Users/hardion/Sites/", autoAttach:true)
                restlet(uri:"/users/{user}", handle:{req,resp->
                    resp.setEntity("Account of user \\"${req.attributes.get('user')}\\"",
                            mediaType.TEXT_PLAIN)
                })
                restlet(uri:"/users/{user}/orders", handle:{req, resp->
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
                restlet(uri:"/users/{user}/orders/{order}", handle:{req, resp->
                    def attrs = req.attributes
                    def message = "Order \\"${attrs.get('order')}\\" for User \\"${attrs.get('user')}\\""
                    resp.setEntity(message, mediaType.TEXT_PLAIN)
                })
            }
        }
    }.start()
'''.getBytes()))

System.exit(0)


RestletBuilder builder = new RestletBuilder()
def springContext = null
builder.setVariable("springContext" , null)
builder.init()
//builder.server(protocol:Protocol.HTTP,port:8182){
//    restlet(handle:{req, resp->
//            resp.setEntity("Hello World", MediaType.TEXT_PLAIN)
//        })
// }.start()
System.setProperty("TANGO_HOST","192.168.0.14:20001")

builder.component{
    current.servers.add(Protocol.HTTP, 8182)
    application(uri:""){
        router{
            def guard = guard(uri:"/docs", scheme:ChallengeScheme.HTTP_BASIC,
                realm:"Restlet Tutorials")
            guard.secrets.put("scott", "tiger".toCharArray())
            guard.next = directory(root:"file:///Users/hardion/Sites/", autoAttach:false)
                
            restlet(uri:"/devices/{domain}", handle:{req,resp->
                    resp.setEntity("Domain \"${req.attributes.get('domain')}\"",
                        MediaType.TEXT_PLAIN)
                })
            restlet(uri:"/devices/{domain}/{family}", handle:{req,resp->
                    resp.setEntity("Domain/Family \"${req.attributes.get('domain')}/${req.attributes.get('family')}\"",
                        MediaType.TEXT_PLAIN)
                })
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
            restlet(uri:"/users/{user}/orders", handle:{req, resp->
                    resp.setEntity("Orders or user \"${req.attributes.get('user')}\"",
                        MediaType.TEXT_PLAIN)
                })
            restlet(uri:"/users/{user}/orders/{order}", handle:{req, resp->
                    def attrs = req.attributes
                    def message = "Order \"${attrs.get('order')}\" for User \"${attrs.get('user')}\""
                    resp.setEntity(message, MediaType.TEXT_PLAIN)
                })
        }
    }
}.start()



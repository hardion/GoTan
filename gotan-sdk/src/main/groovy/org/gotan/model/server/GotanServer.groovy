package org.gotan.model.server

import org.gotan.model.domain.MainDomain
/**
 *
 * @author vinhar
 */
class GotanServer {
    
    
    def name = "main/server"
    
    def domain = new MainDomain()
    
    
    def addObjectClass(String clazz, def name){
        domain.local.registerClass(clazz, name)
    }

    def addObject(String clazz, String name){
        domain.local.register(clazz, name)
    }
       
    def removeObject(String name){
        domain.local.unregister(name)
    }
    
    def removeObjectClass(String name){
        domain.local.unregisterClass(name)
    }

    def propertyMissing(String name) {
        domain.objects[name] 
    }
    
    def getObjects(){
        domain.objects
    }

}


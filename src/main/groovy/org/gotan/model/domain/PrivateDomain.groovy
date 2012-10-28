package org.gotan.model.domain

import groovy.util.logging.Slf4j
import org.slf4j.*

/**
 *
 * Directory of the Server
 * @author hardion
 */
@Slf4j
class PrivateDomain {
    
    def objects = [:]
    def classes = [:]
    
    
    ClassResolver localResolver = new GroovyClassResolver()
    
    def registerClass(def gclazz, def id){
        this.classes."$gclazz"=[id:id,objects:[]]
    }

    boolean register(def gclazz, def objectName){
        boolean result = false
        if( this.objects[objectName] == null ){
            
            // Retrieve the local class corresponding to the Gotan Class
            def gclass = this.classes[gclazz]
            if ( gclass ){
                def newborn = localResolver.newInstance(gclass.id)

                if(newborn){
                    gclass.objects += objectName
                    this.objects[objectName] = newborn
                    
                    result = true
                }else{
                    log.warning("The local resolver ${localResolver.class} couldn't create a new instance ")
                }
            }else{
                log.warning("This class is not yet registered ")
            }
        }else{
            log.warning("Can't add a new Object : this object already exists")
        }
        return result
    }
    
    void unregister(def objectName){
        
        def gclass = classes.values.find({it.objects.contains(objectName)})
        if(gclass){
            gclass.objects.remove(objectName)
            this.objects.remove(objectName)
        }else{
            log.warning("Can't remove this Object : $objectName doesn't exist")
        }
    }

    void unregisterClass(def classname){
        
        def gclass = classes[classname]
        if(gclass){
            def objects = gclass.objects
            objects.each(){
                this.unregister(it)
            }
            this.classes.remove(classname)
        }else{
            log.warn("Can't remove this Class : $classname doesn't exist")
        }
    }

}


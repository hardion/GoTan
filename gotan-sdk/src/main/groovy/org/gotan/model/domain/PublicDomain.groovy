package org.gotan.model.domain

import groovy.util.logging.Slf4j
import org.slf4j.*

/**
 *
 * Public Directory of the Server
 * @author hardion
 */
@Slf4j
class PublicDomain {
  
    def context = "/gotan"
    def subdomains = []
    
    boolean registerDomain( domain ){
        subdomains += domain
    }
    
    def getClasses(){
        def result = [:]
        subdomains.each(){
            result.putAll(it.classes)
        }
        return result
    }

    def getObjects(){
        log.debug("PublicDomain.getObjects()")
        
        def result = [:]
        subdomains.each(){
            result.putAll(it.objects)
            log.debug("$result Subdomain : $it with objects ${it.objects}")
        }
        return result
    }
}
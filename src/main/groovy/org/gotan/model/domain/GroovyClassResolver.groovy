/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gotan.model.domain

/**
 *
 * @author vinhar
 */
class GroovyClassResolver implements ClassResolver{

    def newInstance(def classId){
        if(classId instanceof Class){
            classId.newInstance()
        }else{
            new Class(classname).newInstance();
            
        }
        
    }
}


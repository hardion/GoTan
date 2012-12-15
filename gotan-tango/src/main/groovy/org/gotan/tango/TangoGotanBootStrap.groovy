package org.gotan.tango

import org.gotan.Gotan
import org.gotan.Thermometer1
import org.gotan.model.domain.PrivateDomain

import org.tango.client.database.DatabaseFactory
import org.tango.client.database.Database
import org.tango.DeviceState
import org.gotan.model.domain.ClassResolver
import org.gotan.model.GObject
import org.gotan.model.State
import org.gotan.model.server.GotanClass
import fr.soleil.tango.clientapi.TangoDevice
import org.gotan.model.GAttribute
import fr.soleil.tango.clientapi.TangoAttribute
import org.gotan.model.GCommand
import org.gotan.model.Element
import org.gotan.model.GClass
import groovy.json.JsonBuilder
import fr.soleil.tango.clientapi.TangoCommand

import groovy.util.logging.Slf4j

import org.slf4j.*

class TangoGotanBootStrap{
//    static String TANGO_HOST_NAME="192.168.56.101"
      static String TANGO_HOST_NAME="m4develop.maxlab.lu.se"
    static String TANGO_HOST_PORT="10000"
    static String TANGO_HOST="$TANGO_HOST_NAME:$TANGO_HOST_PORT"
    
    public static void main(String[] args){
        
        def gotan = new Gotan()
       // System.setProperty("TANGO_HOST",TANGO_HOST)
        
        // Register a new Groovy Class as a GotanObject Class
        gotan.localServer.domain.registerDomain(new TangoDomain()) 
//        registerLocalClass("Thermometer", Thermometer1.class)
//        gotan.registerLocalObject("Thermometer", "kitchen/haven/thermometer")

        gotan.startUndetached()

    }
    
}


class TangoResolver implements ClassResolver{
    Object newInstance(Object classname){
        return null
    }
}

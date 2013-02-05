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


/**
 *
 * @author vinhar
 */
@Slf4j
class TangoDomain {
    
    static String DEFAULT_TANGO_HOST="192.168.56.101:10000"
    static String TANGO_HOST = System.getProperty("TANGO_HOST",System.env.TANGO_HOST?:DEFAULT_TANGO_HOST);
    static String TANGO_HOST_NAME = TANGO_HOST.tokenize(":")[0]
    static String TANGO_HOST_PORT = TANGO_HOST.tokenize(":")[1]
    
    static String useCacheDatabase = System.getProperty("GOTAN_TANGO_CACHE",System.env.GOTAN_TANGO_CACHE?:"NO");
        
    static{
       System.setProperty("TANGO_HOST", TANGO_HOST)
    }
    
    static def getDefaultDatabase(){
        def result
        if(useCacheDatabase == "NO"){
           result = new fr.esrf.TangoApi.Database(TANGO_HOST_NAME, TANGO_HOST_PORT)
            
        }else{
           result = DatabaseFactory.getDatabase(TANGO_HOST_NAME, TANGO_HOST_PORT)

        }
    }

    def devices
    def database
    
    def TangoDomain(){
        this(TangoDomain.getDefaultDatabase())
    }
    
    def TangoDomain(def db, def deviceFilter="*"){
        database = db
        
        def devicelist
        
        if(database instanceof fr.esrf.TangoApi.Database){
            devicelist = database.get_device_list(deviceFilter)
            
        }else if (database instanceof org.tango.client.database.Database){
            database.clearCache()
            devicelist = database.getDeviceList("*","*").grep(~/${deviceFilter.replace("*",".*")}/)
        }
        log.debug( "TangoHost=${System.getProperty("TANGO_HOST")} ; database=$database ; objects = $devicelist" )
        devices = devicelist.collectEntries {[(it):new TangoObject(it)]}
    }
    
    def getClasses(){
        def result = [:]
        return result
    }

    def getObjects(){
     //   log.debug("PublicDomain.getObjects()")
        return devices
    }
}


package org.gotan.tango

import spock.lang.Specification
import spock.lang.Shared

import org.gotan.Gotan
import org.gotan.Thermometer1
import org.gotan.model.domain.PrivateDomain

import org.tango.client.database.DatabaseFactory

import org.restlet.resource.ClientResource
import org.restlet.data.MediaType

import groovy.util.logging.Slf4j

import org.slf4j.*
import org.gotan.model.server.GotanCommand
import org.gotan.model.GAttribute

/**
 *
 * @author vinhar
 */
@Slf4j
class TangoGotanBootStrapTest extends Specification {
    
    static def TANGO_TEST="sys/tg_test/1"
    static def TANGO_HOST="192.168.56.101:10000"
    
    @Shared def db;
    @Shared def gotan
    
    def setupSpec() {
        gotan = new Gotan()
        //System.setProperty("TANGO_HOST",TANGO_HOST)
	final String filePath = TangoGotanBootStrapTest.class.getResource("/org/gotan/tango/noDbproperties.txt").getPath();
	
        /*NoDBDeviceManager.startDevice(TANGO_TEST)
	db = DatabaseFactory.getDatabase();*/

        // Register a new Java Class as a GotanObject Class
        gotan.localServer.domain.registerDomain(new TangoDomain(/*db*/))
        gotan.start()

    }
    
    def "Get an Tango device through Gotan Object"() {
        setup :
        ClientResource resource = new ClientResource("http://localhost:8080/gotan/objects/$TANGO_TEST");

        expect:
        resource.get(MediaType.APPLICATION_JSON).text 
        
    }

    def "Get an Tango Attribute through Gotan Object"() {
        setup :
        ClientResource resource = new ClientResource("http://localhost:8080/gotan/objects/$TANGO_TEST/attributes/double_scalar");

        expect:
        resource.get(MediaType.APPLICATION_JSON).text 
        
    }
    
    def "Get an Tango Command through Gotan Object"() {
        setup :
        ClientResource resource = new ClientResource("http://localhost:8080/gotan/objects/$TANGO_TEST/commands/DevVoid");

        expect:
        resource.get(MediaType.APPLICATION_JSON).text 
        
    }
    
    def cleanupSpec() {
        gotan.stop()
        NoDBDeviceManager.stopDevice()
    }



}


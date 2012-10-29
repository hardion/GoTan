package org.gotan

import spock.lang.Specification
import spock.lang.Shared

import org.restlet.resource.ClientResource
import org.restlet.data.MediaType

class GotanServerTest extends Specification {
  
    @Shared def thermometer = "home/kitchen/thermometer"
    @Shared def gotan
    
    def setupSpec() {
        gotan = new Gotan()
        // Register a new Java Class as a GotanObject Class
        gotan.registerLocalClass("Thermometer", IndoorThermometer.class)
        gotan.registerLocalObject("Thermometer", thermometer)
    }
    
    

    def "Register a new gotan class"() {
        when:
        // Register a new Java Class as a GotanObject Class
        gotan.registerLocalClass("IndoorThermometer", IndoorThermometer.class)

        then:
        gotan.classes["IndoorThermometer"].id == IndoorThermometer.class
  
    }
    
    def "Unregister a new gotan class"() {
        setup:
        gotan.registerLocalClass("IndoorWaterThermometer", IndoorThermometer.class)
        
        when:
        // Register a new Java Class as a GotanObject Class
        gotan.unregisterLocalClass("IndoorWaterThermometer")

        then:
        gotan.classes["IndoorWaterThermometer"] == null
  
    }

    def "Unregister a new gotan object"() {
        def roofThermometer = "home/roof/thermometer"

        setup:
        // Register a new Java Class as a GotanObject Class
        gotan.registerLocalClass("IndoorAirThermometer", IndoorThermometer.class)
        gotan.registerLocalObject("IndoorAirThermometer", roofThermometer)

        when:
        gotan.unregisterLocalObject( roofThermometer )

        then:
        gotan."$roofThermometer" == null
  
    }
  
}
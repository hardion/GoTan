package org.gotan.restlet

import spock.lang.Specification
import spock.lang.Shared

import org.restlet.resource.ClientResource
import org.restlet.data.MediaType

import org.gotan.Gotan
import org.gotan.IndoorThermometer


class GotanRestletTest extends Specification {

    static def temperatureExpected = '''{"value":20.1,"unit":"degres","properties":{"minAlarm":19,"maxAlarm":23}}'''
    static def attributesExpected = '''["temperature","min","max"]'''
//    static def objectExpected = """{"class":{"version":"1.0.0","name":"org.gotan.IndoorThermometer"},"state":"ON","status":"Ready","attributes":{"temperature":{"value":21.1,"unit":"degres","gproperties":{"minAlarm":19,"maxAlarm":23}},"min":{"value":1.7976931348623157E308,"unit":"NoUnit","gproperties":{}},"max":{"value":4.9E-324,"unit":"NoUnit","gproperties":{}}},"commands":["reset"],"properties":{"defaultMin":-275}}"""
    static def objectExpected = """{"class":{"version":"1.0.0","name":"org.gotan.IndoorThermometer"},"state":"ON","status":"Ready","attributes":{"temperature":{"value":21.1,"unit":"degres","gproperties":{"minAlarm":19,"maxAlarm":23}},"min":{"value":1.7976931348623157E308,"unit":"NoUnit","gproperties":{}},"max":{"value":4.9E-324,"unit":"NoUnit","gproperties":{}}},"commands":["reset"],"properties":["defaultMin"]}"""
  
    @Shared def thermometer = "home/kitchen/thermometer"
    @Shared def gotan
    
    def setupSpec() {
        gotan = new Gotan()
        // Register a new Java Class as a GotanObject Class
        gotan.registerLocalClass("Thermometer", IndoorThermometer.class)
        gotan.registerLocalObject("Thermometer", thermometer)
        gotan.start()

    }
    
    def cleanupSpec() {
        gotan.stop()
    }
    
    def "Get an Gotan Object"() {
        setup :
        ClientResource resource = new ClientResource("http://localhost:8080/gotan/objects/$thermometer");

        expect:
        resource.get(MediaType.APPLICATION_JSON).text == objectExpected
        
    }
    
    def "Get the properties of a Gotan Object"() {
        setup :
        ClientResource resource = new ClientResource("http://localhost:8080/gotan/objects/$thermometer/properties");

        expect:
        resource.get(MediaType.APPLICATION_JSON).text == """{"defaultMin":"-275"}"""
        
    }
    
    def "Get the value of Gotan Object's property"() {
        setup :
        ClientResource resource = new ClientResource("http://localhost:8080/gotan/objects/$thermometer/properties/defaultMin");

        expect:
        resource.get(MediaType.APPLICATION_JSON).text == """{"-275"}"""
        
    }
    
    def "Read attributes"() {
        setup :
        ClientResource resource = new ClientResource("http://localhost:8080/gotan/objects/$thermometer/attributes");

        expect:
        resource.get(MediaType.APPLICATION_JSON).text == attributesExpected
    }

    def "Read an attribute"() {
        
        when:
        ClientResource resource = new ClientResource("http://localhost:8080/gotan/objects/$thermometer/attributes/temperature");  

        then:
        resource.get(MediaType.APPLICATION_JSON).text == temperatureExpected
          
    }

    def "Register a new gotan classes"() {
        
        setup:

        when:
        gotan.registerLocalClass("IndoorThermometer", IndoorThermometer.class)
        ClientResource resource = new ClientResource("http://localhost:8080/gotan/classes");  

        then:
        resource.get(MediaType.APPLICATION_JSON).text == """["Thermometer","IndoorThermometer"]"""
        
        cleanup:
        gotan.unregisterLocalClass("IndoorThermometer")
  
    }

}
package org.gotan

import spock.lang.Specification
import spock.lang.Shared

import org.restlet.resource.ClientResource
import org.restlet.data.MediaType

import org.gotan.representation.JsonGRep

class GotanObjectTest extends Specification {

    static def temperatureExpected = '''{"value":20.1,"unit":"degres","properties":{"minAlarm":"19","maxAlarm":"23"}}'''
    static def attributesExpected = ["temperature", "min", "max"]
    static def jsonAttributesExpected = '''["temperature","min","max"]'''
    static def thermometerExpected = """{"class":{"version":"1.0.0","name":"org.gotan.IndoorThermometer"},"state":"ON","status":"Ready","attributes":{"temperature":{"value":21.1,"unit":"degres","gproperties":{"minAlarm":"19","maxAlarm":"23"}},"min":{"value":1.7976931348623157E308,"unit":"NoUnit","gproperties":{}},"max":{"value":4.9E-324,"unit":"NoUnit","gproperties":{}}},"commands":["reset"],"properties":["defaultMin"]}"""
  
    @Shared def thermometer = "home/kitchen/thermometer"
    @Shared def gotan
    
    def setupSpec() {
        gotan = new Gotan()
        // Register a new Java Class as a GotanObject Class
        gotan.registerLocalClass("Thermometer", IndoorThermometer.class)
        gotan.registerLocalObject("Thermometer", thermometer)
    }
    
    def "Get a Gotan Object"(){
        expect:
        gotan."$thermometer"
    }
    
    def "Get a Gclass from a Gotan Object"(){
        expect:
        gotan."$thermometer".gclass
    }
    
    def "Get a Gotan Object in Json format"(){
        expect:
        JsonGRep.toJSON(gotan."$thermometer") == thermometerExpected
    }
    
    def "Read attributes"() {
        expect:
        gotan."$thermometer".attributes.keySet() as ArrayList == attributesExpected
    }
    
    def "Read an attribute"() {

        expect:
        gotan."$thermometer".temperature.value == 20.1
  
    }

    def "Read attributes"() {
        expect:
        gotan."$thermometer".attributes.keySet() as ArrayList == attributesExpected
    }
    
    def "Read attribute's properties"() {

        expect:
        gotan."$thermometer".temperature.gproperties == [minAlarm:"19", maxAlarm:"23"]
  
    }

    def "Read an attribute's property"() {

        expect:
        gotan."$thermometer".temperature.gproperties.minAlarm == "19"  
    }

    
    def "Read an attribute with the Json format"() {

        expect:
        JsonGRep.toJSON(gotan."$thermometer".temperature) == temperatureExpected
  
    }

}
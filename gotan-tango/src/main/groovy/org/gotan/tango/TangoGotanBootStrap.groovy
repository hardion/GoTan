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
    
    public static void main(String[] args){

        // CLI Management
        def cli = new CliBuilder(usage:'Gotan [options]')
        cli.h(longOpt:'help', 'print this message')
        cli._(longOpt:'simulate', 'Use Simulated devices')
        cli.th(longOpt:'tango_host', 'Set the TANGO_HOST (by default try with Environnement variable) ')
        cli.p(longOpt:'httpPort', args:1, argName:'value', 'HTTP PORT')
        cli.c(longOpt:'context', args:1, argName:'value', 'Context of the server ; useful for reverse proxy (ie: http://myserver:port/context/)')
        cli._(longOpt:'device-pattern', args:1, argName:'value', 'Filter out the device to export ; use "*" as a joker')
        // _CLI

        def options = cli.parse(args)
        if (options.h) {
          cli.usage();
        
        }else{
            def gotan = new Gotan()
            def tangodomain
            // set the context
            if(options.c){
                gotan.context = options.c
            }
            // set the port
            if(options.p){
                gotan.port = options.p.toInteger()
            }
            
            if(options."device-pattern"){
                def database = TangoDomain.getDefaultDatabase();
                tangodomain = new TangoDomain(database, options."device-pattern")                
            }else{
                tangodomain = new TangoDomain()                
            }
            
            // Register a new Groovy Class as a GotanObject Class
            gotan.localServer.domain.registerDomain(tangodomain) 

            gotan.startUndetached()
            
        }
     

    }
    
}


class TangoResolver implements ClassResolver{
    Object newInstance(Object classname){
        return null
    }
}

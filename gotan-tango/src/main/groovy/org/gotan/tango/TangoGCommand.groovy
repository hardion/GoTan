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
 * @author hardion
 */

/**
 *
 * @author hardion
 */
class TangoGCommand implements GCommand {
    TangoCommand command
    
    TangoGCommand(TangoCommand cmd){        
        command = cmd
    }
    
    Map<String, Element> getInputs() {
        [:]
    }
    
    Map<String, Element> getOutputs() {
        [:]
    }
    
    Map<String, Object> execute(Map<String, Object> args) {
        command.execute()
        return [:]
    }
    
    def toJson(){
        new JsonBuilder(
            "inputs": inputs,
            "outputs": outputs,
        ).toString()
    }
}
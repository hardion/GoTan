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


@Slf4j
class TangoObject implements GObject{
    GClass gclass 
    String version
    def tangodevice
    Map<String, GAttribute> gattributes = [:]
    Map<String, GCommand> gcommands = [:]
    Map<String, String> gproperties = [:]
    
    public TangoObject(def devicename){
        gclass = new GotanClass(version:"1.0.0", name:this.class.name)
        version = gclass.version
        // BIG HACK HERE : Currently no way to use the short name of device when use no db !!!
        // 
        if( !DatabaseFactory.useDb ){
            devicename="tango://localhost:" + System.getProperty("OAPort") + "/" + devicename + "#dbase=no";
            log.debug("device name ==== $devicename")
        }
        //
        
        tangodevice = new TangoDevice(devicename)
    }
        
    Map<String, GAttribute> getAttributes(){
        def attributelist = tangodevice.deviceProxy.get_attribute_list()       
        gattributes = attributelist.collectEntries { [(it):new TangoGAttribute(tangodevice.getTangoAttribute(it))]}
    }
    
    Map<String, GCommand> getCommands(){
        def commandlist = tangodevice.deviceProxy.command_list_query()       
        gcommands = commandlist.collectEntries { [(it.cmd_name):new TangoGCommand(tangodevice.getTangoCommand(it.cmd_name))]}       
    }
    
    Map<String, String> getGproperties(){
        def propertylist = tangodevice.deviceProxy.get_property_list("*")       
        gproperties = propertylist.collectEntries { [(it):""]}        
    }
    
    State getState(){
        
        return Enum.valueOf(State.class, DeviceState.toString(tangodevice.state()) )
    }
    
    String getStatus(){
        log.debug("tangodevice= $tangodevice.deviceName")
        tangodevice.getTangoAttribute("Status").read()       
    }
    
    def propertyMissing(String name) {
        def prop = gattributes[name]
        if(!prop && tangodevice.deviceProxy.get_attribute_list().contains(name)){
            prop = new TangoGAttribute(tangodevice.getTangoAttribute(name))
            gattributes[name]=prop            
        }
        return prop
    }
    
    def methodMissing(String name, args) {
        def cmd = gcommands[name]
        if(!cmd && tangodevice.deviceProxy.command_list_query().find(){ it.cmd_name==name}){
            cmd = new TangoGCommand(tangodevice.getTangoCommand(name))
            gcommands[name]=cmd            
        }
        cmd.execute(args)
    }
    
    def toJson(){
//        def builder = new JsonBuilder()
//        builder.object{
        new JsonBuilder(
            "class": this.gclass,
            "state": this.state,
            "status": this.status,
            "attributes": this.attributes.keySet(),
            "commands": this.commands.keySet(),
            "properties": this.gproperties.keySet(),
        ).toString()
//        }
//        builder.toString()
    }

}

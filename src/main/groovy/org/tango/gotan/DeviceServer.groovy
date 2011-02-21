//
// Generated from archetype; please customize.
//

package org.tango.gotan

/**
 * Example Groovy class.
 */
class DeviceServer{

        def invokeMethod(String name, args){
        //~proxy.command_inout(name, args)
        return "Execute $name with arguments $args"
    }

    def getProperty(String name) {
        "get property $name"
    }

    void setProperty(String name, value) {
        "set property $name with value $value"
    }

    
}

//
// Generated from archetype; please customize.
//

package org.tango.gotan

import groovy.util.GroovyTestCase
//@Grab(group='fr.esrf.tango', module='TangORB', version='7.3.1')



/**
 * Example Groovy class.
 */
class DeviceServerProxyTest extends GroovyTestCase{

    void testClient() {
        println 'Test Client'
        System.setProperty("TANGO_HOST","192.168.0.16:20001")
        def client = new DeviceServerProxy("sys/tg_test/1")

        println client.boolean_scalar
        println client.long_scalar
        assert 100 == client.DevDouble(100)

    }
    
}

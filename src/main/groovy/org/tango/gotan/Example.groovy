package org.tango.gotan

def testClient() {
    println 'Test Client'
    System.setProperty("TANGO_HOST","192.168.0.14:20001")
    def client = new DeviceServerProxy("sys/tg_test/1")

    println client.boolean_scalar
    println client.long_scalar
    println client.DevDouble(100.0)
    println client.DevLong(200)
}

testClient()
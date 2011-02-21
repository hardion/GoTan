package org.tango.gotan

import fr.esrf.TangoApi.DeviceProxy
import fr.esrf.TangoApi.DeviceAttribute
import fr.esrf.TangoApi.DeviceData
import fr.esrf.Tango.DevFailed
import fr.esrf.TangoApi.CommunicationFailed
import fr.esrf.TangoDs.TangoConst
import fr.esrf.Tango.DevState
import fr.esrf.Tango.AttrQuality
import fr.esrf.Tango.DevEncoded
//@Grab(group='fr.esrf.tango', module='TangORB', version='7.3.1')


/**
 * Example Groovy class.
 */
class DeviceServerProxy{

    DeviceProxy proxy
    String address
    
    def printError={ it ->
        println it.desc
        println it.origin
        println it.reason
        println it.severity
    }

    DeviceServerProxy(String address){
        try{
            proxy = new DeviceProxy(address)
            
        }catch(DevFailed e){
            e.errors.each(printError)
        }
        this.address  = address
    }

    def invokeMethod(String name, args){
        println args
        try{
            DeviceData data = new DeviceData();
            if(args){
                data.insert(args[0])
                data=proxy.command_inout(name, data)
                
            }else{
                data=proxy.command_inout(name)
            }

            deviceDataConverter.get(data.getType())(data)
            
        }catch(DevFailed e){
            e.errors.each(printError)
        }
    }

    def getProperty(String name) {
        if(name != deviceDataConverter || name != deviceAttributeConverter){
            try{
                DeviceAttribute attr = proxy.read_attribute(name)
                
            }catch(CommunicationFailed e){
                e.errors.each(printError)
            }
        }
    }

    void setProperty(String name, value) {
        proxy.write_attribute(name, new DeviceAttribute(value))
    }
    
    static deviceAttributeConverter = [
        (DevState[].class)  :   {value -> value.extractDevStateArray()},
        (DevState.class)    :   {value -> value.extractDevState()},
        (boolean)           :   {value -> value.extractBoolean()},
        (boolean[])         :   {value -> value.extractBooleanArray()},
        (short)             :   {value -> value.extractUChar()},
        (short[])           :   {value -> value.extractUCharArray()},
        (byte[])            :   {value -> value.extractCharArray()},
        (short)             :   {value -> value.extractShort()},
        (short[])           :   {value -> value.extractShortArray()},
        (int)               :   {value -> value.extractUShort()},
        (int[])             :   {value -> value.extractUShortArray()},
        (int)               :   {value -> value.extractLong()},
        (int[])             :   {value -> value.extractLongArray()},
        (long)              :   {value -> value.extractULong()},
        (long[])            :   {value -> value.extractULongArray()},
        (long)              :   {value -> value.extractLong64()},
        (long[])            :   {value -> value.extractLong64Array()},
        (long)              :   {value -> value.extractULong64()},
        (long[])            :   {value -> value.extractULong64Array()},
        (float)             :   {value -> value.extractFloat()},
        (float[])           :   {value -> value.extractFloatArray()},
        (double)            :   {value -> value.extractDouble()},
        (double[])          :   {value -> value.extractDoubleArray()},
        (DevState)          :   {value -> value.extractState()},
        (String)            :   {value -> value.extractString()},
        (String[])          :   {value -> value.extractStringArray()},
        (AttrQuality)       :   {value -> value.getQuality()},
        (DevEncoded)        :   {value -> value.extractDevEncoded()},
        (DevEncoded[])      :   {value -> value.extractDevEncodedArray()}
    ]

    static deviceDataConverter = [
	(TangoConst.Tango_DEV_VOID):{value -> value},
        (TangoConst.Tango_DEV_BOOLEAN):{value -> value.extractBoolean()},
	(TangoConst.Tango_DEV_SHORT):{value -> value.extractShort()},
	(TangoConst.Tango_DEV_LONG):{value -> value.extractLong()},
 	(TangoConst.Tango_DEV_FLOAT):{value -> value.extractFloat()},
 	(TangoConst.Tango_DEV_DOUBLE):{value -> value.extractDouble()},
	(TangoConst.Tango_DEV_USHORT):{value -> value.extractUShort()},
	(TangoConst.Tango_DEV_ULONG):{value -> value.extractULong()},
	(TangoConst.Tango_DEV_STRING):{value -> value.extractString()},
	(TangoConst.Tango_DEVVAR_CHARARRAY):{value -> value.extractByteArray()},
	(TangoConst.Tango_DEVVAR_SHORTARRAY):{value -> value.extractShortArray()},
	(TangoConst.Tango_DEVVAR_LONGARRAY):{value -> value.extractLongArray()},
	(TangoConst.Tango_DEVVAR_FLOATARRAY):{value -> value.extractFloatArray()},
	(TangoConst.Tango_DEVVAR_DOUBLEARRAY):{value -> value.extractDoubleArray()},
	(TangoConst.Tango_DEVVAR_USHORTARRAY):{value -> value.extractUShortArray()},
	(TangoConst.Tango_DEVVAR_ULONGARRAY):{value -> value.extractULongArray()},
	(TangoConst.Tango_DEVVAR_STRINGARRAY):{value -> value.extractStringArray()},
	(TangoConst.Tango_DEVVAR_LONGSTRINGARRAY):{value -> value.extractLongStringArray()},
	(TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY):{value -> value.extractDoubleStringArray()},
	(TangoConst.Tango_DEV_STATE):{value -> value.extractDevState()},
	(TangoConst.Tango_DEV_CHAR):{value -> value.extractUChar()},
	(TangoConst.Tango_CONST_DEV_STRING):{value -> value.extractString()},
	(TangoConst.Tango_DEV_UCHAR):{value -> value.extractUChar()},
	(TangoConst.Tango_DEV_LONG64):{value -> value.extractLong64()},
	(TangoConst.Tango_DEV_ULONG64):{value -> value.extractULong64()},
	(TangoConst.Tango_DEVVAR_LONG64ARRAY):{value -> value.extractLong64Array()},
	(TangoConst.Tango_DEVVAR_ULONG64ARRAY):{value -> value.extractULong64Array()},
	(TangoConst.Tango_DEV_INT):{value -> value.extractShort()},
	(TangoConst.Tango_DEV_ENCODED):{value -> value}
    ]
    
    //    static DeviceData toDeviceData(arg) throws DevFailed{
    //        DeviceData data = new DeviceData();
    //        argClass=arg.getClass()
    //        if(!arg.getClass().isArray()){
    //            switch (
    //            data.insert(args[0] as double)
    //            
    //        }else if (Array.get())
    //
    //    }
 
}

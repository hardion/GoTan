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
class TangoGAttribute implements GAttribute {
  private def attribute

  TangoGAttribute(TangoAttribute attribute){
        this.attribute = attribute
  }
    
  public Object getValue(){
      attribute.read()
  }

  public void setValue(Object o){
      attribute.write(o)
  }
    
  public String getUnit(){
     attribute.attributeProxy.get_info().unit
  }

  public void setUnit(String o){
      
  }

  public Map<String, String> getGproperties(){
      def info = attribute.attributeProxy.get_info()
      [ "timestamp":attribute.timestamp,
        "quality":attribute.quality.toString(),
        "description":info.description,
        "label":info.label,
        "min":info.min_value,
        "max":info.max_value,
        "min_alarm":info.min_alarm,
        "max_alarm":info.max_alarm]
  }
    
  def toJson(){
      def value = attribute.isString()?"\"${this.getValue()}\"" : this.getValue()
        """{"value":${value},"unit": "${this.getUnit()}","properties": "${this.getGproperties()}"}"""
  }      

}

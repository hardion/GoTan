package org.gotan.representation

import org.gotan.model.GAttribute
import org.gotan.model.GCommand
import org.gotan.model.GObject
import groovy.json.JsonBuilder

/**
 *
 * @author vinhar
 */
class JsonGRep {
    
    def read = [
        (GAttribute.class) : { attribute ->
                new JsonBuilder(
                    "value": attribute.value,
                    "unit": attribute.unit,
                    "properties": attribute.gproperties,
                ).toString()
        },
        (GCommand.class):{ command ->
                    new JsonBuilder(
                        "inputs": command.inputs,
                        "outputs": command.outputs,
                    ).toString()
        },
        (GObject.class):{ object ->
                    new JsonBuilder(
                        "class": object.gclass,
                        "state": object.state,
                        "status": object.status,
                        "attributes": object.attributes/*.keySet()*/,
                        "commands": object.commands.keySet(),
                        "properties": object.gproperties.keySet(),
                    ).toString()
        }
    ]
	
}


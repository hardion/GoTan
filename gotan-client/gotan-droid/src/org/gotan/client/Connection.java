/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gotan.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/**
 *
 * @author vinhar
 */
abstract class Connection {

    ClientResource resource;
    String elementName;

    /**
     * Create a connection to Gotan iot
     *
     * @param server
     */
    public Connection(String server, String element) {
        this(new ClientResource(server), element);
    }

    public Connection(ClientResource server, String element) {
        elementName = element;
        resource = Connection.getChild(server, elementName);
        // TODO : MediaType.APPLICATION_JSON and resource.getClientInfo().getAcceptedMediaTypes()
    }

    protected ClientResource getChild(String child) {
        return Connection.getChild(this.resource, child);
    }

    protected static ClientResource getChild(ClientResource base, String child) {
        ClientResource result = new ClientResource(base.getReference().toUri());
        if(child.contains("/")){
            String[] children = child.split("/");
            for (int i = 0; i < children.length; i++) {
                result.addSegment(children[i]);
            }
        }else{
            result.addSegment(child);            
        }
        
        return result;
    }

    public String getElementName() {
        return elementName;
    }

    public String getPath() {
        // TODO Auto-generated method stub
        return resource.getReference().toString();
    }

    /**
     * Retrieve the attribute list of this Object
     *
     * @return String array from the attribute list
     */
    public String[] listChildren(String child) {
        ClientResource childResource = this.getChild(child);
        Representation entity = childResource.get(MediaType.APPLICATION_JSON);

        String result[] = null;
        try {
            JSONArray elements = (new JsonRepresentation(entity)).getJsonArray();
            result = new String[elements.length()];
            for (int i = 0; i < result.length; i++) {
                result[i] = elements.getString(i);
            }
            entity.exhaust();
            //entity.release();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            childResource.release();
        }

        return result;
    }

    /**
     * Retrieve an element
     *
     * @return String array from the attribute list
     */
    public String readChild(String child) {
        String result = null;
        try {
            Representation entity = resource.get(MediaType.APPLICATION_JSON);
            JSONObject object = (new JsonRepresentation(entity)).getJsonObject();
            result = object.getString(child);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void writeChild(String child, Object value) {
        if (value instanceof String) {
            value = "\"" + value + "\"";
        }
        String val = "{\"" + child + "\":" + value.toString() + "}";
        resource.post(new JsonRepresentation(val), MediaType.APPLICATION_JSON);
    }

    public void writeChildren(Map<String, Object> children) {
        StringBuilder val = new StringBuilder();
        val.append("{");
        for (String key : children.keySet()) {
            Object value = children.get(key);
            val.append("\"").append(key).append("\":");
            if (value instanceof String) {
                val.append("\"");
            }

            val.append(value.toString());

            if (value instanceof String) {
                val.append("\"");
            }

            val.append(",");

        }
        int size = val.length();
        val.replace(size - 1, size, "}");

        resource.post(new JsonRepresentation(val.toString()), MediaType.APPLICATION_JSON);
    }
}

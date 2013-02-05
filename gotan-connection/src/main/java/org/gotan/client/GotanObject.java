package org.gotan.client;

import org.restlet.resource.ClientResource;

public class GotanObject extends Connection {

    /**
     * Create a GotanObject from the targeted server and object
     *
     * @param server
     * @param objectname
     */
    public GotanObject(String server, String objectname) {
        super(server + "/objects/", objectname);
    }
    
    /**
     * Create a GotanObject from the targeted server and object
     *
     * @param server
     * @param objectname
     */
    public GotanObject(ClientResource server, String objectname) {
        super(server, objectname);
    }

    /**
     * Retrieve the attribute list of this Object
     *
     * @return String array from the attribute list
     */
    public String[] getAttributesNames() {
        return this.listChildren("attributes");
    }

    /**
     * Create an Attribute connection
     *
     * @param name The attribute name
     * @return A GotanAttribute
     */
    public GotanAttribute getAttribute(String name) {
        GotanAttribute result = new GotanAttribute(this.getChild("attributes"),name);
        return result;
    }
}

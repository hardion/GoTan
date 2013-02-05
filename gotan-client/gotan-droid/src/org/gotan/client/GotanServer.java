package org.gotan.client;

public class GotanServer extends Connection {

    /**
     * Create a connection to a Gotan server
     * 
     * @param server
     * @param port
     * @param context 
     */
    public GotanServer(String server, String port, String context) {
        super("http://" + server + (!"".equals(port)? ":"+port : "") , context);
    }

    /**
     * Retrieve the attribute list of this Object
     *
     * @return String array from the attribute list
     */
    public String[] getObjectsNames() {
        return this.listChildren("objects");
    }

    /**
     * Create an Object connection
     *
     * @param name The object name
     * @return A GotanObject
     */
    public GotanObject getObject(String name) {
        GotanObject result = new GotanObject(this.getChild("objects"),name);
        // Benefit of the friendly GotanAttribute to set its client resource from this object's one
        return result;
    }
}

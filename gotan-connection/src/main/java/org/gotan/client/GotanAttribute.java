package org.gotan.client;

import org.restlet.resource.ClientResource;

public class GotanAttribute extends Connection{

	String attributename="";
	
    /**
     * Create a connection with a GotanAttribute from the targeted server and object
     * 
     * @param server
     * @param objectname
     * @param attributename 
     */
    public GotanAttribute(String server, String objectname, String attributename) {
        super(server+"/objects/"+objectname+"/attributes",attributename);
    }
    
    /**
     * Create a connection with a GotanAttribute from the parent connection
     *
     * @param server
     * @param attributename
     */
    public GotanAttribute(ClientResource server, String attributename) {
        super(server, attributename);
    }
	
	public String getValue(){
            return this.readChild("value");
        }
	
	public void setValue(String value){
            this.writeChild("value", value);
	}

}

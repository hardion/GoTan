package org.gotan.client;


public class GotanRestClient {

	public static GotanServer createServerConnection(String server){
	    return GotanRestClient.createServerConnection(server, "8080");
	}
	
	public static GotanServer createServerConnection(String server, String port){
		return GotanRestClient.createServerConnection(server, port, "gotan");	    
	}
	
	public static GotanServer createServerConnection(String server, String port, String context){
		GotanServer result = new GotanServer(server, port, context);
	    return result;
	}

	public static GotanObject createClientObject(String server, String objectname){
		GotanObject object = new GotanObject(server, objectname);
	    return object;
	}
}

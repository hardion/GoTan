package org.gotan.client;

import groovy.json.JsonSlurper;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;

/**
 *
 * @author vinhar
 */
public class GotanServerTest {

    private static Component component;
    private static final short PORT = 8182;
    private static final String SPORT = Short.toString(PORT);

    public GotanServerTest() {
    }

    @BeforeClass
    public static void setUpServer() throws Exception {
        // Gotan Server simulation
        // Create a component  
        component = new Component();
        component.getServers().add(Protocol.HTTP, 8182);
        component.getClients().add(Protocol.CLAP);

        // Create an application  
        Application application = new Application() {
            @Override
            public Restlet createInboundRoot() {
                Directory dir = new Directory(getContext(), "clap://class/");
                dir.setDeeplyAccessible(true);
                dir.setListingAllowed(true);
                return dir;
            }
        };

        // Attach the application to the component and start it  
        component.getDefaultHost().attach("/gotan", application);
        component.start();


    }

    @AfterClass
    public static void tearDownClass() {
        try {
          //  Thread.sleep(30000);
            component.stop();
        } catch (Exception ex) {
            Logger.getLogger(GotanServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getName method, of class GotanServer.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        GotanServer instance = GotanRestClient.createServerConnection("localhost", SPORT);
        String expResult = String.format("http://localhost:%s/gotan", SPORT);
        String result = instance.getPath();
        assertEquals(expResult, result);
    }

    /**
     * Test of getObjectsNames method, of class GotanServer.
     */
    @Test
    public void testGetObjectsNames() {
        System.out.println("getObjectsNames");
        GotanServer instance = GotanRestClient.createServerConnection("localhost", SPORT);
        String[] expResult = GotanServerTest.GetJsonResourceAsStringArray("objects");
        String[] result = instance.getObjectsNames();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getObject method, of class GotanServer.
     */
    @Test
    public void testGetObject() {
        System.out.println("getObject");
        String name = "sys/test/foo";
        GotanServer instance = GotanRestClient.createServerConnection("localhost", SPORT,"gotan/test1");
        GotanObject result = instance.getObject(name);
        String[] expected = GotanServerTest.GetJsonResourceAsStringArray("test1/objects/sys/test/foo/attributes");
                
        assertArrayEquals(expected, result.getAttributesNames());

    }
    
    public static Object GetJsonResource(String path){
        Object result;
        
        InputStream is = GotanServerTest.class.getClassLoader().getResourceAsStream(path);
        result = new JsonSlurper().parse(new InputStreamReader(is));
        
        return result;
    }
    
    public static String[] GetJsonResourceAsStringArray(String path){
        String[] result;
        Object resource = GotanServerTest.GetJsonResource(path);
        if(resource instanceof List){
            List<String> list = (List<String>)resource;
            result = new String[list.size()];
            result = list.toArray(result);
        }else{
            result = new String[0];
        }
        return result;
    }
}

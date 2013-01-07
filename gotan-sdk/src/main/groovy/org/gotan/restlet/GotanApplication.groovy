package org.gotan.restlet

import org.restlet.Application
import org.gotan.Gotan
import org.restlet.Restlet
import org.restlet.routing.Router
import org.restlet.routing.TemplateRoute
import org.restlet.routing.Variable
import org.restlet.routing.Redirector
import org.gotan.representation.JsonGRep

/**
 *
 * @author vinhar
 */
class GotanApplication extends Application{
    
    Gotan gotan
    
    public GotanApplication(Gotan gotan){
        this.gotan = gotan
    }
    
    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        // Attach the gotan object to the context
        this.context.attributes["gotan"] = gotan
        
        // Create a router Restlet that defines routes.
        Router router = new Router(getContext());
        TemplateRoute route

        // Defines a route for the resource "list of items"
        router.attachDefault(GotanResource.class);
        router.attach("/", GotanResource.class);

        this.addSimpleRouter(router, "/classes", GotanClassesResource.class);
        
        this.addSimpleRouter(router, "/servers", GotanServersResource.class);

        this.addSimpleRouter(router, "/objects", GotanObjectsResource.class);        

        this.addBasedObjectRouter(router, "/objects/{object}/attributes/{attribute}/properties/{property}", GotanAttributePropertyResource.class )

        this.addBasedObjectRouter(router, "/objects/{object}/attributes/{attribute}/properties", GotanAttributePropertiesResource.class )

        this.addBasedObjectRouter(router, "/objects/{object}/attributes/{attribute}", GotanAttributeResource.class )

        this.addBasedObjectRouter(router, "/objects/{object}/attributes", GotanAttributesResource.class)

        this.addBasedObjectRouter(router, "/objects/{object}/commands/{command}", GotanCommandResource.class)

        this.addBasedObjectRouter(router, "/objects/{object}/commands", GotanCommandsResource.class)

        this.addBasedObjectRouter(router, "/objects/{object}/properties/{property}", GotanPropertyResource.class)

        this.addBasedObjectRouter(router, "/objects/{object}/properties", GotanPropertiesResource.class)

        this.addBasedObjectRouter(router, "/objects/{object}", GotanObjectResource.class);
        
        return router;
    }
    
    /**
     * Add a redirector to deal with the trailing slash
     * Every Restlet node route should ends with /
     * 
     */
    private def addRedirector(def router, def path){
        // Deal with the trailing slash
        // 
        def pathSlashed = path
        if ( path.endsWith("/") ){
            path = path[0..-1]
        }else {
            pathSlashed += "/"
        }
        
        router.attach(path, new Redirector(this.context, pathSlashed));
//        router.attach(pathSlashed, new Redirector(this.context, path));
        
        return pathSlashed
    }

    /**
     * Create a route based on object url
     */
    private def addSimpleRouter(def router, def path, def restlet){
        
        // Create the route
        // And the redirector in case of path without the trailing space
        //path = this.addRedirector(router, path+"/")
        def route = router.attach(path, restlet)
        
        return route
    }

    
    /**
     * Create a route based on object url
     */
    private def addBasedObjectRouter(def router, def path, def restlet){
        
        // Create the route and the redirector
        // Set the object variable as an URI type
        def route = this.addSimpleRouter(router, path, restlet)
        route.template.variables["object"] = new Variable(Variable.TYPE_URI_PATH)
    }


}


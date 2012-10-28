package org.gotan.restlet

import org.restlet.Application
import org.gotan.Gotan
import org.restlet.Restlet
import org.restlet.routing.Router
import org.restlet.routing.TemplateRoute
import org.restlet.routing.Variable

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
        router.attach("/", GotanResource.class);
        //router.attach("/objects", GotanObjectsResource.class);
        router.attach("/classes", GotanClassesResource.class);
        router.attach("/servers", GotanServersResource.class);
        router.attach("/objects", GotanObjectsResource.class,);
        

        route = router.attach("/objects/{object}/attributes/{attribute}/properties/{property}", GotanAttributePropertyResource.class)
        route.template.variables["object"] = new Variable(Variable.TYPE_URI_PATH)

        route = router.attach("/objects/{object}/attributes/{attribute}/properties", GotanAttributePropertiesResource.class)
        route.template.variables["object"] = new Variable(Variable.TYPE_URI_PATH)

        route = router.attach("/objects/{object}/attributes/{attribute}", GotanAttributeResource.class)
        route.template.variables["object"] = new Variable(Variable.TYPE_URI_PATH)

        route = router.attach("/objects/{object}/attributes", GotanAttributesResource.class)
        route.template.variables["object"] = new Variable(Variable.TYPE_URI_PATH)

        route = router.attach("/objects/{object}/commands/{command}", GotanCommandResource.class)
        route.template.variables["object"] = new Variable(Variable.TYPE_URI_PATH)

        route = router.attach("/objects/{object}/commands", GotanCommandsResource.class)
        route.template.variables["object"] = new Variable(Variable.TYPE_URI_PATH)

        route = router.attach("/objects/{object}/properties/{property}", GotanPropertyResource.class)
        route.template.variables["object"] = new Variable(Variable.TYPE_URI_PATH)

        route = router.attach("/objects/{object}/properties", GotanPropertiesResource.class)
        route.template.variables["object"] = new Variable(Variable.TYPE_URI_PATH)

        route = router.attach("/objects/{object}", GotanObjectResource.class);
        route.template.variables["object"] = new Variable(Variable.TYPE_URI_PATH)
        
        return router;
    }


}


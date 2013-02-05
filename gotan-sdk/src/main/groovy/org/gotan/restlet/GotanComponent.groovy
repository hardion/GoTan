package org.gotan.restlet

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.Restlet
import org.gotan.Gotan

import groovy.util.logging.Slf4j
import org.slf4j.*

@Slf4j
class GotanComponent extends Component {

    public GotanComponent(Gotan gotan){
        // Add a new HTTP server listening on port 8111.
        log.debug("Start GotanComponent : port=$gotan.port, context=$gotan.context")
        this.getServers().add(Protocol.HTTP, gotan.port);
        this.getDefaultHost().attach(gotan.context, new GotanApplication(gotan));
//        this.getDefaultHost().attach("/gotan", new GotanApplication(gotan));
    }
    
}

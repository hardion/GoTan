package org.gotan.restlet

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.Restlet
import org.gotan.Gotan

class GotanComponent extends Component {

    public GotanComponent(Gotan gotan){
        // Add a new HTTP server listening on port 8111.
        this.getServers().add(Protocol.HTTP, gotan.port);
        this.getDefaultHost().attach("/gotan", new GotanApplication(gotan));
    }
    
}

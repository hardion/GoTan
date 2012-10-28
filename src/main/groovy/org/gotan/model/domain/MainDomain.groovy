package org.gotan.model.domain

/**
 *
 * @author vinhar
 */
class MainDomain extends PublicDomain{
    
    PrivateDomain local = new PrivateDomain()
    
    public MainDomain(){
        this.registerDomain( local )
    }
    
    
    
}


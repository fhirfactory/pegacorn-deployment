/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.fhirfactory.pegacorn.deploymentproperties;

import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author mhunter
 */
@ApplicationScoped
public class PegacornCommonProperties {
    
    private final String PEGACORN_COMMUNICATE_IRIS_HOST = "10.10.1.11";
    private final String PEGACORN_COMMUNICATE_ROOMSERVER_HOST = "10.10.1.10";
    private final String PEGACORN_LADON_HOST =  "10.10.10.1.20";
    
    public String getPegacornCommunicateIrisHost(){
        return(PEGACORN_COMMUNICATE_IRIS_HOST);
    }
    
    public String getPegacornCommunicateRoomServerHost(){
        return(PEGACORN_COMMUNICATE_ROOMSERVER_HOST);
    }
    
    public String getPegacornLadonHost(){
        return(PEGACORN_COMMUNICATE_IRIS_HOST);
    }
    
}

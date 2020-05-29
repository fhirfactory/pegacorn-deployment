/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.fhirfactory.pegacorn.deploymentproperties;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mhunter
 */
@ApplicationScoped
public class PetasosProperties {
    
    // For assisting in monitoring expected end time in parcels, this period in milliseconds
    // can be used when monitoring a UoW. For example it can be added to the expected completion
    // time to provide an extra buffer to avoid classing a UoW as failed too early
    private final int EXPECTED_COMPLETION_TIME_BUFFER_MILLIS = 200;

    // How often to send a heartbeat between Nodes
    private final int HEARTBEAT_FREQUENCY_MILLIS = 100;
    
    // Every N heartbeats, include a status update with the heartbeat message
    private final int HEARTBEAT_STATUS_UPDATE_FREQUENCY = 10;
    
    // If a heartbeat fails, try to reconnect N times before failing and marking
    // the Node as unavailable
    private final int HEARTBEAT_NUM_RETRIES = 3;
    
    // If the cache is full, this is the location where the overflow is persisted
    private final String CACHE_OVERFLOW_DIRECTORY = "/tmp";
    
    // Size of the cache in bytes, once exceeded entries will be written to the overflow
    // directory
    private final long CACHE_SIZE_IN_BYTES = 1000000000;

    // The name of the environment variable which holds a list of Petasos service endpoints
    // in <service name>:<base port> form
    // For example, in a 3 site pegacorn deployment, at site A this variable would
    // be set to something like:
    // petasos.mitaf.siteb:80660;petasos.mitaf.sitec:80760
    private final String SITE_ENDPOINT_ENV_VAR_NAME = "DIST_HOST_PORT_AND_URI_SEMI_COLON_LIST";

    // An array which holds the Petasos Node endpoints at other sites. This information is
    // pulled from the {$SITE_ENDPOINT_ENV_VAR_NAME} environment variable
    private ArrayList<String> siteServiceEndpoints;

    // The name of the environment variable which holds service endpointof this deployment.
    // For example, at site A this variable would be set to something like:
    // petasos.mitaf.sitea:80560
    private final String MY_SITE_ENDPOINT_ENV_VAR_NAME = "MY_HOST_PORT_AND_URI";

    // A string holding the site Petasos service endpoint. For example, at site A 
    // this variable would be set to something like:
    // petasos.mitaf.sitea:80560
    private String myServiceEndpoint;
    
    public int getExpectedCompletionTimeBufferMillis() {
        return EXPECTED_COMPLETION_TIME_BUFFER_MILLIS;
    }
    
    public int getHeartbeatFrequencyMillis() {
        return HEARTBEAT_FREQUENCY_MILLIS;
    }
    
    public int getHeartbeatStatusUpdateFrequency() {
        return HEARTBEAT_STATUS_UPDATE_FREQUENCY;
    }
    
    public int getHeartbeatNumRetries () {
        return HEARTBEAT_NUM_RETRIES;
    }
    
    public String getCacheOverflowDirectory() {
        return CACHE_OVERFLOW_DIRECTORY;
    }
    
    public long getCacheSizeInBytes() {
        return CACHE_SIZE_IN_BYTES;
    }
    
    // parse an environment variable holding a comma separated list of Kubernetes Petasos
    // service endpoints and add each endpoint to a List
    public ArrayList<String> getOtherSiteServiceEndpoints() {
        if (siteServiceEndpoints == null) {
    		this.siteServiceEndpoints = new ArrayList<String>();
        	String distributionEndPointsHostPortAndURISemiColonList = System.getenv(SITE_ENDPOINT_ENV_VAR_NAME);
        	if (!StringUtils.isBlank(distributionEndPointsHostPortAndURISemiColonList)) {
            	String[] distributionEndPointsHostPortAndURIs = distributionEndPointsHostPortAndURISemiColonList.split(";");
            	for (int i=0; i < distributionEndPointsHostPortAndURIs.length; i++) {
            		siteServiceEndpoints.add(distributionEndPointsHostPortAndURIs[i].trim());
            	}
        	}
        }
        
        return this.siteServiceEndpoints;
    }
    
    public String getMyServiceEndpoint() {
        if (myServiceEndpoint == null) {
        	String myServiceEndpoint = System.getenv(MY_SITE_ENDPOINT_ENV_VAR_NAME);
        	if (StringUtils.isBlank(myServiceEndpoint)) {
        		myServiceEndpoint = null;
        	} else {
          		myServiceEndpoint = myServiceEndpoint.trim();
        	}
        }
        
        return myServiceEndpoint;
    }
}
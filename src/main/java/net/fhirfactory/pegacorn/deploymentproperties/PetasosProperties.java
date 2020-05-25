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
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.fhirfactory.pegacorn.deployment.properties;

import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Mark A. Hunter (ACT Health)
 */
@ApplicationScoped
public class LadonDefaultDeploymentProperties {

    private static Integer VERY_LONG_CACHE_RETENTION_PERIOD = 1800; // 30 Minutes
    private static Integer LONG_CACHE_RETENTION_PERIOD = 300; // 5 Minutes
    private static Integer MEDIUM_CACHE_RETENTION_PERIOD = 60; // 1 Minute
    private static Integer SHORT_CACHE_RETENTION_PERIOD = 10; // 10 Seconds
    private static Integer VERY_SHORT_CACHE_RETENTION_PERIOD = 2; // 2 Seconds

    // VirtualDB Cache Entry (flushing) Ages --> In Seconds
    public Integer getCareTeamCacheAgeThreshold(){return(SHORT_CACHE_RETENTION_PERIOD);}
    public Integer getCommunicationCacheAgeThreshold(){return(LONG_CACHE_RETENTION_PERIOD);}
    public Integer getCommunicationRequestCacheAgeThreshold(){return(LONG_CACHE_RETENTION_PERIOD);}
    public Integer getDocumentReferenceCacheAgeThreshold(){return(VERY_LONG_CACHE_RETENTION_PERIOD);}
    public Integer getEncounterCacheAgeThreshold(){return(SHORT_CACHE_RETENTION_PERIOD);}
    public Integer getEndpointCacheAgeThreshold(){return(LONG_CACHE_RETENTION_PERIOD);}
    public Integer getGroupCacheAgeThreshold(){return(SHORT_CACHE_RETENTION_PERIOD);}
    public Integer getHealthcareServiceCacheAgeThreshold(){return(LONG_CACHE_RETENTION_PERIOD);}
    public Integer getLocationCacheAgeThreshold(){return(MEDIUM_CACHE_RETENTION_PERIOD);}
    public Integer getOrganizationCacheAgeThreshold(){return(MEDIUM_CACHE_RETENTION_PERIOD);}
    public Integer getPatientCacheAgeThreshold(){return(SHORT_CACHE_RETENTION_PERIOD);}
    public Integer getPractitionerCacheAgeThreshold(){return(SHORT_CACHE_RETENTION_PERIOD);}
    public Integer getPractitionerRoleCacheAgeThreshold(){return(VERY_SHORT_CACHE_RETENTION_PERIOD);}
    public Integer getProcedureCacheAgeThreshold(){return(LONG_CACHE_RETENTION_PERIOD);}
    public Integer getTaskCacheAgeThreshold(){return(VERY_SHORT_CACHE_RETENTION_PERIOD);}
    public Integer getValueSetCacheAgeThreshold(){return(VERY_LONG_CACHE_RETENTION_PERIOD);}

}
